package org.destro.space;

import org.destro.space.vo.ResponseVO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Hanlder responsável no tratamento de erros globais
 * 
 * @author destro
 */
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = Exception.class)
	public ResponseVO handleException(Exception e) {
		return new ResponseVO(HttpStatus.BAD_REQUEST.value(), e.getMessage());
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(value = NoHandlerFoundException.class)
	public ResponseVO notFound(Exception e) {
		return new ResponseVO(HttpStatus.NOT_FOUND.value(), e.getMessage());
	}

}