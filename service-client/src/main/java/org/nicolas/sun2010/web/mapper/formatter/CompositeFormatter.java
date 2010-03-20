package org.nicolas.sun2010.web.mapper.formatter;

import java.text.ParseException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(value = "CompositeFormatter")
public class CompositeFormatter<T, U> extends LinkedList<Formatter<T, U>>
		implements Formatter<List<T>, List<U>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4995714445559828445L;

	@Override
	public List<U> parse(List<T> element) throws ParseException {
		List<U> tmp = new LinkedList<U>();
		int i = 0;
		for (Formatter<T, U> t : this) {
			tmp.add(t.parse(element.get(i)));
			i++;
		}
		return tmp;
	}

	public CompositeFormatter(Collection<? extends Formatter<T, U>> d) {
		super(d);
	}
}
