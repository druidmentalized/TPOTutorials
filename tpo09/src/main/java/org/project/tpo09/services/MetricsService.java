package org.project.tpo09.services;

import org.project.tpo09.dto.BmiDto;
import org.project.tpo09.dto.BmrDto;
import org.project.tpo09.enums.BodyCategoryEnum;
import org.project.tpo09.exceptions.InvalidDataException;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {
    public BmiDto calculateBmi(double weight, double height) {
        if (weight <= 0 || height <= 0) {
            throw new InvalidDataException("Weight and height must be positive");
        }

        height /= 100;
        double bmi = weight / (height * height);

        BodyCategoryEnum category;
        if (bmi < 18.5) {
            category = BodyCategoryEnum.UNDERWEIGHT;
        } else if (bmi < 25.0) {
            category = BodyCategoryEnum.NORMAL;
        } else if (bmi < 30.0) {
            category = BodyCategoryEnum.OVERWEIGHT;
        } else {
            category = BodyCategoryEnum.OBESE;
        }

        BmiDto dto = new BmiDto();

        dto.setWeight(weight);
        dto.setHeight(height);
        dto.setBmi(Math.round(bmi * 100.0) / 100.0);
        dto.setType(category);

        return dto;
    }

    public BmrDto calculateBmr(String gender, double weight, double height, int age) {
        return null;
    }
}
