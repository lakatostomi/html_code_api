package com.example.demo.restapi.exception.handler;

import com.example.demo.restapi.exception.ConstructorInvocationException;
import com.example.demo.restapi.exception.RequiredArgumentMissingException;
import com.example.demo.restapi.exception.UnsupportedElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class RestExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler({UnsupportedElementException.class, RequiredArgumentMissingException.class, MissingPathVariableException.class, MissingServletRequestParameterException.class})
    protected ProblemDetail handleHttpBadRequest(Exception ex) {
        ProblemDetail problemDetail = createProblemDetail(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        log.warn(problemDetail.toString());
        return problemDetail;
    }

    @ExceptionHandler({NoSuchElementException.class})
    protected ProblemDetail handleHttpNotFound(Exception ex) {
        ProblemDetail problemDetail = createProblemDetail(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        log.warn(problemDetail.toString());
        return problemDetail;
    }

    @ExceptionHandler({ConstructorInvocationException.class, RuntimeException.class})
    protected ProblemDetail handleConstructorInvocation(Exception ex) {
        ProblemDetail problemDetail = createProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        log.warn(problemDetail.toString());
        return problemDetail;
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    protected ProblemDetail handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        ProblemDetail problemDetail = createProblemDetail(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), ex.getMessage());
        log.warn(problemDetail.toString());
        return problemDetail;
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ProblemDetail handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        ProblemDetail problemDetail = createProblemDetail(HttpStatus.METHOD_NOT_ALLOWED.value(), ex.getMessage());
        log.warn(problemDetail.toString());
        return problemDetail;
    }

    private ProblemDetail createProblemDetail(int statusCode, String message) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(statusCode), message);
        problemDetail.setProperty("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return problemDetail;
    }
}
