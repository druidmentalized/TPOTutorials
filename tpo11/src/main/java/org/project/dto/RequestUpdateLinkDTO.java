package org.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import org.project.annotations.UniqueURL;
import org.springframework.lang.Nullable;

public class RequestUpdateLinkDTO {
    @Size(min = 5, max = 20, message = "{validation.name.size}")
    public String name;

    @NotBlank(message = "{validation.url.notBlank}")
    @URL(protocol = "https", message = "{validation.url.httpsOnly}")
    @UniqueURL
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
