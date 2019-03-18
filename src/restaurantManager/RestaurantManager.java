package restaurantManager;

import list.ListRA;
import list.AscendinglyOrderedList;

public class RestaurantManager {
	
	AscendinglyOrderedList<Table> petTables;
	AscendinglyOrderedList<Table> nonPetTables;
	ListRA<Party> waitingParties;
	AscendinglyOrderedList<Party> servingParties;
	
	public RestaurantManager()	{
		petTables = new AscendinglyOrderedList<Table>();
		nonPetTables = new AscendinglyOrderedList<Table>();
		waitingParties = new ListRA<Party>();
		servingParties = new AscendinglyOrderedList<Party>();
	}
	
	/**
	 * adds a party to the end of the list to maintain a queued order
	 * 
	 * @param party Party to be added.
	 */
	public void addParty(Party party)	{
		waitingParties.add(party);
	}
	
	/**
	 * Adds table to appropriate collection
	 * 
	 * @param table Table to be added.
	 */
	public void addTable(Table table)	{
		if(table.isPetFriendly())	{
			petTables.add(table);
		}
		else	{
			nonPetTables.add(table);
		}
	}
	
	/**
	 * Precondition - containsTable() must return true for the give information.
	 * Removes a table from the appropriate section
	 * 
	 * @param name Name of the table to be removed
	 * @param isPetFriendly Determines the section the table is in
	 */
	public void removeTable(String name, boolean isPetFriendly)	{
		if(isPetFriendly)	{
			petTables.remove(petTables.search(new Table(name)));
		}
		else	{
			nonPetTables.remove(nonPetTables.search(new Table(name)));
		}
	}
	
	/**
	 * Determines if a table exists.
	 * @param name Name of the table in question
	 * @param isPetFriendly Section table is in.
	 * @return True if the table is found.
	 */
	public boolean containsTable(String name, boolean isPetFriendly)	{
		int ndx = isPetFriendly ? petTables.search(new Table(name)) : nonPetTables.search(new Table(name));
		return ndx != -1;
	}
	
	/**
	 * Serves the next available party.
	 */
	public void serveParty()	{
		boolean searching = true;
		for(int i = 0; i < waitingParties.size() && searching; i++)	{
			Party party = waitingParties.get(i);
			Table table = findOpenTable(party);
			if(table != null)	{
				servingParties.add(party);
				party.setTable(table);
				waitingParties.remove(i);
				searching = false;
				System.out.println("Serving Customer " + party + " at table " + table);
			}
		}
	}
	/**
	 * Attempts to find a table appropriate for a given party. Performs a sequential 
	 * search for a table matching the size of the party, if no exact match is found 
	 * the table with least amount of seats that can seat the party is returned.
	 * 
	 * @param party Party to be seated if a table is available.
	 * @return The most appropriate table for a given party, null if no table is found.
	 */
	private Table findOpenTable(Party party)	{
		int min = Integer.MAX_VALUE;
		int size = party.getSize();
		int ndx = -1;
		if(party.isPetFriendly())	{
			for(int i = 0; i < petTables.size(); i++)	{
				Table temp = petTables.get(i);
				if(!temp.isOccupied())	{
					if(temp.getSeats() == size)	{
						return temp;
					}
					if(min < temp.getSeats())	{
						min = temp.getSeats();
						ndx = i;
					}
				}
			}
			return (ndx != -1) ? petTables.get(ndx) : null;
		}
		else	{
			for(int i = 0; i < nonPetTables.size(); i++)	{
				Table temp = nonPetTables.get(i);
				if(!temp.isOccupied())	{
					if(temp.getSeats() == size)	{
						return temp;
					}
					if(min < temp.getSeats())	{
						min = temp.getSeats();
						ndx = i;
					}
				}
			}
			return (ndx != -1) ? nonPetTables.get(ndx) : null;
		}
	}
	
	/**
	 * Removes a party from the restaurant if they have already been served.
	 * 
	 * @param name Name of party that wishes to leave.
	 */
	public void removeParty(String name)	{
		int ndx = servingParties.search(new Party(name));
		if(ndx == -1)	{
			for(int i = 0; i < waitingParties.size(); i++)	{
				Party temp = waitingParties.get(i);
				if(temp.getName().equals(name))	{
					System.out.println("Customer " + temp.getName() + " is not being served but waiting to be seated.");
					break;
				}
			}
		}
		else	{
			servingParties.get(ndx).clearTable();
			servingParties.remove(ndx);
		}
	}
	
	/**
	 * Prints all available tables.
	 */
	public void printTables()	{
		System.out.println("The following " + petTables.size() + " tables are available in the pet-friendly section");
		for(int i = 0; i < petTables.size(); i++)	{
			Table table = petTables.get(i);
			if(!table.isOccupied())	{
				System.out.println("Table " + table);
			}
		}
		System.out.println("The following " + nonPetTables.size() + " tables are available in the non-pet-friendly section");
		for(int i = 0; i < nonPetTables.size(); i++)	{
			Table table = nonPetTables.get(i);
			if(!table.isOccupied())	{
				System.out.println("Table " + table);
			}
		}
	}
	
	/**
	 * Prints all waiting parties.
	 */
	public void printWaitingParties()	{
		if(waitingParties.isEmpty())	{
			System.out.println("\tNo customers are being served!");
		}
		else	{
			System.out.println("The following customer parties are waiting for tables: ");
			for(int i = 0; i < waitingParties.size(); i++)	{
				System.out.println("Customer " + waitingParties.get(i));
			}
		}
	}
	
	/**
	 * Prints all serving parties
	 */
	public void printServingParties()	{
		if(servingParties.isEmpty())	{
			System.out.println("\tNo customers are being served!");
		}
		System.out.println("The following customer is being served in the pet-friendly section:");
		for(int i = 0; i < servingParties.size(); i++)	{
			System.out.println(servingParties.get(i));
		}
	}
	
	/**
	 * Determines if the line is empty.
	 * 
	 * @return True if no parties are waiting in line.
	 */
	public boolean isLineEmpty()	{
		return waitingParties.isEmpty();
	}
	
	/**
	 * Determines if there are any parties currently being served.
	 * 
	 * @return True if no parties are being served.
	 */
	public boolean areTablesEmpty()	{
		return servingParties.isEmpty();
	}
	
	/**
	 * Checks to see if a given table is occupied.
	 * 
	 * @param name Name of table
	 * @param isPetFriendly Section of table
	 * @return True if the table is occupied.
	 */
	public boolean isTableOccupied(String name, boolean isPetFriendly)	{
		int ndx = (isPetFriendly) ? petTables.search(new Table(name)) : nonPetTables.search(new Table(name));
		if(ndx == -1)
			return false;
		return (isPetFriendly) ? petTables.get(ndx).isOccupied() : nonPetTables.get(ndx).isOccupied();
	}
	
	/**
	 * Determines if a party name is valid.
	 * 
	 * @param name Name in question.
	 * @return True if the name is not already in use.
	 */
	public boolean isValidPartyName(String name)	{
		for(int i = 0; i < waitingParties.size(); i++)	{
			if(waitingParties.get(i).compareTo(new Party(name)) == 0)	{
				return false;
			}
		}
		return servingParties.search(new Party(name)) == -1;
	}
}
