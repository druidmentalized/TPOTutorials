package org.project.controllers;

import org.project.dto.ResponseLinkDTO;
import org.project.exceptions.NoSuchLinkException;
import org.project.services.LinkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(value = "/red/")
public class RedirectController {
    private final LinkService linkService;

    public RedirectController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<?> redirect(@PathVariable String id) {
        try {
            ResponseLinkDTO responseLinkDTO = linkService.getById(id, true);

            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(responseLinkDTO.getTargetUrl()))
                    .build();
        }
        catch (NoSuchLinkException _) {
            return ResponseEntity.notFound().build();
        }
        catch (Exception _) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
