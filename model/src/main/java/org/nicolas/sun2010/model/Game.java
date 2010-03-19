package org.nicolas.sun2010.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

@Entity
public class Game {
	@Id
	private Long Uid;

	@ManyToOne
	private Machine machine = null;

	@OneToMany
	@MapKey(name = "id.dateTaken")
	@JoinColumn(name = "game")
	private Map<Date, DailyState> dailyStates = new HashMap<Date, DailyState>();

	Game(Long l) {
		this.Uid = l;
	}

	Game() {

	}

	Game(Long Uid, Machine mach) {
		this();
		setUid(Uid);
		this.setMachine(mach);
	}

	Game(Long Uid, Machine mach, Map<Date, DailyState> states) {
		this();
		setUid(Uid);
		this.setMachine(mach);
	}

	public void setUid(Long uid) {
		Uid = uid;
	}

	public Long getUid() {
		return Uid;
	}

	public void setMachine(Machine machine) {
		this.machine = machine;
	}

	public Machine getMachine() {
		return machine;
	}

	public void setDailyStates(Map<Date, DailyState> dailyStates) {
		this.dailyStates = dailyStates;
	}

	public Map<Date, DailyState> getDailyStates() {
		return dailyStates;
	}

	@Override
	public String toString() {
		return "UID : " + Uid.toString() + " - [ Machine : "
				+ getMachine().toString() + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Game))
			return false;
		Game obje = (Game) obj;
		return obje.getUid().equals(this.getUid());
	}

	@Override
	public int hashCode() {
		return this.Uid.hashCode();
	}
}
