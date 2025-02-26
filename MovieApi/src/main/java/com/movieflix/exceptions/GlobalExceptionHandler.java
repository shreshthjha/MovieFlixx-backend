package com.movieflix.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MovieNotFoundException.class)
	public ProblemDetail handleMovieNotFoundException(MovieNotFoundException rf) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,rf.getMessage());	
	}
	
	@ExceptionHandler(FileExistsException.class)
	public ProblemDetail handleFileExistsException(FileExistsException rf) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, rf.getMessage());
	}
	
	@ExceptionHandler(EmptyFileException.class)
	public ProblemDetail handleEmptyFileException(EmptyFileException rf) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, rf.getMessage());
	}
}
