package org.project.tpo08.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class FormatResult implements Serializable {
    private String id;
    private String sourceCode;
    private String formattedCode;
    private LocalDateTime formatDate;
    private LocalDateTime expiryDate;

    public String getId() {
        return id;
    }
    public void setId(String codeId) {
        this.id = codeId;
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

    public LocalDateTime getFormatDate() {
        return formatDate;
    }
    public void setFormatDate(LocalDateTime formatDate) {
        this.formatDate = formatDate;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }
    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
}
