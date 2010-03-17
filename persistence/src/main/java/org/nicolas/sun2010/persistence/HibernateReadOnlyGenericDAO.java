package org.nicolas.sun2010.persistence;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

public class HibernateReadOnlyGenericDAO<T, ID extends Serializable> implements
		ReadOnlyGenericDAO<T, ID> {

	static final Log log = LogFactory.getLog(HibernateReadOnlyGenericDAO.class);

	final Class<T> AssociatedClass;
	private Session session;

	public HibernateReadOnlyGenericDAO(Class<T> claz, Session sess) {
		this.AssociatedClass = claz;
		this.setSession(sess);
	}

	private void setSession(Session session) {
		this.session = session;
	}

	Session getSession() {
		return session;
	}

	@SuppressWarnings("unchecked")
	public List<T> findByExample(T instance) {
		log.debug("finding" + this.AssociatedClass.toString()
				+ " instance by example");
		try {
			List<T> results = getSession().createCriteria(this.AssociatedClass)
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public T findById(ID id) {
		log.debug("getting " + AssociatedClass.toString()
				+ " instance with id: " + id);
		try {
			T instance = (T) getSession().get(AssociatedClass, id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

}
