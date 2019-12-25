package net.skai.chefplaneteapi.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiErrorResponse {
    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    private String message;

    private ApiErrorResponse() {
        timestamp = LocalDateTime.now();
    }

    public ApiErrorResponse(@NotNull final HttpStatus httpStatus,
                            @NotNull final Throwable throwable) {
        this();
        this.status = httpStatus;
        this.message = throwable.getMessage();
    }

    public ApiErrorResponse(@NotNull final HttpStatus httpStatus,
                            @NotNull final String message) {
        this();
        this.status = httpStatus;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }
}
