package net.skai.chefplaneteapi.exception;

import net.skai.chefplaneteapi.domain.response.ApiErrorResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Bean
    public ErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes(@NotNull final WebRequest webRequest,
                                                          final boolean includeStackTrace) {
                final Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
                errorAttributes.remove("exception");
                return errorAttributes;
            }
        };
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(@NotNull final UsernameNotFoundException ex) {
        logger.error(ex.getMessage(), ex);
        final ApiErrorResponse errorResponse = new ApiErrorResponse(
                HttpStatus.UNAUTHORIZED,
                ex.getMessage()
        );
        return buildResponse(errorResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(@NotNull final UserNotFoundException ex) {
        logger.error(ex.getMessage(), ex);
        final ApiErrorResponse errorResponse = new ApiErrorResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
        return buildResponse(errorResponse);
    }

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<Object> handleUserExistsException(@NotNull final UserExistsException ex) {
        logger.error(ex.getMessage(), ex);
        final ApiErrorResponse errorResponse = new ApiErrorResponse(
                HttpStatus.CONFLICT,
                ex.getMessage()
        );
        return buildResponse(errorResponse);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(@NotNull final AuthenticationException ex){
        logger.error(ex.getMessage(), ex);
        final ApiErrorResponse errorResponse = new ApiErrorResponse(
                HttpStatus.UNAUTHORIZED,
                ex.getMessage()
        );
        return buildResponse(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        logger.error(ex.getMessage(), ex);
        final ApiErrorResponse errorResponse = new ApiErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Looks like something went wrong: " + ex.getMessage());
        return buildResponse(errorResponse);
    }

    private static ResponseEntity<Object> buildResponse(@NotNull final ApiErrorResponse apiErrorResponse) {
        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.getStatus());
    }
}
