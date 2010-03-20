package org.nicolas.sun2010.web.mapper.formatter.wrappers.jdom;

import java.text.ParseException;

import org.jdom.Content;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;
import org.nicolas.sun2010.web.mapper.formatter.BeanPathFormatter;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(value = "JDOMSingleFormatter")
public class JDOMSingleFormatter extends BeanPathFormatter<Element, String> {

	public JDOMSingleFormatter(String s) {
		super(s);
	}

	@Override
	public String parse(Element t) throws ParseException {
		try {
			XPath p = constructPath(this.getPath(), t);
			Content elem = (Content) p.selectSingleNode(t);
			return elem.getValue();
		} catch (JDOMException e) {
			throw new ParseException(e.getMessage(), 0);
		}
	}
}
