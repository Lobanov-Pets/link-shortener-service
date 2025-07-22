package by.lobanov.linkshortenerservice.service;

import by.lobanov.linkshortenerservice.exception.*;
import by.lobanov.linkshortenerservice.model.dto.*;
import by.lobanov.linkshortenerservice.model.entity.Link;
import by.lobanov.linkshortenerservice.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.*;

import static by.lobanov.linkshortenerservice.util.ShortenerMessages.ALIAS_ALREADY_EXIST_MESSAGE;

@Service
@RequiredArgsConstructor
public class LinkService {

    private static final int SHORT_CODE_LENGTH = 7;

    private static final RandomStringGenerator CODE_GENERATOR  = new RandomStringGenerator.Builder()
            .withinRange('0', 'z')
            .filteredBy(Character::isLetterOrDigit)
            .get();

    private final LinkRepository linkRepository;

    @Transactional(readOnly = true)
    public Page<Link> getAllLinks(Pageable pageable) {
        return linkRepository.findAll(pageable);
    }

    @Transactional
    public Link createShortLink(CreateLinkRequest request) {
        String shortCode;
        do {
            shortCode = CODE_GENERATOR.generate(SHORT_CODE_LENGTH);
        } while (linkRepository.findByShortCode(shortCode).isPresent());

        Link newLink = new Link(shortCode, request.getLongUrl());

        if (request.getTtlInMinutes() != null && request.getTtlInMinutes() > 0) {
            newLink.setExpiresAt(LocalDateTime.now().plusMinutes(request.getTtlInMinutes()));
        }

        return linkRepository.save(newLink);
    }

    @Transactional
    public Link createShortLinkWithAlias (CreateLinkWithAliasRequest request) {
        if (linkRepository.findByShortCode(request.getAlias()).isPresent()) {
            throw new LinkAliasExistException(String.format(ALIAS_ALREADY_EXIST_MESSAGE, request.getAlias()));
        }

        Link newLink = new Link(request.getAlias(), request.getLongUrl());

        if (request.getTtlInMinutes() != null && request.getTtlInMinutes() > 0) {
            newLink.setExpiresAt(LocalDateTime.now().plusMinutes(request.getTtlInMinutes()));
        }

        return linkRepository.save(newLink);
    }

    @Transactional(readOnly = true)
    public Optional<Link> getLongLink(String shortCode) {
        return linkRepository.findByShortCode(shortCode);
    }
}