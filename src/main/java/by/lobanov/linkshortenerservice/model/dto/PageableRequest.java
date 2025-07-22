package by.lobanov.linkshortenerservice.model.dto;

import lombok.*;

@Data
public class PageableRequest {
    private Integer offset;
    private Integer limit;
}
