package org.nicolas.sun2010.model;

import java.util.Date;
import java.util.Map;

/**
 * Una factory para construir los items dentro del modelo.
 * 
 * @author nicolas
 * */

public class ModelFactory {

	public static Manufacturer createManufacturer(String nme) {
		return new Manufacturer(nme);
	}

	public static Manufacturer createManufacturer() {
		return new Manufacturer();
	}

	public static Machine createMachine() {
		return new Machine();
	}

	public static Machine createMachine(Manufacturer m, String name) {
		return new Machine(m, name);
	}

	public static Game createGame() {
		return new Game();
	}

	public static Game createGame(Long Uid, Machine mach) {
		return new Game(Uid, mach);
	}

	public static Game createGame(Long Uid, Machine mach,
			Map<Date, DailyState> states) {
		return new Game(Uid, mach, states);
	}

	public static DailyStatePK createDailyStatePK() {
		return new DailyStatePK();
	}

	public static DailyStatePK createDailyStatePK(Long g, Date d) {
		return new DailyStatePK(g, d);
	}

	public static Game createGame(Long l) {
		return new Game(l);
	}

	public static DailyState createDailyState(Date d, Long g, Double bet,
			Double yield) {
		return new DailyState(g, d, bet, yield);
	}

	public static DailyState createDailyState() {
		return new DailyState();
	}

}
