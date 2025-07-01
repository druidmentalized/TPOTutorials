package org.project.controllers;

import jakarta.validation.Valid;
import org.project.dto.LinkFormDTO;
import org.project.exceptions.NoSuchLinkException;
import org.project.services.LinkService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return LINK_CREATE;

        LinkFormDTO dto = linkService.createNew(linkFormDTO);
        return REDIRECT + LINKS + "info?id=" + dto.getId() + "&password=" + linkFormDTO.getPassword();
    }

    // * View link details
    @GetMapping("/info")
    public String linkInfoForm(@RequestParam String id,
                               @RequestParam String password,
                               Model model) {
        try {
            if (!linkService.verifyPassword(id, password)) {
                model.addAttribute(ERROR_ATTRIBUTE, "Invalid password.");
                return REDIRECT;
            }
            LinkFormDTO dto = linkService.getByIdAsFormDto(id);
            model.addAttribute("link", dto);
            return LINK_INFO;
        } catch (NoSuchLinkException e) {
            model.addAttribute(ERROR_ATTRIBUTE, e.getMessage());
            return REDIRECT;
        }
    }

    // * Edit a link
    @GetMapping("/edit")
    public String editLinkForm(@RequestParam String id, Model model) {
        try {
            LinkFormDTO dto = linkService.getByIdAsFormDto(id);
            model.addAttribute("link", dto);
            return LINK_EDIT;
        } catch (NoSuchLinkException e) {
            model.addAttribute(ERROR_ATTRIBUTE, e.getMessage());
            return REDIRECT;
        }
    }

    @PostMapping("/edit")
    public String editLink(@Valid @ModelAttribute(name = "link") LinkFormDTO dto,
                           BindingResult bindingResult,
                           Model model) {
        if (!bindingResult.hasErrors() &&
                linkService.existsByTargetUrlAndIdNot(dto.getTargetUrl(), dto.getId())) {
            bindingResult.rejectValue("targetUrl", null, "validation.url.notUnique");
        }
        else if (bindingResult.hasErrors()) {
            model.addAttribute("link", dto);
            return LINK_EDIT;
        }

        try {
            linkService.update(dto);
            return REDIRECT + LINKS + "info?id=" + dto.getId() + "&password=" + dto.getPassword();
        } catch (NoSuchLinkException e) {
            model.addAttribute(ERROR_ATTRIBUTE, e.getMessage());
            return LINK_EDIT;
        }
    }

    // * Delete a link
    @PostMapping("/delete")
    public String deleteLink(@RequestParam String id) {
        linkService.deleteByIdNoPass(id);
        return REDIRECT;
    }
}
