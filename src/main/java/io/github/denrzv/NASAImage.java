package io.github.denrzv;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class NASAImage {
    private final String copyright, explanation, media_type, service_version, title, hdurl, url;
    private final Date date;

    public NASAImage(@JsonProperty("copyright") String copyright,
                     @JsonProperty("explanation") String explanation,
                     @JsonProperty("media_type") String media_type,
                     @JsonProperty("service_version") String service_version,
                     @JsonProperty("title") String title,
                     @JsonProperty("hdurl") String hdurl,
                     @JsonProperty("url") String url,
                     @JsonProperty("date") Date date) {
        this.copyright = copyright;
        this.explanation = explanation;
        this.media_type = media_type;
        this.service_version = service_version;
        this.title = title;
        this.hdurl = hdurl;
        this.url = url;
        this.date = date;
    }

    public String getCopyright() {
        return copyright;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getMedia_type() {
        return media_type;
    }

    public String getService_version() {
        return service_version;
    }

    public String getTitle() {
        return title;
    }

    public String getHdurl() {
        return hdurl;
    }

    public String getUrl() {
        return url;
    }

    public Date getDate() {
        return date;
    }
}
