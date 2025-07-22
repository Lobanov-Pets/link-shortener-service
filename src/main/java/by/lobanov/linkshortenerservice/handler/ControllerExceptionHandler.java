package by.lobanov.linkshortenerservice.handler;


import by.lobanov.linkshortenerservice.exception.*;
import by.lobanov.linkshortenerservice.model.dto.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.*;
import org.springframework.web.servlet.mvc.method.annotation.*;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(LinkNotFoundException.class)
    public ResponseEntity<Object> handleLinkNotFound(LinkNotFoundException ex, WebRequest request) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), path);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LinkAliasExistException.class)
    public ResponseEntity<Object> handleAliasExistException(LinkAliasExistException ex, WebRequest request) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), path);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
}
