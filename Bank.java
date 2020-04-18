package ATM;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bank {

	private String name;

	private List<Client> listOfUsers = new ArrayList<>();

	private List<Account> listOfAccounts = new ArrayList<>();

	/**
	 */

	/**
	 * Generate a new universally unique ID for a Client
	 * 
	 * @return
	 */

	public Bank(String theBank) {
		this.name = theBank;
		this.listOfUsers = new ArrayList<Client>();
		this.listOfAccounts = new ArrayList<Account>();
	}

	public String getNewUserUUID() {

		String uuID;
		Random rng = new Random();
		int length = 7;
		boolean nonUnique = false;

		// loop until we get a unique ID/when nonUnique = true
		do {

			uuID = "";
			for (int c = 0; c < length; c++) {// Generate numbers between 0 inclusive & 10 exclusive
				uuID += ((Integer) rng.nextInt(10)).toString(); // Casting the rng from int primitive to Integer wrapper
																// class
			}
			nonUnique = false;
			for (Client c : this.listOfUsers) {
				if (uuID.compareTo(c.getUUID()) == 0) {
					nonUnique = true;
					break;
				}
			}

		} while (nonUnique);

		return uuID;
	}

	/**
	 * Generate a new universally unique id for each account
	 * 
	 * @return
	 */
	public String getNewAccountUUID() {
		String uuID;
		Random rng = new Random();
		int length = 10;
		boolean nonUnique;

		do {
			uuID = "";
			for (int c = 0; c < length; c++) {
				uuID += ((Integer) rng.nextInt(10)).toString();
			}
			nonUnique = false;
			for (Account a : this.listOfAccounts) {
				if (uuID.compareTo(a.getUUID()) == 0) {
					nonUnique = true;
					break;
				}
			}
		} while (nonUnique);
		return uuID;
	}

	public void addAccount(Account onAcct) {
		listOfAccounts.add(onAcct);
	}

	// Create a new Client
	/**
	 * 
	 * @param firstName
	 * @param lastName
	 * @param PIN
	 * @return
	 */
	public Client addClient(String firstName, String lastName, String PIN) {

//		Create a new Client object and add it to the list
		Client newClient = new Client(firstName, lastName, PIN, this);
		this.listOfUsers.add(newClient);

		// Create a savings acc for the Client
		Account newAccount = new Account("Savings", newClient, this);
		newClient.addAccount(newAccount);
		this.addAccount(newAccount);

		return newClient;
	}

	/**
	 * 
	 * @param userID
	 * @param PIN
	 * @return
	 */
	public Client userLogin(String userID, String PIN) {
		for (Client c : listOfUsers) {
			if (c.getUUID().compareTo(userID) == 0 && c.validatePin(PIN)) {
				return c;
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}
}
