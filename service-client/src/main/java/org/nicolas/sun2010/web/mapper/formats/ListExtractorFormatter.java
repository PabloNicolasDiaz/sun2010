package org.nicolas.sun2010.web.mapper.formats;

import java.text.ParseException;
import java.util.List;

public class ListExtractorFormatter<T> implements Formatter<List<T>, T> {

	Integer value = 0;

	public ListExtractorFormatter(Integer v) {
		this.value = v;
	}

	@Override
	public T parse(List<T> t) throws ParseException {
		return t.get(value);
	}

}
