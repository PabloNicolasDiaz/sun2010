package org.nicolas.sun2010.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DailyState {

	@Id
	private DailyStatePK id;

	private Double bet;
	private Double yield;

	public DailyState() {

	}

	public DailyState(Long g, Date d, Double bet, Double yield) {
		id = ModelFactory.createDailyStatePK(g, d);
		this.bet = bet;
		this.yield = yield;
	}

	public void setBet(Double bet) {
		this.bet = bet;
	}

	public Double getBet() {
		return bet;
	}

	public void setYield(Double yield) {
		this.yield = yield;
	}

	public Double getYield() {
		return yield;
	}

	public void setId(DailyStatePK id) {
		this.id = id;
	}

	public DailyStatePK getId() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof DailyState))
			return false;

		if (getClass() != obj.getClass())
			return false;

		DailyState ds = (DailyState) obj;

		return (ds.bet == bet || (bet == null && bet.equals(ds.bet)))
				&& (yield == ds.yield || (yield != null && yield
						.equals(ds.yield)))
				&& (ds.id == id || (id != null && id.equals(ds.id)));
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + (bet == null ? 0 : bet.hashCode());
		result = 37 * result + (yield == null ? 0 : yield.hashCode());
		result = 37 * result + (id == null ? 0 : id.hashCode());
		return result;
	}
}
