package org.project.tpo08.controllers;

import com.google.googlejavaformat.java.FormatterException;
import org.project.tpo08.dto.FormatRequest;
import org.project.tpo08.dto.FormatResult;
import org.project.tpo08.exceptions.ResultPersistenceException;
import org.project.tpo08.services.FormatService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FormatController {

    private final FormatService formatService;
    private final int standardRetention;

    public FormatController(FormatService formatService, int standardRetention) {
        this.formatService = formatService;
        this.standardRetention = standardRetention;
    }

    @GetMapping({"/", "/codeFormatter"})
    public String getCodeFormat(Model model) {
        FormatRequest request = new FormatRequest();
        request.setDuration(standardRetention);
        model.addAttribute("formatRequest", request);
        return "codeFormatter";
    }

    @PostMapping("/codeFormatter")
    public String postCodeFormat(
            @ModelAttribute FormatRequest formatRequest,
            Model model) {
        String formattedCode = null;
        String errorAttribute = "error";
        try {
            formattedCode = formatService.formatCode(formatRequest.getSourceCode());
            formatRequest.setFormattedCode(formattedCode);
            formatService.saveResult(formatRequest);
        } catch (FormatterException e) {
            model.addAttribute(errorAttribute, "Invalid Java code: " + e.getMessage());
        } catch (ResultPersistenceException e) {
            model.addAttribute(errorAttribute, "Exception during saving: " + e.getMessage());
        } catch (Exception e) {
            model.addAttribute(errorAttribute, "Error: " + e.getMessage());
        }

        formatRequest.setFormattedCode(formattedCode);
        return "codeFormatter";
    }

    @GetMapping("/codeFormatter-result")
    public String getFormattedCode(
            @RequestParam String id,
            Model model) {
        try {
            FormatResult formatResult = formatService.getResultById(id);
            model.addAttribute("formatResult", formatResult);
        } catch (Exception e) {
            return "formatNotFound";
        }
        return "codeFormatter-result";
    }
}
