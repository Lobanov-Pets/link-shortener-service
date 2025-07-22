package by.lobanov.linkshortenerservice.model.dto;

import lombok.*;

@Data
public class CreateLinkRequest {
    private String longUrl;
    private Integer ttlInMinutes;
}
