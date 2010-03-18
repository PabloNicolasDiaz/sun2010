package org.nicolas.sun2010.web.mapper;

import java.util.List;

public abstract class TableExtractor<T, U, V, W> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.nicolas.sun2010.web.mapper.TableExtractor#process(org.jdom.Document)
	 */
	public void process(U document) throws BadDocumentException {
		V table = matchTable(document);
		List<W> tableBody = matchTableBody(table);
		for (W element : tableBody) {
			T object;
			object = makeRow(element);
			handle(object);
		}
	}

	protected abstract List<W> matchTableBody(V table)
			throws BadDocumentException;

	protected abstract V matchTable(U doc) throws BadDocumentException;

	/** por defecto no hago actividad alguna. */

	protected void handle(T value) {

	};

	/** no tengo idea de como hacer la fila desde el Element */
	protected abstract T makeRow(W element) throws BadRowException;

}
