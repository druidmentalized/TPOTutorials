package org.project.controllers;

import org.project.models.PersonDto;
import org.project.services.FakeDataService;
import org.project.enums.AdditionalFields;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.EnumSet;
import java.util.List;

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
            @RequestParam(value = "language") String language,
            @RequestParam(value = "additionalFields", required = false) EnumSet<AdditionalFields> additionalFields,
            Model model
    ) {
        try {
            List<PersonDto> generatedPeople = fakeDataService.generatePersons(
                    entriesQty,
                    language,
                    additionalFields != null ? additionalFields : EnumSet.noneOf(AdditionalFields.class)
            );

            model.addAttribute("people", generatedPeople);
            model.addAttribute("selected", additionalFields);
            return "fake-data";
        } catch (Exception ex) {
            model.addAttribute("error", "An error occurred while generating data: " + ex.getMessage());
            model.addAttribute("selected", List.of());
            return "fake-data";
        }
    }

}
