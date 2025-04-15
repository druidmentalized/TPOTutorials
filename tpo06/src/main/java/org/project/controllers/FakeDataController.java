package org.project.controllers;

import org.project.dto.PersonDto;
import org.project.services.FakeDataService;
import org.project.enums.AdditionalFields;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class FakeDataController {

    private final FakeDataService fakeDataService;

    public FakeDataController(FakeDataService fakeDataService) {
        this.fakeDataService = fakeDataService;
    }

    @GetMapping({"/", "/fake-data"})
    public String getFakeData(Model model) {
        model.addAttribute("selected", List.of());
        return "fake-data";
    }

    @PostMapping("/fake-data")
    public String postFakeData(
            @RequestParam(value = "entriesQty") int entriesQty,
            @RequestParam(value = "additionalFields", required = false) EnumSet<AdditionalFields> additionalFields,
            Locale locale,
            Model model
    ) {
        try {
            additionalFields = additionalFields != null ? additionalFields : EnumSet.noneOf(AdditionalFields.class);
            List<PersonDto> generatedPeople = fakeDataService.generatePeople(
                    entriesQty,
                    locale,
                   additionalFields
            );
            model.addAttribute("people", generatedPeople);
        } catch (Exception ex) {
            model.addAttribute("error", "An error occurred while generating data: " + ex.getMessage());
        }
        return "fake-data";
    }

}
