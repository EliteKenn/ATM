package ATM;

import java.util.List;
import java.util.ArrayList;

public class Account {

	private String name;

	private String uuID;

	private Client holder;

	private List<Transaction> transactions = new ArrayList<>();

	/**
	 * 
	 * @param name
	 * @param holder
	 * @param theBank
	 */
	public Account(String name, Client holder, Bank theBank) {

		this.name = name;
		this.holder = holder;

		// get acc UUID
		this.uuID = theBank.getNewAccountUUID();

		this.transactions = new ArrayList<Transaction>();

//		//add holder and banklist

	}

	public String getUUID() {
		return uuID;
	}

	/**
	 */
	public String getSummaryLine() {
		double balance = this.getBalance();

		// format the summary line, depending on the whether the account is overdrawn
//		if(balance >=0) {
//			return System.out.printf("%s : $%0.2f : $s", uuID,balance,name);
//		}
		return balance >= 0 ? String.format("%s : $(%.02f) : %s", uuID, balance, name)
				: String.format("%s : $(%.02f) : %s", uuID, balance, name);

	}

	/**
	 * Get the balance of this account by adding the amount of of the transactions
	 * 
	 * @return the balance
	 */
	public double getBalance() {
		double balance = 0;

		for (Transaction t : transactions) {
			balance += t.getAmount();
		}
		return balance;
	}

	/**
	 * Print the transaction history
	 */
	public void printTransHistory() {

		System.out.printf("\nTransaction history for account %s\n", this.uuID);
		for (int t = transactions.size() - 1; t >= 0; t--) {
			System.out.println(this.transactions.get(t).getSummaryLine());
		}
		System.out.println();
	}

	public void addTransaction(double amount, String memo) {
		// Create new transaction object, add it to our list

		Transaction newTrans = new Transaction(amount, memo, this);
		this.transactions.add(newTrans);
	}

}
