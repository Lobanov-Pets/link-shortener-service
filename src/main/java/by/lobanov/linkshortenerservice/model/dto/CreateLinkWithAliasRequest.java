package by.lobanov.linkshortenerservice.model.dto;

import lombok.*;

@Data
public class CreateLinkWithAliasRequest {
    private String longUrl;
    private String alias;
    private Integer ttlInMinutes;
}
