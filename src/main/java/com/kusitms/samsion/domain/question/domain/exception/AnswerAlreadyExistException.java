package com.kusitms.samsion.domain.question.domain.exception;

import com.kusitms.samsion.common.exception.Error;

public class AnswerAlreadyExistException extends QuestionException{
	public AnswerAlreadyExistException(Error error) {
		super(error);
	}
}
