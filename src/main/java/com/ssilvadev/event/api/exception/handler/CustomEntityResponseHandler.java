package com.ssilvadev.event.api.exception.handler;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ssilvadev.event.api.exception.ExceptionResponse;
import com.ssilvadev.event.api.exception.RequiredNonNullObject;

@ControllerAdvice
@RestController
public class CustomEntityResponseHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<ExceptionResponse> handlerIllegalArgumentException(Exception e, WebRequest request) {
        var response = new ExceptionResponse(
                new Date(),
                e.getMessage(),
                request.getDescription(false));

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handlerAllException(Exception e, WebRequest request) {
        var response = new ExceptionResponse(
                new Date(),
                e.getMessage(),
                request.getDescription(false));

        return ResponseEntity.internalServerError().body(response);
    }

    @ExceptionHandler(RequiredNonNullObject.class)
    public final ResponseEntity<ExceptionResponse> handlerRequiredNonNullObject(Exception e, WebRequest request) {
        var response = new ExceptionResponse(
                new Date(),
                e.getMessage(),
                request.getDescription(false));

        return ResponseEntity.internalServerError().body(response);
    }

}
