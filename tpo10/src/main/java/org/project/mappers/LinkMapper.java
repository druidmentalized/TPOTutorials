package org.project.mappers;


import org.project.config.LinkProperties;
import org.project.dto.RequestCreateLinkDTO;
import org.project.dto.ResponseLinkDTO;
import org.project.entities.Link;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class LinkMapper {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final Random random = new Random();

    private final LinkProperties linkProperties;

    public LinkMapper(LinkProperties linkProperties) {
        this.linkProperties = linkProperties;
    }

    public ResponseLinkDTO toResponseDto(Link link) {
        return new ResponseLinkDTO(
                link.getId(),
                link.getName(),
                link.getTargetUrl(),
                linkProperties.getHost() + linkProperties.getRedirectPath() + link.getId(),
                link.getVisits()
        );
    }

    public Link toEntity(RequestCreateLinkDTO dto) {
        return new Link(
                generateRandomId(),
                dto.getName(),
                dto.getTargetUrl(),
                dto.getPassword(),
                0L
        );
    }

    private String generateRandomId() {
        StringBuilder idBuilder = new StringBuilder();
        for (int i = 0; i < linkProperties.getIdLength(); i++) {
            idBuilder.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        return idBuilder.toString();
    }
}
