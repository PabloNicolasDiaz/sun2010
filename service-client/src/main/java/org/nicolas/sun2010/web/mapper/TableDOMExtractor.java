package org.nicolas.sun2010.web.mapper;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;
import org.nicolas.sun2010.web.mapper.formatter.Formatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(value = "TableDOMExtractor")
public class TableDOMExtractor<T, ID extends Serializable> extends
		TableExtractor<T, Document, Element, Element> {

	private static Logger log = LoggerFactory
			.getLogger(TableDOMExtractor.class);

	public TableDOMExtractor(String tablePath, String bp, String Namespace,
			Collection<Formatter<Element, ?>> rowMapping,
			Collection<Formatter<Element, ?>> constantMapping, Method sfMethod,
			Integer rowl) {
		this.namespace = Namespace;
		this.bodyPath = bp;
		this.insertMethodCallMapping = new LinkedList<Formatter<Element, ?>>(
				rowMapping);
		this.constantsMapping = new LinkedList<Formatter<Element, ?>>(
				constantMapping);
		this.tablePrefix = tablePath;
		this.factoryMethod = sfMethod;
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

	Method factoryMethod;

	Integer rowLenght;

	/** por convencion se ponen primero las constantes */

	List<Formatter<Element, ?>> constantsMapping;

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

	@Override
	@SuppressWarnings("unchecked")
	protected T makeRow(List<Object> constants, Element element)
			throws BadRowException {
		T newEntry = null;
		if (element.getChildren().size() != rowLenght)
			throw new BadRowException("Row size doesn't match");
		try {
			List<Object> values = extractElements(this.insertMethodCallMapping,
					constants, element);
			newEntry = (T) factoryMethod.invoke(null, values.toArray());

		} catch (Exception e) {
			// e.printStackTrace();
			throw new BadRowException(e.toString());
		}
		return newEntry;
	}

	private static List<Object> extractElements(
			Collection<Formatter<Element, ?>> mapping, List<Object> constants,
			Element element) throws ParseException {
		List<Object> values = new LinkedList<Object>(constants);
		for (Formatter<Element, ?> thing : mapping) {
			values.add(thing.parse(element));
		}
		return values;
	}

	@Override
	protected void handle(T value) {
		System.out.println(value);
	}

	@Override
	protected List<Object> matchTableConstants(Document table)
			throws ParseException {
		if (this.constantsMapping == null)
			return new LinkedList<Object>();
		return extractElements(this.constantsMapping, new LinkedList<Object>(),
				table.getRootElement());
	}

}
