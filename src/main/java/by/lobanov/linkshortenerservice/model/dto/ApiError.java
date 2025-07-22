package by.lobanov.linkshortenerservice.model.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.springframework.http.*;

import java.time.*;

@Getter
public class ApiError {

    private final int status;
    private final String error;
    private final String message;
    private final String path;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private final LocalDateTime timestamp;

    public ApiError(HttpStatus httpStatus, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = httpStatus.value();
        this.error = httpStatus.getReasonPhrase();
        this.message = message;
        this.path = path;
    }
}