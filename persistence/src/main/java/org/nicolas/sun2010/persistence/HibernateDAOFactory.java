package org.nicolas.sun2010.persistence;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * It implies a transaction.
 * 
 * */

public class HibernateDAOFactory implements DAOFactory {

	private final Transaction tr;
	private final Session sess;

	private static final Log log = LogFactory.getLog(HibernateDAOFactory.class);

	private static final SessionFactory sessionFactory = getSessionFactory();

	private static SessionFactory getSessionFactory() {
		try {
			return new AnnotationConfiguration().configure()
					.buildSessionFactory();
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}

	}

	private static SessionFactory getSessionfactory() {
		return sessionFactory;
	}

	private static void close() {
		sessionFactory.close();
	}

	public HibernateDAOFactory() {
		sess = getSessionfactory().getCurrentSession();
		tr = sess.beginTransaction();
	}

	@Override
	public <T, U extends Serializable> GenericDAO<T, U> createDAO(Class<T> clasz) {
		return new HibernateGenericDAO<T, U>(clasz, sess);
	}

	@Override
	public void terminate() {
		tr.commit();
		close();
	}

	@Override
	public <T, U extends Serializable> ReadOnlyGenericDAO<T, U> createROnlyDAO(
			Class<T> clasz) {
		return new HibernateReadOnlyGenericDAO<T, U>(clasz, sess);
	}
}
