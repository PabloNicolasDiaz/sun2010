/**
 * One new DAO object implies one hibernate session ... 
 */
package org.nicolas.sun2010.persistence;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

/**
 * @author nicolas
 * 
 */
public class HibernateGenericDAO<T, ID extends Serializable> extends
		HibernateReadOnlyGenericDAO<T, ID> implements GenericDAO<T, ID> {

	static final Log log = LogFactory.getLog(HibernateGenericDAO.class);

	public HibernateGenericDAO(Class<T> claz, Session sess/* , Transaction tr */) {
		super(claz, sess);
	}

	public void create(T elem) {
		getSession().persist(elem);
	}

	public void delete(T object) {
		getSession().delete(object);
	}

	public void update(T value) {
		getSession().update(value);
	}

}
