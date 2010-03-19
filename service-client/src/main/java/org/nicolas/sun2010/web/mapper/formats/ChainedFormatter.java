package org.nicolas.sun2010.web.mapper.formats;

import java.text.ParseException;

public class ChainedFormatter<T, U, V> implements Formatter<T, V> {

	Formatter<T, U> fmt1;
	Formatter<U, V> fmt2;

	public ChainedFormatter(Formatter<T, U> f1, Formatter<U, V> f2) {
		this.fmt1 = f1;
		this.fmt2 = f2;
	}

	@Override
	public V parse(T t) throws ParseException {
		return fmt2.parse(fmt1.parse(t));
	}

}
