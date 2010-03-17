package org.nicolas.sun2010.persistence;

import java.io.Serializable;
import java.util.List;

public interface ReadOnlyGenericDAO<T, ID extends Serializable> {

	public abstract List<T> findByExample(T instance);

	/**
	 * Read Operation.
	 **/
	public abstract T findById(ID id);

}