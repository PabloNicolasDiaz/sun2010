/**
 * 
 */
package org.nicolas.sun2010.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;

/**
 * @author nicolas
 * 
 */
@Embeddable
public class DailyStatePK implements Serializable {
	DailyStatePK() {

	}

	DailyStatePK(Long g, Date d) {
		this.setGame(g);
		this.setDateTaken(d);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8939854461798012751L;

	private Long game;
	private Date dateTaken;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof DailyStatePK))
			return false;

		if (getClass() != obj.getClass())
			return false;

		DailyStatePK dp = (DailyStatePK) obj;
		return (game == dp.game)
				|| (game != null && game.equals(dp.game))
				&& (dateTaken == dp.dateTaken || (dateTaken != null && dateTaken
						.equals(dp.dateTaken)));
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result
				+ (this.dateTaken == null ? 0 : this.dateTaken.hashCode());
		result = 37 * result + (this.game == null ? 0 : this.game.hashCode());
		return result;
	}

	public void setGame(Long game) {
		this.game = game;
	}

	public Long getGame() {
		return game;
	}

	public void setDateTaken(Date dateTaken) {
		this.dateTaken = dateTaken;
	}

	public Date getDateTaken() {
		return dateTaken;
	}

}
