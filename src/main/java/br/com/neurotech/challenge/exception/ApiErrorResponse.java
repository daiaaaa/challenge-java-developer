package br.com.neurotech.challenge.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@ToString
public class ApiErrorResponse {

    private final String timestamp;
    private final int status;
    private final String error;
    private final Object message;
    private final String path;

    public ApiErrorResponse(HttpStatus status, Object message, String path) {
        this.timestamp = ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT);
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
        this.path = path;
    }
}

