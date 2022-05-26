package com.edug.devfinder.messages.parts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.FieldError;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidationExceptionDetails {

    @JsonProperty("ObjectName")
    private String objectName;

    @JsonProperty("FieldName")
    private String fieldName;

    @JsonProperty("DefaultMessage")
    private String defaultMessage;

    public static ValidationExceptionDetails parse(FieldError e) {
        return new ValidationExceptionDetails(e.getObjectName(), e.getField(), e.getDefaultMessage());
    }
}
