package org.nicolas.sun2010.web.mapper.projector;

public class BasicProjector<T, PK> extends Projector<T, PK> {

	public abstract T buildRow(PK pk, AT value);

	public T getReference();
}
