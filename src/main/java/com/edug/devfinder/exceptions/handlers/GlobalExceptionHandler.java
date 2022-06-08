package com.edug.devfinder.exceptions.handlers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.edug.devfinder.exceptions.ApplicationException;
import com.edug.devfinder.exceptions.ApplicationExternalException;
import com.edug.devfinder.messages.ApplicationMessage;
import com.edug.devfinder.messages.MessagesEnum;
import com.edug.devfinder.messages.parts.TypeMismatchDetails;
import com.edug.devfinder.messages.parts.UnsupportedHttpMethodDetails;
import com.edug.devfinder.messages.parts.ValidationExceptionDetails;
import com.edug.devfinder.utils.LoggerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.ClassUtils;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(ClassUtils.getUserClass(this.getClass()));

    @ResponseBody
    @ExceptionHandler(value = {ResourceAccessException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, List<ApplicationMessage>> handleNotFoundException(ResourceAccessException e) {
        LoggerUtil.logError(log, e);
        return errors(ApplicationMessage.parse(MessagesEnum.EXTERNAL_RESOURCE_ACCESS_FAILED, e));
    }

    @ResponseBody
    @ExceptionHandler(value = {NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, List<ApplicationMessage>> handleNotFoundException(NoHandlerFoundException e) {
        LoggerUtil.logError(log, e);
        return errors(ApplicationMessage.parse(MessagesEnum.RESOURCE_NOT_FOUND, e));
    }

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, List<ApplicationMessage>> handleConstraintViolationException(ConstraintViolationException e) {
        LoggerUtil.logError(log, e);
        List<ApplicationMessage> messages = new LinkedList<>();
        for (ConstraintViolation<?> cv : e.getConstraintViolations()) {
            messages.add(ApplicationMessage.parse(MessagesEnum.VALIDATION_ERROR, cv.getMessage()));
        }
        return errors(messages);
    }

    /* body parameter validation */
    @ResponseBody
    @ExceptionHandler(value={BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, List<ApplicationMessage>> handleMethodArgumentNotValidException(BindException e) {
        LoggerUtil.logError(log, e);
        List<ApplicationMessage> messages = new LinkedList<>();
        e.getBindingResult().getFieldErrors().forEach((fieldError) -> {
            var message = ApplicationMessage.parse(MessagesEnum.VALIDATION_ERROR, ValidationExceptionDetails.parse(fieldError));
            messages.add(message);
        });
        return errors(messages);
    }

    /* path parameter type validation */
    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, List<ApplicationMessage>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        LoggerUtil.logError(log, e);
        return errors(List.of(
                ApplicationMessage.parse(MessagesEnum.TYPE_MISMATCH_REQUEST, TypeMismatchDetails.parse(e))));
    }

    /* path parameter not found */
    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, List<ApplicationMessage>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        LoggerUtil.logError(log, e);
        return errors(ApplicationMessage.parse(MessagesEnum.REQUEST_METHOD_NOT_SUPPORTED, e, UnsupportedHttpMethodDetails.parse(e)));
    }

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, List<ApplicationMessage>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        LoggerUtil.logError(log, e);
        return errors(ApplicationMessage.parse(MessagesEnum.MALFORMED_JSON_REQUEST, e));
    }

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, List<ApplicationMessage>> handleInvalidRefreshTokenException(JWTVerificationException e) {
        LoggerUtil.logError(log, e);
        return errors(ApplicationMessage.parse(MessagesEnum.INVALID_JWT_TOKEN, e));
    }

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public Map<String, List<ApplicationMessage>> handleApplicationException(ApplicationException e) {
        LoggerUtil.logError(log, e);
        return errors(ApplicationMessage.parse(e.getMessageEnum()));
    }

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public Map<String, List<ApplicationMessage>> handleApplicationException(EntityNotFoundException e) {
        LoggerUtil.logError(log, e);
        return errors(ApplicationMessage.parse(e));
    }

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, List<ApplicationMessage>> handleApplicationInternalException(ApplicationExternalException e) {
        var throwable = e.getCause() != null ? e.getCause() : e;
        LoggerUtil.logError(log, throwable, e.getReason());
        return errors(ApplicationMessage.parse(e));
    }

    @ResponseBody
    @ExceptionHandler(value = {HttpMediaTypeNotSupportedException.class, HttpMediaTypeNotAcceptableException.class,
            MissingPathVariableException.class, MissingServletRequestParameterException.class,
            ServletRequestBindingException.class, ConversionNotSupportedException.class,
            TypeMismatchException.class, HttpMessageNotWritableException.class,
            MissingServletRequestPartException.class, AsyncRequestTimeoutException.class }
//            Exception.class}
    )
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, List<ApplicationMessage>> handleException(Exception e) {
        LoggerUtil.logError(log, e);
        return errors(ApplicationMessage.getDefault());
    }

    private Map<String, List<ApplicationMessage>> errors(List<ApplicationMessage> messages) {
        return Collections.singletonMap("Errors", messages);
    }

    private Map<String, List<ApplicationMessage>> errors(ApplicationMessage message) {
        return Collections.singletonMap("Errors", List.of(message));
    }

}
