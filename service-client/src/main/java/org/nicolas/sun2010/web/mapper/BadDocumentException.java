package org.nicolas.sun2010.web.mapper;

public class BadDocumentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6901238704612785864L;

	public BadDocumentException() {
		this("no reason");
	}

	public BadDocumentException(String s) {
		super(s);
	}
}
