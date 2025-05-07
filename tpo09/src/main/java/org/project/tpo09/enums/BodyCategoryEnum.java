package org.project.tpo09.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BodyCategoryEnum {
    UNDERWEIGHT("Underweight"),
    NORMAL("Normal"),
    OVERWEIGHT("Overweight"),
    OBESE("Obese");

    private final String label;

    BodyCategoryEnum(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
}
