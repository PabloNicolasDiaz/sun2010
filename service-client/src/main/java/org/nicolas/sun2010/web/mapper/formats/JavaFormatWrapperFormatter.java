package org.nicolas.sun2010.web.mapper.formats;

import java.text.Format;
import java.text.ParseException;

public class JavaFormatWrapperFormatter<T, U> implements Formatter<T, U> {

	Class<? extends Format> fmt;
	transient Format fmrtr;

	public JavaFormatWrapperFormatter(Class<? extends Format> fmt)
			throws InstantiationException, IllegalAccessException {
		this.fmt = fmt;
		this.fmrtr = fmt.newInstance();
	}

	@SuppressWarnings("unchecked")
	@Override
	public U parse(T t) throws ParseException {
		if (fmrtr == null)
			try {
				fmrtr = fmt.newInstance();
			} catch (InstantiationException e) {
				throw new ParseException("error with Formatter", 0);
			} catch (IllegalAccessException e) {
				throw new ParseException("error with Formatter", 0);
			}
		return (U) (fmrtr.parseObject(t.toString()));
	}
}
