package org.nicolas.sun2010.web.mapper;

public class BadRowException extends BadDocumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2530788773947944183L;

	public BadRowException() {
		super();
	}

	public BadRowException(String reason) {
		super(reason);
	}
}
