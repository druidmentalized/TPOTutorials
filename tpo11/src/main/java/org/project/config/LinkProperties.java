package org.project.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "link")
public class LinkProperties {
    private int idLength;
    private String redirectPath;
    private String linksPath;
    private String host;

    public int getIdLength() {
        return idLength;
    }
    public void setIdLength(int idLength) {
        this.idLength = idLength;
    }

    public String getRedirectPath() {
        return redirectPath;
    }
    public void setRedirectPath(String redirectPath) {
        this.redirectPath = redirectPath;
    }

    public String getLinksPath() {
        return linksPath;
    }
    public void setLinksPath(String linksPath) {
        this.linksPath = linksPath;
    }

    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
}
