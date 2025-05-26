package org.project.services;

import org.project.dto.LinkFormDTO;
import org.project.dto.RequestCreateLinkDTO;
import org.project.dto.RequestUpdateLinkDTO;
import org.project.dto.ResponseLinkDTO;
import org.project.entities.Link;
import org.project.exceptions.InvalidPasswordException;
import org.project.exceptions.NoSuchLinkException;
import org.project.mappers.LinkMapper;
import org.project.repositories.LinkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
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

    public LinkFormDTO createNew(LinkFormDTO dto) {
        return null;
        //TODO: implement
    }


    public ResponseLinkDTO getByIdAsResponseDto(String id) {
        return getByIdAsResponseDto(id, false);
    }

    public ResponseLinkDTO getByIdAsResponseDto(String id, boolean toVisit) {
        Optional<Link> linkOptional = linkRepository.findById(id);

        if (linkOptional.isPresent()) {
            Link link = linkOptional.get();
            if (toVisit) link.setVisits(link.getVisits() + 1);
            return linkMapper.toResponseDto(link);
        } else throw new NoSuchLinkException("No link with given id exists.");
    }

    public LinkFormDTO getByIdAsFormDto(String id) {
        return null;
        //TODO: implement
    }

    public void updateById(String id, RequestUpdateLinkDTO dto) {
        Optional<Link> linkOptional = linkRepository.findById(id);
        if (linkOptional.isEmpty())
            throw new NoSuchLinkException("No link with given id exists.");

        Link link = linkOptional.get();

        String stored = link.getPassword();
        String given = dto.getPassword();
        if (stored == null || given == null || !stored.equals(given)) {
            throw new InvalidPasswordException("wrong password");
        }

        link.setName(dto.getName());

        linkRepository.save(link);
    }

    public void update(LinkFormDTO dto) {
        //TODO: implement
    }

    public void deleteById(String id, String pass) {
        Optional<Link> linkOptional = linkRepository.findById(id);
        if (linkOptional.isEmpty()) return;

        Link link = linkOptional.get();

        String stored = link.getPassword();
        if (stored == null || pass == null || !stored.equals(pass)) {
            throw new InvalidPasswordException("wrong password");
        }

        linkRepository.delete(link);
    }

    public boolean verifyPassword(String password1, String password2) {
        return password1.equals(password2);
        //TODO: remake to use hash instead
    }
}
