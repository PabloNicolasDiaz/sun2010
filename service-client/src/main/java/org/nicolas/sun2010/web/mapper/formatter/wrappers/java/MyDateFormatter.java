package org.nicolas.sun2010.web.mapper.formatter.wrappers.java;

import java.text.SimpleDateFormat;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(value = "MyDateFormatter")
public class MyDateFormatter extends SimpleDateFormat {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1646428587043149289L;

	public MyDateFormatter() {
		super("dd/MM/yyyy");
	}

}
