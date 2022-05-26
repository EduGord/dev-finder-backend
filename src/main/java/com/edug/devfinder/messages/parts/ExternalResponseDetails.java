package com.edug.devfinder.messages.parts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Getter
public class ExternalResponseDetails {
    @JsonProperty("Status")
    private final int status;

    @JsonProperty("Body")
    private final String body;

    @JsonProperty("Headers")
    private final HttpHeaders headers;

    public ExternalResponseDetails(int status, HttpHeaders headers, String body) {
        this.status = status;
        this.headers = headers;
        this.body = body;
    }

    public static ExternalResponseDetails parse(ClientHttpResponse clientHttpResponse) throws IOException {
        var status = clientHttpResponse.getRawStatusCode();
        var headers = clientHttpResponse.getHeaders();
        var body = new String(clientHttpResponse.getBody().readAllBytes(), StandardCharsets.ISO_8859_1);
        return new ExternalResponseDetails(status, headers, body);
    }
}
