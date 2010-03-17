/**
 * 
 */
package org.nicolas.sun2010.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author nicolas
 * 
 */
@Entity
public class Manufacturer {
	@Id
	private String Id;

	public void setId(String id) {
		Id = id;
	}

	public String getId() {
		return Id;
	}

	Manufacturer() {

	}

	Manufacturer(String nme) {
		this.setId(nme);
	}

}
