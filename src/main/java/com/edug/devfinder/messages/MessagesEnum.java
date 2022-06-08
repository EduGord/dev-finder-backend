package com.edug.devfinder.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;

@Getter
@AllArgsConstructor
public enum MessagesEnum {
	// VALIDATION
	INVALID_JWT_TOKEN(501, "Authorization token is invalid."),
	INVALID_CREDENTIALS(502, "Invalid credentials."),

	// ENTITY NOT FOUND
	ENTITY_NOT_FOUND(600, "Entity not found."),
	USER_NOT_FOUND(601, "User not found."),
	TECHNOLOGY_NOT_FOUND(602, "Technology not found."),
	ROLE_NOT_FOUND(603, "Role not found."),
	JOB_EXPERIENCE_NOT_FOUND(604, "Job experience not found."),
	USER_TECHNOLOGY_NOT_FOUND(605, "User technology not found."),


	// ALREADY EXISTS
	USER_ALREADY_EXISTS(701, "User already exists."),
	USER_TECHNOLOGY_ALREADY_EXISTS(702, "User technology already exists."),
	JOB_EXPERIENCE_ALREADY_EXISTS(702, "Job experience already exists."),

	// REQUIRED
	AUTH_TOKEN_REQUIRED(801, "Consumer Auth Token Required."),
	AUTHORIZATION_HEADERS_REQUIRED(802, "Authorization headers is required."),

	// MISCELLANEOUS
	DEFAULT_ERROR(1000, "Internal error. If problem persists, contact support."),
	RESOURCE_NOT_FOUND(1001, "Resource not found."),
	EXTERNAL_RESOURCE_ACCESS_FAILED(1002, "Failed to access external resource."),
	LOGIN_ATTEMPTS_EXCEEDED_LIMIT(1003, "Login attempts exceeded limits."),
	REQUEST_METHOD_NOT_SUPPORTED(1004, "Request method not supported or path parameter not found."),
	MALFORMED_JSON_REQUEST(1005, "Malformed JSON request."),
	VALIDATION_ERROR(1006, "Validation Error"),
	// TODO
	TYPE_MISMATCH_REQUEST(1007, "Invalid %s: Type mismatch (%s).");

	private final int code;
	private final String message;

	public HashMap<String, Object> toMap() {
		var error = new HashMap<String, Object>();
		error.put("Code", this.getCode());
		error.put("Message", this.getMessage());
		return error;
	}

	public HashMap<String, Object> toMap(Exception e) {
		var error = this.toMap();
		error.put("Exception", e.getMessage());
		return error;
	}

}
