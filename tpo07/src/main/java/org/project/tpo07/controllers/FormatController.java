package org.project.tpo07.controllers;

import com.google.googlejavaformat.java.FormatterException;
import org.project.tpo07.dto.FormatRequest;
import org.project.tpo07.dto.FormatResult;
import org.project.tpo07.exceptions.ResultPersistenceException;
import org.project.tpo07.services.FormatService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FormatController {
    private final FormatService formatService;

    public FormatController(FormatService formatService) {
        this.formatService = formatService;
    }

    @GetMapping({"/", "/format-code"})
    public String getCodeFormat(Model model) {
        model.addAttribute("formatRequest", new FormatRequest());
        return "format-code";
    }

    @PostMapping("/code-format")
    public String postCodeFormat(
            @ModelAttribute FormatRequest formatRequest,
            Model model) {
        String formattedCode = ""; //TODO: think about this variable
        try {
            formattedCode = formatService.formatCode(formatRequest.getSourceCode());
            formatRequest.setFormattedCode(formattedCode);
            formatService.saveResult(formatRequest);
        } catch (FormatterException e) {
            model.addAttribute("error", "Invalid Java code: " + e.getMessage());
        } catch (ResultPersistenceException e) {
            model.addAttribute("error", "Exception during saving: " + e.getMessage());
        } catch (Exception e) {
            model.addAttribute("error", "Error: " + e.getMessage());
        }

        formatRequest.setFormattedCode(formattedCode);
        return "format-code";
    }

    @GetMapping("/format-result/{id}")
    public String getFormattedCode(
            @PathVariable String id,
            Model model) {
        try {
            FormatResult formatResult = formatService.getResultById(id);
            model.addAttribute("formatResult", formatResult);
        } catch (Exception e /*TODO: change to normal custom exception*/) {
            //TODO: make normal fallback
        }
        return "format-result";
    }
}
