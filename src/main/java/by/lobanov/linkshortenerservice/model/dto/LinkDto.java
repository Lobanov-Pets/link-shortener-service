package by.lobanov.linkshortenerservice.model.dto;

import java.time.*;

public record LinkDto(
        String shortUrl,
        String longUrl,
        LocalDateTime createdAt,
        LocalDateTime expiresAt
) {
}