package org.project.dto;

public class LinkFormDTO {
    public String id;
    public String name;
    public String targetUrl;
    public String redirectUrl;
    public String visits;

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

    public String getVisits() {
        return visits;
    }
    public void setVisits(String visits) {
        this.visits = visits;
    }
}
