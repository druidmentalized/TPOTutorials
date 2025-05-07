package org.project.tpo09.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class BmrDto {
    String gender;
    double weight;
    double height;
    int age;
    double bmr;

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

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

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    @JsonProperty("bmr")
    @JacksonXmlProperty(localName = "bmr")
    public int getBmrInt() {
        return (int) bmr;
    }
    @JsonIgnore
    public double getBmr() {
        return bmr;
    }
    public void setBmr(double bmr) {
        this.bmr = bmr;
    }
}
