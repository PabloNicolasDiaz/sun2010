/**
 * 
 */
package org.nicolas.sun2010.web.mapper.formats.wrappers.jdom;

import java.text.ParseException;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

/**
 * @author nicolas
 * 
 */
public class JDOMAttributeFormatter extends JDOMSingleFormatter {

	String attribute;

	public JDOMAttributeFormatter(String s, String attString) {
		super(s);
		this.attribute = attString;
	}

	@Override
	public String parse(Element t) throws ParseException {
		try {

			XPath p = constructPath(this.getPath(), t);
			Element elem = (Element) p.selectSingleNode(t);
			return elem.getAttribute(attribute).getValue();
		} catch (JDOMException e) {
			throw new ParseException(e.getMessage(), 0);
		}
	}

}
