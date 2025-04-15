package org.project.dto;

import java.util.LinkedHashMap;
import java.util.Map;

public class PersonDto {
    private final Map<String, String> fieldsMap = new LinkedHashMap<>();

    public PersonDto() {}

    public Map<String, String> getFieldsMap() {
        return fieldsMap;
    }
}
