package org.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import org.project.annotations.ValidPassword;

public class LinkFormDTO {
    public String id;

    @Size(min = 5, max = 20, message = "{validation.name.size}")
    public String name;

    @NotBlank(message = "{validation.url.notBlank}")
    @URL(protocol = "https", message = "{validation.url.httpsOnly}")

    public String targetUrl;

    public String redirectUrl;

    @ValidPassword
    public String password;

    public long visits;

    public LinkFormDTO() {}

    public LinkFormDTO(String id, String name, String targetUrl, String redirectUrl, String password, long visits) {
        this.id = id;
        this.name = name;
        this.targetUrl = targetUrl;
        this.redirectUrl = redirectUrl;
        this.password = password;
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

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public long getVisits() {
        return visits;
    }
    public void setVisits(long visits) {
        this.visits = visits;
    }
}
