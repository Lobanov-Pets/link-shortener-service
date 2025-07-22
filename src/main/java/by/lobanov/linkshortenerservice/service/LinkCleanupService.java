package by.lobanov.linkshortenerservice.service;

import by.lobanov.linkshortenerservice.repository.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;

import java.time.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class LinkCleanupService {

    private final LinkRepository linkRepository;

    @Scheduled(cron = "0 0 * * * *")
    public void cleanupExpiredLinks() {
        log.info("Starting expired links cleanup job...");
        LocalDateTime now = LocalDateTime.now();
        linkRepository.deleteExpiredLinks(now);
        log.info("Finished expired links cleanup job.");
    }
}