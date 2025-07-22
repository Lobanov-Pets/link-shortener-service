package by.lobanov.linkshortenerservice.controller;

import by.lobanov.linkshortenerservice.exception.*;
import by.lobanov.linkshortenerservice.model.dto.*;
import by.lobanov.linkshortenerservice.model.entity.*;
import by.lobanov.linkshortenerservice.service.*;
import jakarta.servlet.http.*;
import lombok.*;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.*;

import static by.lobanov.linkshortenerservice.util.ShortenerMessages.LINK_NOT_FOUND_MESSAGE;

@RestController
@RequiredArgsConstructor
public class LinkController {

    private final LinkService linkService;

    @GetMapping("/api/links")
    public ResponseEntity<Page<LinkDto>> getAllLinks(PageableRequest pageableRequest, HttpServletRequest request) {
        Page<Link> linkPage = linkService.getAllLinks(PageRequest.of(pageableRequest.getOffset(), pageableRequest.getLimit()));

        String baseUrl = getBaseUrlFromRequest(request);

        Page<LinkDto> dtoPage = linkPage.map(link -> new LinkDto(
                baseUrl + "/" + link.getShortCode(),
                link.getLongUrl(),
                link.getCreatedAt(),
                link.getExpiresAt()
        ));

        return ResponseEntity.ok(dtoPage);
    }

    @PostMapping("/api/links")
    public ResponseEntity<CreateLinkResponse> createShortLink(@RequestBody CreateLinkRequest request, HttpServletRequest servletRequest) {
        if (request.getLongUrl() == null || request.getLongUrl().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Link newLink = linkService.createShortLink(request);

        String baseUrl = servletRequest.getRequestURL().toString().replace(servletRequest.getRequestURI(), "");
        String shortUrl = baseUrl + "/" + newLink.getShortCode();

        return new ResponseEntity<>(new CreateLinkResponse(shortUrl), HttpStatus.CREATED);
    }

    @PostMapping("/api/links/alias")
    public ResponseEntity<CreateLinkResponse> createShortWithAliasLink(@RequestBody CreateLinkWithAliasRequest request, HttpServletRequest servletRequest) {
        if (request.getLongUrl() == null || request.getLongUrl().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Link newLink = linkService.createShortLinkWithAlias(request);

        String baseUrl = servletRequest.getRequestURL().toString().replace(servletRequest.getRequestURI(), "");
        String shortUrl = baseUrl + "/" + newLink.getShortCode();

        return new ResponseEntity<>(new CreateLinkResponse(shortUrl), HttpStatus.CREATED);
    }

    @GetMapping("/{shortCode}")
    public RedirectView redirectToLongLink(@PathVariable String shortCode) {
        Link link = linkService.getLongLink(shortCode)
                .orElseThrow(() -> new LinkNotFoundException(String.format(LINK_NOT_FOUND_MESSAGE,  shortCode)));

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(link.getLongUrl());
        return redirectView;
    }

    private String getBaseUrlFromRequest(HttpServletRequest request) {
        String requestUrl = request.getRequestURL().toString();
        return requestUrl.substring(0, requestUrl.length() - request.getRequestURI().length());
    }
}