package org.project.controllers;

import org.project.config.LinkProperties;
import org.project.dto.RequestCreateLinkDTO;
import org.project.dto.RequestUpdateLinkDTO;
import org.project.dto.ResponseLinkDTO;
import org.project.exceptions.InvalidPasswordException;
import org.project.exceptions.NoSuchLinkException;
import org.project.services.LinkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/links")
public class ApiLinkController {
    private static final String REASON_HEADER = "reason";

    private final LinkService linkService;
    private final LinkProperties linkProperties;

    public ApiLinkController(LinkService linkService, LinkProperties linkProperties) {
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
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header(REASON_HEADER, e.getMessage()).build();
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseLinkDTO> getById(@PathVariable String id) {
        try {
            ResponseLinkDTO dto = linkService.getByIdAsResponseDto(id);
            return ResponseEntity.ok(dto);
        }
        catch (NoSuchLinkException _) {
            return ResponseEntity.notFound().build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header(REASON_HEADER, e.getMessage()).build();
        }
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Void> updateById(@PathVariable String id,
            @RequestBody RequestUpdateLinkDTO dto) {
        try {
            linkService.updateById(id, dto);
            return ResponseEntity.noContent().build();
        }
        catch (NoSuchLinkException _) {
            return ResponseEntity.notFound().build();
        }
        catch (InvalidPasswordException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).header(REASON_HEADER, e.getMessage()).build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header(REASON_HEADER, e.getMessage()).build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id,
                                           @RequestHeader(name = "pass", required = false) String pass) {
        try {
            linkService.deleteByIdWithPass(id, pass);
            return ResponseEntity.noContent().build();
        }
        catch (InvalidPasswordException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).header(REASON_HEADER, e.getMessage()).build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header(REASON_HEADER, e.getMessage()).build();
        }
    }
}
