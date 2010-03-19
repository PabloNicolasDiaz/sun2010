package org.nicolas.sun2010.web.mapper.formats.wrappers.jdom;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.nicolas.sun2010.web.mapper.formats.BeanPathFormatter;

public class JDOMMultipleFormatter extends
		BeanPathFormatter<Element, List<String>> {

	public JDOMMultipleFormatter(String s) {
		super(s);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> parse(Element t) throws ParseException {
		try {
			List<String> tmp = new LinkedList<String>();
			List<Element> l = constructPath(getPath(), t).selectNodes(t);
			for (Element object : l) {
				tmp.add(object.getValue());
			}
			return tmp;
		} catch (JDOMException e) {
			throw new ParseException(e.getMessage(), 0);
		}

	}

}
