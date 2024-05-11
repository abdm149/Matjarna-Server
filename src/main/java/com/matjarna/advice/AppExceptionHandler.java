package com.matjarna.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.matjarna.exception.AuthenticationFailedException;
import com.matjarna.exception.DuplicateUserException;
import com.matjarna.exception.ValidationException;

@RestControllerAdvice
public class AppExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {

		Map<String, String> errorMap = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			errorMap.put(error.getField(), error.getDefaultMessage());
		});
		return errorMap;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ValidationException.class)
	public Map<String, String> ValidationException(ValidationException ex) {
		Map<String, String> errorMap = new HashMap<>();
		errorMap.put("Error", ex.getMessage());
		return errorMap;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public Map<String, String> handleException(MaxUploadSizeExceededException ex) {
		Map<String, String> errorMap = new HashMap<>();
		errorMap.put("Error", ex.getMessage());
		return errorMap;
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(AuthenticationFailedException.class)
	public Map<String, String> handleException(AuthenticationFailedException ex) {
		Map<String, String> errorMap = new HashMap<>();
		errorMap.put("Error", ex.getMessage());
		return errorMap;
	}

	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(DuplicateUserException.class)
	public Map<String, String> handleException(DuplicateUserException ex) {
		Map<String, String> errorMap = new HashMap<>();
		errorMap.put("Error", ex.getMessage());
		return errorMap;
	}
}
