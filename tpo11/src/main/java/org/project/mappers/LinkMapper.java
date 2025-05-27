package org.project.mappers;


import org.project.config.LinkProperties;
import org.project.dto.LinkFormDTO;
import org.project.dto.RequestCreateLinkDTO;
import org.project.dto.ResponseLinkDTO;
import org.project.entities.Link;
import org.project.repositories.LinkRepository;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class LinkMapper {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final Random random = new Random();

    private final LinkProperties linkProperties;
    private final LinkRepository linkRepository;

    public LinkMapper(LinkProperties linkProperties, LinkRepository linkRepository) {
        this.linkProperties = linkProperties;
        this.linkRepository = linkRepository;
    }

    public ResponseLinkDTO toResponseDto(Link link) {
        return new ResponseLinkDTO(
                link.getId(),
                link.getName(),
                link.getTargetUrl(),
                constructRedirectUrl(link.getId()),
                link.getVisits()
        );
    }

    public LinkFormDTO toFormDto(Link link) {
        return new LinkFormDTO(
                link.getId(),
                link.getName(),
                link.getTargetUrl(),
                constructRedirectUrl(link.getId()),
                link.getPassword(),
                link.getVisits()
        );
    }

    public Link toEntity(RequestCreateLinkDTO dto) {
        return new Link(
                generateUniqueId(),
                dto.getName(),
                dto.getTargetUrl(),
                dto.getPassword(),
                0L
        );
    }

    public Link toEntity(LinkFormDTO dto) {
        return new Link(
                generateUniqueId(),
                dto.getName(),
                dto.getTargetUrl(),
                dto.getPassword(),
                0L
        );
    }

    private String generateUniqueId() {
        String id;

        do {
            id = generateRandomId();
        } while (linkRepository.existsById(id));

        return id;
    }

    private String generateRandomId() {
        StringBuilder idBuilder = new StringBuilder();
        for (int i = 0; i < linkProperties.getIdLength(); i++) {
            idBuilder.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        return idBuilder.toString();
    }

    private String constructRedirectUrl(String linkId) {
        return linkProperties.getHost() + linkProperties.getRedirectPath() + linkId;
    }
}
