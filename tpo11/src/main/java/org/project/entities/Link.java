package org.project.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Link")
public class Link {
    @Id
    @Column(name = "Link_ID", length = 10)
    public String id;

    @Column(name = "Name", nullable = false, length = 50)
    public String name;

    @Column(name = "Target_Url", nullable = false, length = 250)
    public String targetUrl;

    @Column(name = "Password", length = 100)
    public String password;

    @Column(name = "Visits", nullable = false)
    public long visits;

    public Link() {}

    public Link(String id, String name, String targetUrl, String password, long visits) {
        this.id = id;
        this.name = name;
        this.targetUrl = targetUrl;
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
