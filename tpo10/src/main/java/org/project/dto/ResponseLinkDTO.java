package org.project.dto;

public class ResponseLinkDTO {
    public String id;
    public String name;
    public String targetUrl;
    public String redirectUrl;
    public long visits;

    public ResponseLinkDTO() {}

    public ResponseLinkDTO(String id, String name, String targetUrl, String redirectUrl, long visits) {
        this.id = id;
        this.name = name;
        this.targetUrl = targetUrl;
        this.redirectUrl = redirectUrl;
        this.visits = visits;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getTargetUrl() {
        return targetUrl;
    }
    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }
    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public long getVisits() {
        return visits;
    }
    public void setVisits(long visits) {
        this.visits = visits;
    }
}
