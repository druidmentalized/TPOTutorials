package org.project.dto;

import java.util.HashMap;
import java.util.Map;

public class PersonDto {
    private final Map<String, String> fieldsMap = new HashMap<>();

    public PersonDto() {}

    public Map<String, String> getFieldsMap() {
        return fieldsMap;
    }
}
