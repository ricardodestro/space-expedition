package org.destro.space;

import org.apache.log4j.Logger;
import org.destro.space.vo.ResponseVO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.ServletRequestBindingException;
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

	private static Logger LOG = Logger.getLogger(GlobalExceptionHandler.class);

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = { ServletRequestBindingException.class, ValidationException.class })
	public ResponseVO badRequest(Exception e) {
		return new ResponseVO(HttpStatus.BAD_REQUEST.value(), e.getMessage());
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(value = NoHandlerFoundException.class)
	public ResponseVO notFound(Exception e) {
		return new ResponseVO(HttpStatus.NOT_FOUND.value(), e.getMessage());
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = Exception.class)
	public ResponseVO error(Exception e) {
		LOG.error(e.getMessage(), e);

		return new ResponseVO(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
	}
}