package com.edug.devfinder.exceptions;

import com.edug.devfinder.messages.MessagesEnum;
import lombok.Getter;

import java.io.Serial;

@Getter
public class ApplicationException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 1L;

	private final MessagesEnum messageEnum;

	public ApplicationException(MessagesEnum messageEnum, Throwable cause) {
		super(messageEnum.getMessage(), cause);
		this.messageEnum = messageEnum;
	}

	public ApplicationException(MessagesEnum messageEnum) {
		super(messageEnum.getMessage());
		this.messageEnum = messageEnum;
	}

}

