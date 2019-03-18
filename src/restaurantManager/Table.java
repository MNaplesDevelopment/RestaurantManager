package restaurantManager;

public class Table implements Comparable<Table> {

	String name;
	int seats;
	boolean isPetFriendly;
	boolean isOccupied;

	public Table(String name) {
		this.name = name;
	}

	public Table(String name, int seats, boolean isPetFriendly) {
		this.name = name;
		this.seats = seats;
		this.isPetFriendly = isPetFriendly;
	}

	/**
	 * Determines if this table is occupied
	 * 
	 * @return True if is occupied.
	 */
	public boolean isOccupied() {
		return isOccupied;
	}

	/**
	 * Sets occupied.
	 * 
	 * @param occupied
	 *            Updated flag
	 */
	public void setOccupied(boolean occupied) {
		isOccupied = occupied;
	}

	/**
	 * determines if a table is pet friendly
	 * 
	 * @return True if is pet friendly
	 */
	public boolean isPetFriendly() {
		return isPetFriendly;
	}

	/**
	 * Gets the number of seats at the table
	 * 
	 * @return Number of seats.
	 */
	public int getSeats() {
		return seats;
	}

	/**
	 * Gets the name of the table.
	 * 
	 * @return Name of the table.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Turns the table into a readable String.
	 * 
	 * @return String representation of this table.
	 */
	public String toString() {
		return name + " with " + seats + " seats";
	}

	/**
	 * Tables are compared by name.
	 * 
	 * @return 0 if the objects are equals.
	 */
	public int compareTo(Table table) {
		return name.compareTo(table.getName());
	}

}
