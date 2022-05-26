package com.edug.devfinder.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessagesEnum {

	//validation errors
	REQUEST_METHOD_NOT_SUPPORTED(400, "Request method not supported or path parameter not found."),
	CONSUMER_AUTHORIZATION_UNAUTHORIZED(402, "Unauthorized."),
	MALFORMED_JSON_REQUEST(403, "Malformed JSON request."),
	VALIDATION_ERROR(406, "Validation Error"),
	TYPE_MISMATCH_REQUEST(407, "Invalid %s: Type mismatch (%s)."),

	DEFAULT_ERROR(500, "Internal error. If problem persists, contact support."),
	RESOURCE_NOT_FOUND(501, "Resource not found."),
	EXTERNAL_RESOURCE_ACCESS_FAILED(502, "Failed to access external resource.");

	private final int code;
	private final String message;
}
