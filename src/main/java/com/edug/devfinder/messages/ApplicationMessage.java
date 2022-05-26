package com.edug.devfinder.messages;

import com.edug.devfinder.exceptions.ApplicationExternalException;
import com.edug.devfinder.messages.parts.TypeMismatchDetails;
import com.edug.devfinder.messages.parts.UnsupportedHttpMethodDetails;
import com.edug.devfinder.messages.parts.ValidationExceptionDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter @Setter @Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("Code")
    private Integer code;

    @JsonProperty("Message")
    private String message;

    @JsonIgnore
    private Exception exception;

    @JsonProperty("Exception")
    private String getExceptionMessage() {
        return this.exception == null ? null : this.exception.getLocalizedMessage();
    }

    // TODO: REMOVER EXTRA E ENCAPSULAR O CONTEÚDO EM ALGUM OUTRO LUGAR
    @JsonProperty("Extra")
    private String extra;

    @JsonProperty("Details")
    private Details details;

    public static ApplicationMessage parse(MessagesEnum messageEnum, String extra) {
        return ApplicationMessage.builder()
                .code(messageEnum.getCode())
                .message(messageEnum.getMessage())
                .extra(extra)
                .build();
    }

    public static ApplicationMessage parse(Exception exception) {
        return ApplicationMessage.builder()
                .code(MessagesEnum.DEFAULT_ERROR.getCode())
                .message(MessagesEnum.DEFAULT_ERROR.getMessage())
                .exception(exception)
                .build();
    }

    public static ApplicationMessage parse(ApplicationExternalException extendedException) {
        return ApplicationMessage.builder()
                .code(extendedException.getMessageEnum().getCode())
                .message(extendedException.getMessageEnum().getMessage())
                .details(new Details(extendedException.getExternalResponseDetails()))
                .exception(extendedException.getCause() != null ? (Exception) extendedException.getCause() : null)
                .build();
    }

    public static ApplicationMessage parse(MessagesEnum messageEnum, Exception exception) {
        return ApplicationMessage.builder()
                .code(messageEnum.getCode())
                .message(messageEnum.getMessage())
                .exception(exception)
                .build();
    }

    public static ApplicationMessage parse(MessagesEnum messageEnum, Exception exception, UnsupportedHttpMethodDetails unsupportedHttpMethodDetails) {
        return ApplicationMessage.builder()
                .code(messageEnum.getCode())
                .message(messageEnum.getMessage())
                .exception(exception)
                .details(new Details(unsupportedHttpMethodDetails))
                .build();
    }

    public static ApplicationMessage parse(MessagesEnum messageEnum, ValidationExceptionDetails validationExceptionDetails) {
        return ApplicationMessage.builder()
                .code(messageEnum.getCode())
                .message(messageEnum.getMessage())
                .details(new Details(validationExceptionDetails))
                .build();
    }

    public static ApplicationMessage parse(MessagesEnum messageEnum, TypeMismatchDetails typeMissmatchDetails) {
        return ApplicationMessage.builder()
                .code(messageEnum.getCode())
                .message(messageEnum.getMessage())
                .details(new Details(typeMissmatchDetails))
                .build();
    }

}


