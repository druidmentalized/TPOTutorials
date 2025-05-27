package org.project.controllers;

import jakarta.validation.Valid;
import org.project.dto.LinkFormDTO;
import org.project.services.LinkService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO: implement exception handling for all methods in this controller
//TODO: implement styling for css files

@Controller
@RequestMapping("links/")
public class WebLinkController {
    private static final String LINK_CREATE = "link-create";
    private static final String LINK_EDIT = "link-edit";
    private static final String LINK_INFO = "link-info";

    private static final String REDIRECT = "redirect:/";
    private static final String LINKS = "links/";

    private static final String ERROR_ATTRIBUTE = "error";


    private final LinkService linkService;

    public WebLinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    // * Create a link
    @GetMapping("/create")
    public String createLinkForm(Model model) {
        model.addAttribute("link", new LinkFormDTO());

        return LINK_CREATE;
    }

    @PostMapping("/create")
    public String createLink(@Valid @ModelAttribute(name = "link") LinkFormDTO linkFormDTO,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();

            model.addAttribute(ERROR_ATTRIBUTE, errors);
            return LINK_CREATE;
        }

        LinkFormDTO dto = linkService.createNew(linkFormDTO);
        return REDIRECT + LINKS + "info?id=" + dto.getId() + "&password=" + linkFormDTO.getPassword();
    }

    // * View link details
    @GetMapping("/info")
    public String linkInfoForm(@RequestParam String id,
                               @RequestParam String password,
                               Model model) {
        if (linkService.verifyPassword(id, password)) {
            model.addAttribute("link", linkService.getByIdAsFormDto(id));
            return LINK_INFO;
        }
        else {
            model.addAttribute(ERROR_ATTRIBUTE, "Invalid password.");
            return REDIRECT;
        }
    }

    // * Edit a link
    @GetMapping("/edit")
    public String editLinkForm(@RequestParam String id, Model model) {
        model.addAttribute("link", linkService.getByIdAsFormDto(id));
        return LINK_EDIT;
    }

    @PostMapping("/edit")
    public String editLink(@Valid @ModelAttribute(name = "link") LinkFormDTO dto,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();

            model.addAttribute(ERROR_ATTRIBUTE, errors);
            return LINK_EDIT;
        }
        linkService.update(dto);
        return REDIRECT + LINKS + "info?id=" + dto.getId() + "&password=" + dto.getPassword();
    }

    // * Delete a link
    @PostMapping("/delete")
    public String deleteLink(@RequestParam String id) {
        linkService.deleteByIdNoPass(id);
        return REDIRECT;
    }
}
