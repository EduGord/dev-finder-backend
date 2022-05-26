package com.edug.devfinder.messages.parts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TypeMismatchDetails {

    @JsonProperty("Name")
    String name;

    @JsonProperty("Value")
    String value;

    @JsonProperty("PropertyName")
    String propertyName;

    @JsonProperty("RequiredType")
    String requiredType;

    @JsonProperty("Parameter")
    String parameter;

    public static TypeMismatchDetails parse(MethodArgumentTypeMismatchException e) {
        var requiredType = e.getRequiredType() != null ? e.getRequiredType().getSimpleName() : null;
        var value = e.getValue() != null ? e.getValue().toString() : null;
        return new TypeMismatchDetails(e.getName(), value, e.getPropertyName(), requiredType, e.getParameter().getParameterName());
    }
}
