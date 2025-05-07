package org.project.tpo09.services;

import org.project.tpo09.dto.BmiDto;
import org.project.tpo09.dto.BmrDto;
import org.project.tpo09.enums.BodyCategoryEnum;
import org.project.tpo09.exceptions.InvalidDataException;
import org.project.tpo09.exceptions.InvalidGenderException;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {
    public BmiDto calculateBmi(double weight, double height) {
        if (weight <= 0 || height <= 0) {
            throw new InvalidDataException("Invalid data: weight and height parameters must be positive numbers");
        }

        double heightInMeters = height / 100;
        double bmi = weight / (heightInMeters * heightInMeters);

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
        if (weight <= 0 || height <= 0 || age <= 0) {
            throw new InvalidDataException("Invalid data: weight, height and age parameters must be positive numbers");
        }

        double bmr;
        switch (gender) {
            case "man" -> bmr = calculateMaleBMR(weight, height, age);
            case "woman" -> bmr = calculateFemaleBMR(weight, height, age);
            default -> throw new InvalidGenderException("Invalid gender data");
        }

        BmrDto bmrDto = new BmrDto();

        bmrDto.setGender(gender);
        bmrDto.setWeight(weight);
        bmrDto.setHeight(height);
        bmrDto.setAge(age);
        bmrDto.setBmr(Math.round(bmr * 100.0) / 100.0);

        return bmrDto;
    }

    
    // BMR gender formulas
    private static double calculateMaleBMR(double weight, double height, int age) {
        return 13.397 * weight + 4.799 * height - 5.677 * age + 88.362;
    }

    private static double calculateFemaleBMR(double weight, double height, int age) {
        return 9.247 * weight + 3.098 * height - 4.330 * age + 447.593;
    }
}
