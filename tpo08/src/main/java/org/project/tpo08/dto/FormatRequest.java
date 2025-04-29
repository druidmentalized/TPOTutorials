package org.project.tpo08.dto;

public class FormatRequest {
    private String id;
    private int duration;
    private String sourceCode;
    private String formattedCode;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getSourceCode() {
        return sourceCode;
    }
    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getFormattedCode() {
        return formattedCode;
    }
    public void setFormattedCode(String formattedCode) {
        this.formattedCode = formattedCode;
    }
}
