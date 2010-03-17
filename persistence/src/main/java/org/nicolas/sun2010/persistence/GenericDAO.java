package org.nicolas.sun2010.persistence;

import java.io.Serializable;

/**
 * a fairly basic and simple DAO generic class, Read & Write.
 * 
 * */
public interface GenericDAO<T, ID extends Serializable> extends
		ReadOnlyGenericDAO<T, ID> {

	/**
	 * Create Operation.
	 **/
	void create(T values);

	/**
	 * Update Operation.
	 **/
	void update(T value);

	/**
	 * Delete Operation.
	 **/
	void delete(T object);

}
