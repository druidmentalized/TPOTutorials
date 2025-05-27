package org.project.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.project.annotations.UniqueURL;
import org.project.services.LinkService;
import org.springframework.stereotype.Component;

@Component
public class UniqueURLValidator implements ConstraintValidator<UniqueURL, String> {
    private final LinkService linkService;

    public UniqueURLValidator(LinkService linkService) {
        this.linkService = linkService;
    }

    @Override
    public boolean isValid(String url, ConstraintValidatorContext constraintValidatorContext) {
        if (url == null || url.isEmpty()) return false;
        return linkService.existsByTargetUrl(url);
    }
}
