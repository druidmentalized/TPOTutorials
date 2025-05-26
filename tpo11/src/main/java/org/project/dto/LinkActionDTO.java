package org.project.dto;

public class LinkActionDTO {
    public String id;
    public String action;
    public String password;

    public LinkActionDTO() {}

    public LinkActionDTO(String id, String action) {
        this.id = id;
        this.action = action;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
