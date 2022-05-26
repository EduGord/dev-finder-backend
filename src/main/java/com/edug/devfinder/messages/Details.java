package com.edug.devfinder.messages;

import com.edug.devfinder.messages.parts.ExternalResponseDetails;
import com.edug.devfinder.messages.parts.TypeMismatchDetails;
import com.edug.devfinder.messages.parts.UnsupportedHttpMethodDetails;
import com.edug.devfinder.messages.parts.ValidationExceptionDetails;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Details {

    @JsonProperty("ExternalResponse")
    private ExternalResponseDetails externalResponseDetails;

    @JsonProperty("ValidationDetails")
    private ValidationExceptionDetails validationExceptionDetails;

    @JsonProperty("UnsupportedHttpMethodDetails")
    private UnsupportedHttpMethodDetails unsupportedHttpMethodDetails;

    @JsonProperty("TypeMismatchDetails")
    private TypeMismatchDetails typeMismatchDetails;

    public Details(ValidationExceptionDetails validationExceptionDetails) {
        this.validationExceptionDetails = validationExceptionDetails;
    }

    public Details(ExternalResponseDetails externalResponseDetails) {
        this.externalResponseDetails = externalResponseDetails;
    }

    public Details(UnsupportedHttpMethodDetails unsupportedHttpMethodDetails) {
        this.unsupportedHttpMethodDetails = unsupportedHttpMethodDetails;
    }

    public Details(TypeMismatchDetails typeMismatchDetails) {
        this.typeMismatchDetails = typeMismatchDetails;
    }
}
