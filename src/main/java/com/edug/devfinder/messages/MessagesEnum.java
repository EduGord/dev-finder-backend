package com.edug.devfinder.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum MessagesEnum {
	REQUEST_METHOD_NOT_SUPPORTED(400, "Request method not supported or path parameter not found."),

	AUTH_TOKEN_REQUIRED(401, "Consumer Auth Token Required."),

	AUTHORIZATION_HEADERS_NOT_PRESENT(402, "Authorization headers is required."),
	MALFORMED_JSON_REQUEST(403, "Malformed JSON request."),
	VALIDATION_ERROR(404, "Validation Error"),
	TYPE_MISMATCH_REQUEST(405, "Invalid %s: Type mismatch (%s)."),

	INVALID_JWT_TOKEN(406, "Authorization token is invalid."),
	INVALID_CREDENTIALS(407, "Invalid credentials."),
	USER_NOT_FOUND(408, "User not found."),

	DEFAULT_ERROR(500, "Internal error. If problem persists, contact support."),
	RESOURCE_NOT_FOUND(501, "Resource not found."),
	EXTERNAL_RESOURCE_ACCESS_FAILED(502, "Failed to access external resource.");

	private final int code;
	private final String message;

	public Map<String, Object> toMap() {
		var error = new HashMap<String, Object>();
		error.put("Code", this.getCode());
		error.put("Message", this.getMessage());
		return error;
	}

	public Map<String, Object> toMap(Exception e) {
		var error = this.toMap();
		error.put("Exception", e.getMessage());
		return error;
	}

}
