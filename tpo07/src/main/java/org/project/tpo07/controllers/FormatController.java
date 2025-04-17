package org.project.tpo07.controllers;

import org.project.tpo07.dto.FormatRequest;
import org.project.tpo07.dto.FormatResult;
import org.project.tpo07.services.FormatService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

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
        } catch (Exception e /*TODO: change to normal custom exception*/) {
            model.addAttribute("error", e.getMessage());
        }

        formatRequest.setFormattedCode(formattedCode);
        return "format-code";
    }

    @GetMapping("/format-result/{id}")
    public String getFormattedCode(
            @PathVariable String id,
            Model model) {
        try {
            FormatResult formatResult= formatService.getResultById(id);
            model.addAttribute("formatResult", formatResult);
        } catch (Exception e /*TODO: change to normal custom exception*/) {
            //TODO: make normal fallback
        }
        return "format-result";
    }
}
