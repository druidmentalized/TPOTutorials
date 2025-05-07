package org.project.tpo09.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import org.project.tpo09.enums.BodyCategoryEnum;

public class BmiDto {
    double weight;
    double height;
    double bmi;
    BodyCategoryEnum type;

    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }
    public void setHeight(double height) {
        this.height = height;
    }

    @JsonProperty("bmi")
    @JacksonXmlProperty(localName = "bmi")
    public int getBmiInt() {
        return (int) bmi;
    }
    @JsonIgnore
    public double getBmi() {
        return bmi;
    }
    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public BodyCategoryEnum getType() {
        return type;
    }
    public void setType(BodyCategoryEnum type) {
        this.type = type;
    }
}
