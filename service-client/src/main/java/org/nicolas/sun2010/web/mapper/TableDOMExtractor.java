package org.nicolas.sun2010.web.mapper;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.text.Format;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

public class TableDOMExtractor<T, ID extends Serializable> extends
		TableExtractor<T, Document, Element, Element> {

	public TableDOMExtractor(String tablePath, String Namespace,
			Map<String, Class<? extends Format>> map, Method sfMethod) {
		this.namespace = Namespace;
		// this.insertMethodCallMapping = map;
		this.tablePrefix = tablePath;
		// this.staticFactoryMethod = sfMethod;
	}

	/**
	 * Clase de la cual voy a usar una instancia sacada de una instancia de
	 * daoFactoryClass para luego llamar a un método que me haga el insert en la
	 * base de datos.
	 */

	// GenericDAO<T, U> dao;

	String tablePrefix;

	String bodyPath;

	String namespace;

	Method staticFactoryMethod;

	Map<String, Class<? extends Format>> insertMethodCallMapping;

	@Override
	@SuppressWarnings("unchecked")
	protected List<Element> matchTableBody(Element element) {
		XPath path;
		try {
			path = constructPath(bodyPath, element);
			return path.selectNodes(element);
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		return new LinkedList<Element>();
	}

	private XPath constructPath(String s, Element element) throws JDOMException {
		XPath path = XPath.newInstance(s);
		path.addNamespace(namespace, element.getNamespaceURI());
		return path;
	};

	@Override
	protected Element matchTable(Document doc) {
		XPath path;
		try {
			path = constructPath(tablePrefix, doc.getRootElement());
			return (Element) path.selectSingleNode(doc);
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		return new Element("");
	};

	@SuppressWarnings("unchecked")
	@Override
	protected T makeRow(Element element) {
		try {
			List<Object> values = new LinkedList();

			for (Map.Entry<String, Class<? extends Format>> thing : insertMethodCallMapping
					.entrySet()) {
				XPath path;

				path = XPath.newInstance(thing.getKey());

				path.addNamespace(namespace, element.getNamespaceURI());
				Format rec = thing.getValue().newInstance();
				Element t = (Element) path.selectSingleNode(element);
				System.out.println(t);
				String valor = t.getValue();
				values.add(rec.parseObject(valor));
			}
			System.out.println(values.toString());
			T newEntry = (T) staticFactoryMethod.invoke(null, values.toArray());
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void handle(T value) {
		dao.create(newEntry);
	};

}
