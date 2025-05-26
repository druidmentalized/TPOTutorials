package org.project.services;

import org.project.dto.RequestCreateLinkDTO;
import org.project.dto.ResponseLinkDTO;
import org.project.entities.Link;
import org.project.exceptions.NoSuchLinkException;
import org.project.mappers.LinkMapper;
import org.project.repositories.LinkRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LinkService {
    private final LinkRepository linkRepository;
    private final LinkMapper linkMapper;

    public LinkService(LinkRepository linkRepository, LinkMapper linkMapper) {
        this.linkRepository = linkRepository;
        this.linkMapper = linkMapper;
    }

    public ResponseLinkDTO createNew(RequestCreateLinkDTO dto) {
        Link newLink = linkMapper.toEntity(dto);
        linkRepository.save(newLink);
        return linkMapper.toResponseDto(newLink);
    }

    public ResponseLinkDTO getById(String id) {
        return getById(id, false);
    }

    public ResponseLinkDTO getById(String id, boolean toVisit) {
        Optional<Link> linkOptional =  linkRepository.findById(id);

        if (linkOptional.isPresent()) {
            Link link = linkOptional.get();
            if (toVisit) link.setVisits(link.getVisits() + 1);
            return linkMapper.toResponseDto(link);
        }
        else throw new NoSuchLinkException("No link with given id exists.");
    }
}
