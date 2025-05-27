package org.project.controllers;

import org.project.dto.LinkFormDTO;
import org.project.services.LinkService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String createLink(@ModelAttribute(name = "link") LinkFormDTO linkFormDTO) {
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
    public String editLink(@ModelAttribute(name = "link") LinkFormDTO dto) {
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
