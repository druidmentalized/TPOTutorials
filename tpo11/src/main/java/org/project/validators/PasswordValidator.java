package org.project.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.project.annotations.ValidPassword;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null || password.isBlank()) return true;

        boolean hasLower = password.matches(".*[a-z].*");
        boolean hasUpper = password.replaceAll("[^A-Z]", "").length() >= 2;
        boolean hasDigits = password.replaceAll("[\\D]", "").length() >= 3;
        boolean hasSpecial = password.replaceAll("[a-zA-Z0-9]", "").length() >= 4;
        boolean hasLength = password.length() >= 10;

        if (hasLower && hasUpper && hasDigits && hasSpecial && hasLength) {
            return true;
        }

        context.disableDefaultConstraintViolation();

        if (!hasLower)
            context.buildConstraintViolationWithTemplate("{validation.password.missingLowercase}")
                    .addConstraintViolation();
        if (!hasUpper)
            context.buildConstraintViolationWithTemplate("{validation.password.missingUppercase}")
                    .addConstraintViolation();
        if (!hasDigits)
            context.buildConstraintViolationWithTemplate("{validation.password.missingDigits}")
                    .addConstraintViolation();
        if (!hasSpecial)
            context.buildConstraintViolationWithTemplate("{validation.password.missingSpecial}")
                    .addConstraintViolation();
        if (!hasLength)
            context.buildConstraintViolationWithTemplate("{validation.password.tooShort}")
                    .addConstraintViolation();

        return false;
    }
}
