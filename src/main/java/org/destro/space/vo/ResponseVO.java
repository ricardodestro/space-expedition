package org.destro.space.vo;

/**
 * VO de reposta
 * 
 * @author destro
 */
public class ResponseVO {

	private int code;

	private String message;

	private Object entity;

	public ResponseVO(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public ResponseVO(int code, String message, Object entity) {
		this(code, message);
		this.entity = entity;
	}

	public String getMessage() {
		return message;
	}

	public Object getEntity() {
		return entity;
	}

	public int getCode() {
		return code;
	}
}
