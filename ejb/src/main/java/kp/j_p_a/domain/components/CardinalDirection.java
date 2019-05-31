package kp.j_p_a.domain.components;

/**
 * The <b>cardinal direction</B> enumeration.
 *
 */
public enum CardinalDirection {

	NORTH, EAST, SOUTH, WEST;

	/**
	 * Gets the next cardinal direction.
	 * 
	 * @return the next cardinal direction
	 */
	public CardinalDirection getNext() {
		switch (this) {
		case NORTH:
			return EAST;
		case EAST:
			return SOUTH;
		case SOUTH:
			return WEST;
		default:// WEST
			return NORTH;
		}
	}
}
