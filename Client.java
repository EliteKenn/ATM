package ATM;

import java.util.List;
import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Client {

	// Attributes.
	/**
	 */
	private String firstName;
	private String lastName;
	private String uuID;
	// Using MD5 algorithm to hash the PIN
	private byte pinHash[];
	private List<Account> accounts = new ArrayList<>();

	public Client(String firstName, String lastName, String PIN, Bank theBank) {
		this.firstName = firstName;
		this.lastName = lastName;

		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			this.pinHash = md5.digest(PIN.getBytes());// Getting the bytes from our pin and digesting them thru our md5
														// algorithm
			// We'll then store it in our pinHash
		} catch (NoSuchAlgorithmException ex) {
			ex.getMessage();
			System.exit(0);
		}

		// Get a new unique universal ID
		this.uuID = theBank.getNewUserUUID();

		this.accounts = new ArrayList<Account>();

		System.out.printf("New User %s, %s with ID %s created.\n", lastName, firstName, this.uuID);

	}

	/**
	 * 
	 * @param onAcct
	 */
	public void addAccount(Account onAcct) {
		accounts.add(onAcct);

	}

	public String getUUID() {
		return uuID;
	}

	/**
	 * 
	 * @param aPin
	 * @return
	 */
	public boolean validatePin(String aPin) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(aPin.getBytes()), pinHash);
		} catch (NoSuchAlgorithmException e) {
			System.err.println("eror, caught NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}
		return false;
	}

	public String getFirstName() {
		return firstName;
	}

	/**
	 * Print summaries for the accounts for the Client
	 */
	public void printAccountsSummary() {

		System.out.printf("\n\n%s's accounts summary:\n", this.firstName);
		for (int a = 0; a < accounts.size(); a++) {
			System.out.printf("%d) %s\n", a + 1, accounts.get(a).getSummaryLine());
		}
		System.out.println();
	}

	public int numOfAccounts() {
		return accounts.size();
	}

	public void printAcctTransHistory(int accIndex) {
		this.accounts.get(accIndex).printTransHistory();
	}

	public double getAcctBalance(int index) {
		return this.accounts.get(index).getBalance();
	}

	/**
	 * Get the UUID of a particular account
	 * 
	 * @param accIndex
	 * @return
	 */
	public String getAcctUUID(int accIndex) {
		return this.accounts.get(accIndex).getUUID();
	}

	public void addAccountTransaction(int acctIndex, double amount, String memo) {
		accounts.get(acctIndex).addTransaction(amount, memo);
	}
}
