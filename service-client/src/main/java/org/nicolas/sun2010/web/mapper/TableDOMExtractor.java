package org.nicolas.sun2010.web.mapper;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
			Map<String, Formatter<Element, ?>> map, Method sfMethod,
			Integer rowl) {
		this.namespace = Namespace;
		this.bodyPath = bp;
		this.insertMethodCallMapping = map;
		this.tablePrefix = tablePath;
		this.staticFactoryMethod = sfMethod;
		this.rowLenght = rowl;
	}

	/**
	 * Clase de la cual voy a usar una instancia sacada de una instancia de
	 * daoFactoryClass para luego llamar a un método que me haga el insert en la
	 * base de datos.
	 */

	/* GenericDAO<T, ID> dao; */

	String tablePrefix;

	String bodyPath;

	String namespace;

	Method staticFactoryMethod;

	Integer rowLenght;

	Map<String, Formatter<Element, ?>> insertMethodCallMapping;

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
			throw new BadTableException();
		}
	};

	@SuppressWarnings("unchecked")
	@Override
	protected T makeRow(Element element) throws BadRowException {
		T newEntry = null;
		if (element.getChildren().size() != rowLenght)
			throw new BadRowException();
		try {
			List<Object> values = new LinkedList();

			for (Map.Entry<String, Formatter<Element, ?>> thing : insertMethodCallMapping
					.entrySet()) {
				XPath path = constructPath(thing.getKey(), element);
				Formatter<Element, ?> rec = thing.getValue();

				List<Element> t = path.selectNodes(element);
				values.add(rec.parse(t));
			}
			newEntry = (T) staticFactoryMethod.invoke(null, values.toArray());

		} catch (Exception e) {
			throw new BadRowException();
		}

		return newEntry;
	}

	@Override
	protected void handle(T value) {
		System.out.println(value);
	};

}
