package org.nicolas.sun2010.web.mapper;

import java.util.List;
import java.util.Observable;

public abstract class TableExtractor<T, U, V, W> extends Observable {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.nicolas.sun2010.web.mapper.TableExtractor#process(org.jdom.Document)
	 */
	public void process(U document) {

		V table = matchTable(document);

		List<W> tableBody = matchTableBody(table);

		for (W element : tableBody) {
			T object = makeRow(element);
			this.notifyObservers(object);
		}

	}

	protected abstract List<W> matchTableBody(V table);

	protected abstract V matchTable(U doc);

	/** por defecto no hago actividad alguna. */

	protected void handle(T value) {

	};

	/** no tengo idea de como hacer la fila desde el Element */
	protected abstract T makeRow(W element);

}
