package ATM;

import java.util.Date;

public class Transaction {

	private double amount;
	private Date timeStamp;
	private String memo;
	private Account inAccount;

	/**
	 * 
	 * @param amount
	 * @param inAccount
	 */
	public Transaction(double amount, Account inAccount) {

		this.amount = amount;
		this.inAccount = inAccount;
		this.timeStamp = new Date();
		this.memo = "";
	}

	/**
	 * 
	 * @param amount
	 * @param name
	 * @param inAccount
	 */
	public Transaction(double amount, String name, Account inAccount) {

		// Call the two-arg constructor first
		this(amount, inAccount);
		this.memo = memo;
	}

	/**
	 * Returns the current amount the Client owns
	 * 
	 * @return double.
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * Get a string summarizing the transaction(s)
	 * 
	 * @return
	 */
	public String getSummaryLine() {

		return this.amount >= 0 ? String.format("%s : $%.2f : %s", this.timeStamp.toString(), this.amount, this.memo)
				: String.format("%s : $(%.2f) : %s", this.timeStamp.toString(), -this.amount, this.memo);

	}

}
