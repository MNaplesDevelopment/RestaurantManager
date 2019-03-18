package restaurantManager;

public class Party implements Comparable<Party>	{

	boolean isPetFriendly;
	int size;
	String name;
	Table table;
	
	public Party(String name)	{
		this.name = name;
	}
	
	public Party(boolean petFriendly, int size, String name) {
		isPetFriendly = petFriendly;
		this.size = size;
		this.name = name;
	}
	
	public void setTable(Table table)	{
		this.table = table;
		table.setOccupied(true);
	}
	
	public void clearTable()	{
		table.setOccupied(false);
		table = null;
	}

	public boolean isPetFriendly() {
		return isPetFriendly;
	}

	public void setPetFriendly(boolean isPetFriendly) {
		this.isPetFriendly = isPetFriendly;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString()	{
		String result = name + " party of " + size;
		result += isPetFriendly ? "(Pet)" : "(No Pet)";
		return result;
	}
	
	public int compareTo(Party party)	{
		return name.compareTo(party.getName());
	}
}
