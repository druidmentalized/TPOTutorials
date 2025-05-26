package org.project.dto;

import org.springframework.lang.Nullable;

public class RequestUpdateLinkDTO {
    public String name;
    public String targetUrl;

    @Nullable
    public String password;

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

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
