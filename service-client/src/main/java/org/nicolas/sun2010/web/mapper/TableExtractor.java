package org.nicolas.sun2010.web.mapper;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

public abstract class TableExtractor<T, U, V, W> implements Extractor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.nicolas.sun2010.web.mapper.TableExtractor#process(org.jdom.Document)
	 */
	public void process(U document) throws BadDocumentException {
		V table = matchTable(document);
		List<Object> tableConstants = new LinkedList<Object>();
		try {
			tableConstants = matchTableConstants(document);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		List<W> tableBody = matchTableBody(table);
		for (W element : tableBody) {
			try {
				T object;
				object = makeRow(tableConstants, element);
				handle(object);
			} catch (BadRowException e) {
				// e.printStackTrace();
			}
		}
	}

	protected abstract List<Object> matchTableConstants(U table)
			throws ParseException;

	protected abstract List<W> matchTableBody(V table)
			throws BadDocumentException;

	protected abstract V matchTable(U doc) throws BadDocumentException;

	/** por defecto no hago actividad alguna. */

	protected void handle(T value) {

	};

	/** no tengo idea de como hacer la fila desde el Element */
	protected abstract T makeRow(List<Object> constants, W element)
			throws BadRowException;

}
