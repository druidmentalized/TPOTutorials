package org.project.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HtmlLoader {
    public static String loadHtml(String htmlPath) {
        try {
            ClassPathResource resource = new ClassPathResource(htmlPath);
            return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return "<p class='error-message visible'>Error: " + e.getMessage() + "</p>";
        }
    }
}
