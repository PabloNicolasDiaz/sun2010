package org.nicolas.sun2010.web.mapper.formats.wrappers.dao;

import java.io.Serializable;
import java.text.ParseException;

import org.nicolas.sun2010.persistence.HibernateDAOFactory;
import org.nicolas.sun2010.persistence.ReadOnlyGenericDAO;
import org.nicolas.sun2010.web.mapper.formats.Formatter;

public class DAOAdapterFormatter<T, ID extends Serializable> implements
		Formatter<ID, T> {

	Class<T> classdao;

	transient ReadOnlyGenericDAO<T, ID> dao;

	public DAOAdapterFormatter(Class<T> cls) {
		this.classdao = cls;
	}

	@Override
	public T parse(ID t) throws ParseException {
		if (dao == null)
			dao = getDAO();
		return dao.findById(t);
	}

	private ReadOnlyGenericDAO<T, ID> getDAO() {
		return HibernateDAOFactory.getDaoFactory().createROnlyDAO(classdao);
	}

}
