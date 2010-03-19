package org.nicolas.sun2010.web.mapper;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;
import org.nicolas.sun2010.web.mapper.formats.Formatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableDOMExtractor<T, ID extends Serializable> extends
		TableExtractor<T, Document, Element, Element> {

	private static Logger log = LoggerFactory
			.getLogger(TableDOMExtractor.class);

	public TableDOMExtractor(String tablePath, String bp, String Namespace,
			Collection<Formatter<Element, ?>> map, Method sfMethod, Integer rowl) {
		this.namespace = Namespace;
		this.bodyPath = bp;
		this.insertMethodCallMapping = new LinkedList<Formatter<Element, ?>>(
				map);
		this.tablePrefix = tablePath;
		this.staticFactoryMethod = sfMethod;
		this.rowLenght = rowl;
	}

	/**
	 * Clase de la cual voy a usar una instancia sacada de una instancia de
	 * daoFactoryClass para luego llamar a un método que me haga el insert en la
	 * base de datos.
	 */

	String tablePrefix;

	String bodyPath;

	String namespace;

	Method staticFactoryMethod;

	Integer rowLenght;

	List<Formatter<Element, ?>> insertMethodCallMapping;

	@Override
	@SuppressWarnings("unchecked")
	protected List<Element> matchTableBody(Element element)
			throws BadTableException {
		XPath path;
		try {
			path = constructPath(bodyPath, element);
			log.debug("Xpath : " + path.toString());
			return path.selectNodes(element);
		} catch (Exception e) {
			throw new BadTableException();
		}
	}

	private XPath constructPath(String s, Element element) throws JDOMException {
		XPath path = XPath.newInstance(s);
		path.addNamespace(namespace, element.getNamespaceURI());
		return path;
	};

	@Override
	protected Element matchTable(Document doc) throws BadDocumentException {
		XPath path;
		try {
			log.info("tblPrefix : " + tablePrefix);
			path = constructPath(tablePrefix, doc.getRootElement());
			log.info("Xpath : " + path.toString());
			return (Element) path.selectSingleNode(doc);
		} catch (JDOMException e) {
			throw new BadTableException(e.getMessage());
		}
	};

	@SuppressWarnings("unchecked")
	@Override
	protected T makeRow(Element element) throws BadRowException {
		T newEntry = null;
		if (element.getChildren().size() != rowLenght)
			throw new BadRowException("Row size doesn't match");
		try {
			List<Object> values = new LinkedList();

			for (Formatter<Element, ?> thing : insertMethodCallMapping) {
				values.add(thing.parse(element));
			}
			newEntry = (T) staticFactoryMethod.invoke(null, values.toArray());

		} catch (Exception e) {
			throw new BadRowException(e.getMessage());
		}

		return newEntry;
	}

	@Override
	protected void handle(T value) {
		System.out.println(value);
	};

}
