package restaurantManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Driver {

	static RestaurantManager mang = new RestaurantManager();

	public static void main(String args[]) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
		// BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		boolean isRunning = true;

		System.out.println("Enter your restaurant configuration:");
		System.out.print(">>How many tables does your pet-friendly section have?");
		int tables = Integer.parseInt(reader.readLine().trim());
		System.out.println(tables);

		setTables(tables, true, reader);

		System.out.print(">>How many tables does your non-pet-friendly section have?");
		tables = Integer.parseInt(reader.readLine().trim());
		System.out.println(tables);

		setTables(tables, false, reader);

		System.out.println("\nSelect from the following menu:");
		System.out.println("\t0.\tClose the restaurant.");
		System.out.println("\t1.\tCustomer party enters the restaurant.");
		System.out.println("\t2.\tCustomer party is seated and served.");
		System.out.println("\t3.\tCustomer party leaves the restaurant.");
		System.out.println("\t4.\tAdd a table.");
		System.out.println("\t5.\tRemove a table.");
		System.out.println("\t6.\tDisplay available tables.");
		System.out.println("\t7.\tDisplay info about waiting customer parties.");
		System.out.println("\t8.\tDisplay info about customer parties being served.\n");

		while(isRunning) {
			System.out.print("Make your selection now: ");
			int input = Integer.parseInt(reader.readLine().trim());
			System.out.println(input);

			switch (input) {
			case 0:
				isRunning = false;
				break;
			case 1:
				enterParty(reader);
				break;
			case 2:
				serveParty(reader);
				break;
			case 3:
				removeParty(reader);
				break;
			case 4:
				addTable(reader);
				break;
			case 5:
				removeTable(reader);
				break;
			case 6:
				printTables();
				break;
			case 7:
				printWaitingParties();
				break;
			case 8:
				printServingParties();
				break;
			}
			if(isRunning)
				System.out.print("\nYou know the options. ");
			else
				System.out.print("We are closing the restaurant... Goodbye!");
		}

		reader.close();
	}

	private static void setTables(int tables, boolean isPetFriendly, BufferedReader reader) throws IOException {
		for(int i = 0; i < tables; i++) {
			System.out.print(">>Enter table name: ");
			String in = reader.readLine().trim();
			System.out.println(in);
			if (mang.containsTable(in, isPetFriendly)) {
				System.out.println(">>This table already exists! Please enter another table name.");
				i--;
			} 
			else {
				System.out.print(">>Enter number of seats: ");
				int seats = Integer.parseInt(reader.readLine().trim());
				System.out.println(seats);
				mang.addTable(new Table(in, seats, isPetFriendly));
			}
		}
	}

	private static void enterParty(BufferedReader reader) throws IOException {
		String name;
		do{
		    System.out.print(">>Enter customer name: ");
		    name = reader.readLine().trim();
		    System.out.println(name);
		}while(!mang.isValidPartyName(name));
		
		System.out.print(">>Enter number of seats for customer " + name + ": ");
		int size = Integer.parseInt(reader.readLine().trim());
		System.out.println(size);
		System.out.print(">>Does your part have pets (Y/N)? ");
		String in = reader.readLine().trim();
		System.out.println(in);
		boolean pets = in.toUpperCase().equals("Y") ? true : false;
		mang.addParty(new Party(pets, size, name));
		
	}

	private static void serveParty(BufferedReader reader) throws IOException {
		if(mang.isLineEmpty())	{
			System.out.println("\tNo customers to serve!");
		}
		else	{
			mang.serveParty();
		}
	}

	private static void removeParty(BufferedReader reader) throws IOException {
		if(mang.areTablesEmpty()) {
			System.out.println("\tNo customer is being served!");
		}
		else	{
			System.out.print(">>Enter the name of the customer that wants to leave:");
			String name = reader.readLine().trim();
			System.out.println(name);
			mang.removeParty(name);
		}
	}

	private static void addTable(BufferedReader reader) throws IOException {
		System.out.println(">>You are now adding a table.");
		System.out.print(" To which section would you like to add this table?(P/N):");
		String in = reader.readLine().trim().toUpperCase();
		System.out.println(in);
		boolean pets = in.equals("P") ? true : false;
		System.out.print(">>Enter table name:");
		String name = reader.readLine().trim();
		System.out.println(name);
		boolean isFindingName = true;
		while(isFindingName)	{
			if(mang.containsTable(name, pets))	{
				System.out.print(" This table already exists in the ");
				if(pets)	{
					System.out.print("pet friendly section!");
				}
				else	{
					System.out.print("non-pet-friendly section!");
				}
				System.out.println("Please enter another table name");
				System.out.print(">>Enter table name: ");
				name = reader.readLine().trim();
				System.out.println(name);
			}
			else	{
				isFindingName = false;
			}
		}
		System.out.println(">>Enter number of seats: ");
		int seats = Integer.parseInt(reader.readLine().trim());
		mang.addTable(new Table(name, seats, pets));
	}

	private static void removeTable(BufferedReader reader) throws IOException {
		System.out.println(">>You are now removing a table.");
		System.out.println(" From which section would you like to remove this table?(P/N):");
		boolean pets;
		if(reader.readLine().trim().toUpperCase().equals("P"))	{
			pets = true;
		}
		else	{
			pets = false;
		}
		System.out.println(">>Enter table name: ");
		String name = reader.readLine().trim();
		if(mang.isTableOccupied(name, pets))	{
			System.out.println("Can't remove a table that is currently in use!");
		}
		else if(!mang.containsTable(name, pets))	{
			System.out.print(" This table doesn't exists in the ");
			if(pets)	{
				System.out.print("pet-friendly section!");
			}
			else	{
				System.out.print("non-pet-friendly section!");
			}
			System.out.println(" Please enter another table name.");
		}
		else	{
			mang.removeTable(name, pets);
			System.out.println("Table " + name + " has been removed.");
		}
	}

	private static void printTables() {
		mang.printTables();
	}

	private static void printWaitingParties() {
		mang.printWaitingParties();
	}

	private static void printServingParties() {
		mang.printServingParties();
	}
}