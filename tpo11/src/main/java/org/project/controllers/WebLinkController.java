package org.project.controllers;

import org.project.dto.LinkActionDTO;
import org.project.dto.LinkFormDTO;
import org.project.exceptions.InvalidPasswordException;
import org.project.services.LinkService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//TODO: implement exception handling for all methods in this controller
//TODO: implement styling for css files

@Controller
public class WebLinkController {
    private static final String LINK_CREATE = "link-create";
    private static final String LINK_EDIT = "link-edit";
    private static final String LINK_INFO = "link-info";
    private static final String LINK_CONFIRM = "link-confirm";

    private static final String REDIRECT = "redirect:/";
    private static final String LINKS = "links/";

    private static final String ERROR_ATTRIBUTE = "error";


    private final LinkService linkService;

    public WebLinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    // * Create a link
    @GetMapping("links/create")
    public String createLinkForm(Model model) {
        model.addAttribute("link", new LinkFormDTO());

        return LINK_CREATE;
    }

    @PostMapping("links/create")
    public String createLink(@ModelAttribute(name = "link") LinkFormDTO linkFormDTO, Model model) {
        LinkFormDTO dto = linkService.createNew(linkFormDTO);
        model.addAttribute("link", dto);
        return LINK_INFO;
    }

    // * View link details
    @GetMapping("links/info")
    public String linkInfoForm(@RequestParam String id, Model model) {
        model.addAttribute("link", linkService.getByIdAsFormDto(id));
        return LINK_INFO;
    }

    // * Edit a link
    @GetMapping("links/edit")
    public String editLinkForm(@RequestParam String id, Model model) {
        model.addAttribute("link", linkService.getByIdAsFormDto(id));
        return LINK_EDIT;
    }

    @PostMapping("links/edit")
    public String editLink(@ModelAttribute(name = "link") LinkFormDTO dto) {
        linkService.update(dto);
        return REDIRECT + LINKS + "info?id=" + dto.getId();
    }

    // * Confirm sensitive action
    @GetMapping("links/confirm")
    public String confirmLinkForm(@RequestParam String id,
                                  @RequestParam String action,
                                  Model model) {
        model.addAttribute("linkAction", new LinkActionDTO(id, action));
        return LINK_CONFIRM;
    }

    @PostMapping("links/execute")
    public String executeAction(@ModelAttribute(name = "linkAction") LinkActionDTO linkActionDTO, Model model) {
        String action = linkActionDTO.getAction();
        if ("edit".equals(action)) {
            if (linkService.verifyPassword(linkActionDTO.getId(), linkActionDTO.getPassword())) {
                return REDIRECT + LINKS + "edit?id=" + linkActionDTO.getId();
            } else {
                model.addAttribute(ERROR_ATTRIBUTE, "Invalid password for editing.");
                return LINK_CONFIRM;
            }
        } else if ("delete".equals(action)) {
            try {
                linkService.deleteById(linkActionDTO.getId(), linkActionDTO.getPassword());
                return REDIRECT;
            } catch (InvalidPasswordException e) {
                model.addAttribute(ERROR_ATTRIBUTE, e.getMessage());
                return LINK_CONFIRM;
            }
        }
        return REDIRECT + LINKS + linkActionDTO.getId();
    }
}
