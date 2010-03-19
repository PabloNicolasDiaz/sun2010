/**
 * 
 */
package org.nicolas.sun2010.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author nicolas
 * 
 */
@Entity
public class Machine {

	@ManyToOne(cascade = CascadeType.ALL)
	private Manufacturer manufacturer;

	@Id
	private String Id;

	Machine() {

	}

	Machine(Manufacturer m, String name) {
		this.setManufacturer(m);
		this.setId(name);
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public void setId(String name) {
		this.Id = name;
	}

	public String getId() {
		return Id;
	}

	@Override
	public String toString() {
		return "ID : " + getId().toString() + " - [ Manufacturer : "
				+ getManufacturer().toString() + "]";
	}

}
