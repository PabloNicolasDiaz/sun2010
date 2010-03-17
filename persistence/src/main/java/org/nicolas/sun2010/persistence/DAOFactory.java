package org.nicolas.sun2010.persistence;

import java.io.Serializable;

/**
 * Abstract Factory to create dao's Objects ...
 * 
 * */

public interface DAOFactory {

	public <T, U extends Serializable> GenericDAO<T, U> createDAO(Class<T> clasz);

	public <T, U extends Serializable> ReadOnlyGenericDAO<T, U> createROnlyDAO(
			Class<T> clasz);

	public void terminate();

}
