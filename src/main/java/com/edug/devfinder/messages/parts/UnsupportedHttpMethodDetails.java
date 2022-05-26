package com.edug.devfinder.messages.parts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.HttpRequestMethodNotSupportedException;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UnsupportedHttpMethodDetails {

    @JsonProperty("RequestedMethod")
    private String method;

    @JsonProperty("SupportedMethods")
    private String[] supportedMethods;

    public static UnsupportedHttpMethodDetails parse(HttpRequestMethodNotSupportedException e) {
        return new UnsupportedHttpMethodDetails(e.getMethod(), e.getSupportedMethods());
    }
}
