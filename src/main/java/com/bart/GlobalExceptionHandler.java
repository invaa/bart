package com.bart;

import com.bart.exception.CartException;
import com.bart.exception.ConversionException;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.net.BindException;
import java.util.Collections;
import java.util.Map;

@ControllerAdvice
@Log4j
public class GlobalExceptionHandler {
    @ExceptionHandler(CartException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map handle(final CartException exception) {
        log.error(exception.getMessage(), exception);
        return errorResponse(exception.getMessage());
    }

    private Map errorResponse(final String message) {
        return Collections.singletonMap("error", message);
    }

    @ExceptionHandler(ConversionException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map handle(final ConversionException exception) {
        log.error(exception.getMessage(), exception);
        return errorResponse(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map handle(final MethodArgumentNotValidException exception) {
        log.error(exception.getMessage(), exception);
        return errorResponse(exception.getMessage());
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map handle(final BindException exception) {
        log.error(exception.getMessage(), exception);
        return errorResponse(exception.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map handle(final ConstraintViolationException exception) {
        log.error(exception.getMessage(), exception);
        return errorResponse(exception.getMessage());
    }
}
