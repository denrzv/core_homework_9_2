package io.github.denrzv;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) {
        final String URL = "https://api.nasa.gov/planetary/apod?api_key=wNHGHx7SjgpcdXOs5XN4o5U3DhqUvMfykHALy6a3";

        try(CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build()) {

            CloseableHttpResponse response = getResponse(URL, httpClient);
            ObjectMapper mapper = new ObjectMapper();
            NASAImage nasaImage = mapper.readValue(
                    response.getEntity().getContent(),
                    new TypeReference<>() {});

            String imageUrl = nasaImage.getUrl();
            String fileName = getFileNameFromURL(imageUrl);

            response = getResponse(imageUrl, httpClient);

            saveFile(response.getEntity().getContent(), fileName);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static CloseableHttpResponse getResponse(String URL, CloseableHttpClient httpClient) throws IOException {
        HttpGet request = new HttpGet(URL);
        return httpClient.execute(request);
    }

    public static void saveFile(InputStream is, String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName, false);
        int read;
        byte[] buffer = new byte[32768];
        while((read = is.read(buffer)) > 0) {
            fos.write(buffer, 0, read);
        }
        fos.close();
        is.close();
    }

    public static String getFileNameFromURL(String url) {
        int slashIndex = url.lastIndexOf("/");
        return url.substring(slashIndex + 1);
    }
}