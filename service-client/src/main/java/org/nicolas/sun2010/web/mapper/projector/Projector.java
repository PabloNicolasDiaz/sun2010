package org.nicolas.sun2010.web.mapper.projector;

import java.util.List;
import java.util.Map;
import java.util.Observer;

public abstract class Projector<T, PK> implements Observer {

	/**
	 * Aca se llama a la factory que corresponda.
	 * 
	 * */
	public abstract T factoryCallHook(PK pk, Object... values);

	public abstract T convertColumn(List<?>... values);

	Map<Integer, Object> mapaConversion;

}
