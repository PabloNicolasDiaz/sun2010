package org.nicolas.sun2010.web.mapper.formats;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

public abstract class BeanPathFormatter<T, U> implements Formatter<T, U> {

	private String path;

	public BeanPathFormatter() {

	}

	public BeanPathFormatter(String s) {
		this.path = s;
	}

	protected static XPath constructPath(String s, Element element)
			throws JDOMException {
		XPath path = XPath.newInstance(s);
		path.addNamespace("x", element.getNamespaceURI());
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

}