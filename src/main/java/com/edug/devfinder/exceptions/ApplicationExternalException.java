package com.edug.devfinder.exceptions;

import com.edug.devfinder.messages.MessagesEnum;
import com.edug.devfinder.messages.parts.ExternalResponseDetails;
import lombok.Getter;

import java.io.Serial;

@Getter
public class ApplicationExternalException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 1L;

	private final ExternalResponseDetails externalResponseDetails;
	private final String reason;
	private final MessagesEnum messageEnum;

	public ApplicationExternalException(MessagesEnum messageEnum, ExternalResponseDetails externalResponseDetails) {
		super(messageEnum.getMessage());
		this.messageEnum = messageEnum;
		this.externalResponseDetails = externalResponseDetails;
		this.reason = externalResponseDetails.getBody();
	}

	public ApplicationExternalException(MessagesEnum messageEnum, Throwable cause) {
		super(messageEnum.getMessage(), cause);
		this.messageEnum = messageEnum;
		this.externalResponseDetails = null;
		this.reason = cause.getMessage();
	}

}
