package by.lobanov.linkshortenerservice.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LinkAliasExistException extends RuntimeException {
    public LinkAliasExistException(String message) {
        super(message);
    }
}