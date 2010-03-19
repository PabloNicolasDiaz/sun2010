package org.nicolas.sun2010.web.mapper.formats;

import java.text.ParseException;

import org.jdom.Element;
import org.jdom.JDOMException;

public class JDOMSingleFormatter extends BeanPathFormatter<Element, String> {

	public JDOMSingleFormatter(String s) {
		super(s);
	}

	@Override
	public String parse(Element t) throws ParseException {
		try {
			return ((Element) constructPath(this.getPath(), t)
					.selectSingleNode(t)).getValue();
		} catch (JDOMException e) {
			throw new ParseException(e.getMessage(), 0);
		}
	}
}
