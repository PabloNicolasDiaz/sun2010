package org.nicolas.sun2010.web.mapper.formats;

import java.text.ParseException;

public interface Formatter<T, U> {

	U parse(T t) throws ParseException;

}
