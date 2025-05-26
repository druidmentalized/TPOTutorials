package org.project.controllers;

import org.project.config.LinkProperties;
import org.project.dto.RequestCreateLinkDTO;
import org.project.dto.ResponseLinkDTO;
import org.project.exceptions.NoSuchLinkException;
import org.project.services.LinkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/links")
public class LinkController {
    private final LinkService linkService;
    private final LinkProperties linkProperties;

    public LinkController(LinkService linkService, LinkProperties linkProperties) {
        this.linkService = linkService;
        this.linkProperties = linkProperties;
    }

    @PostMapping
    public ResponseEntity<ResponseLinkDTO> createNewLink(@RequestBody RequestCreateLinkDTO dto) {
        try {
            ResponseLinkDTO createdDto = linkService.createNew(dto);

            URI location = URI.create(
                    linkProperties.getHost() +
                            "api/" +
                    linkProperties.getLinksPath() +
                    createdDto.getId());

            return ResponseEntity.created(location).body(createdDto);
        }
        //TODO: make exception handling
        catch (Exception _) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseLinkDTO> getById(@PathVariable String id) {
        try {
            ResponseLinkDTO dto = linkService.getById(id);
            return ResponseEntity.ok(dto);
        }
        //TODO: make proper exception handling
        catch (NoSuchLinkException _) {
            return ResponseEntity.notFound().build();
        }
        catch (Exception _) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
