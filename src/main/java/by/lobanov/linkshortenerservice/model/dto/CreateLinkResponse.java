package by.lobanov.linkshortenerservice.model.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class CreateLinkResponse {
    private String shortUrl;
}
