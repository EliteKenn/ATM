package ATM;

import java.util.*;

public class ATM {
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		Bank theBank = new Bank("Bank of Baendit");

//		Add a user, which also creates a savings account

		Client aClient = theBank.addClient("John", "Doe", "1234");

		Account checkings = new Account("Checking", aClient, theBank);
		aClient.addAccount(checkings);
		theBank.addAccount(checkings);

		Client curClient;

		while (true) {

			curClient = ATM.mainMenuPrompt(theBank, sc);

			ATM.printClientMenu(curClient, sc);

		}
	}

	public static Client mainMenuPrompt(Bank theBank, Scanner sc) {
		int count = 0;
		String userID;
		String pin;
		Client authUser;
		do {

			System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
			System.out.print("Enter user ID: ");
			userID = sc.nextLine();
			System.out.print("Enter pin: ");
			pin = sc.nextLine();

			authUser = theBank.userLogin(userID, pin);
			if (authUser == null) {
				count++;
				System.out
						.println("Incorrect user ID/pin combo. " + "Please try again" + "\n1Attempt: " + count + "/5");
			}
			if (count == 5) {
				System.out.println("You've entered invalid login information. Please try again later!");
				System.exit(1);
			}

		} while (authUser == null); // con't until successful login

		return authUser;
	}

	public static void printClientMenu(Client theClient, Scanner sc) {

		// print summary of Client's account(s)

		theClient.printAccountsSummary();

		int choice;

		// User menu

		do {
			System.out.printf("Welcome %s! What would you like to do? \n", theClient.getFirstName());
			System.out.println(" 1)Show Account Transaction History");
			System.out.println(" 2)Withdraw Funds");
			System.out.println(" 3)Deposit Funds");
			System.out.println(" 4)Transfer Funds");
			System.out.println(" 5)Quit");
			System.out.println();
			System.out.println("Enter choice:");
			choice = sc.nextInt();

			if (choice < 1 || choice > 5) {
				System.out.println("Invalid choice. Please choose 1-5");
			}

		} while (choice < 1 || choice > 5);

		// process each choice

		switch (choice) {
		case 1:
			ATM.showTransactionHistory(theClient, sc);
			break;
		case 2:
			ATM.withdrawFunds(theClient, sc);
			break;
		case 3:
			ATM.depositFunds(theClient, sc);
			break;
		case 4:
			ATM.transferFunds(theClient, sc);
			break;
		case 5:
			sc.nextLine();
			System.out.print("Thank You " + theClient.getFirstName() + " for using Baendit Entertainment Banking "
					+ "Have a great day!");
			System.exit(1);
		}

		if (choice != 5) {
			ATM.printClientMenu(theClient, sc);
		}

	}

	/**
	 * Show transaction history for an account
	 * 
	 * @param theClient
	 * @param sc
	 */

	/**
	 * Process transferring funds from one account to another
	 * 
	 * @param theClient
	 * @param sc
	 */
	public static void transferFunds(Client theClient, Scanner sc) {
		int fromAcct;
		int toAcct;
		double amount;
		double acctBalance;

		// get the account to transfer from
		do {

			System.out.printf("Enter the number(1-%d) of the account\n" + "to transfer from: ",
					theClient.numOfAccounts());
			fromAcct = sc.nextInt() - 1;
			if (fromAcct < 0 || fromAcct >= theClient.numOfAccounts()) {
				System.out.println("Invalid account, please try again");
			}

		} while (fromAcct < 0 || fromAcct >= theClient.numOfAccounts());

		acctBalance = theClient.getAcctBalance(fromAcct);

		// get the account to transfer to
		do {

			System.out.printf("Enter the number(1-%d) of the account\n" + "to transfer to: ",
					theClient.numOfAccounts());
			toAcct = sc.nextInt() - 1;
			if (toAcct < 0 || toAcct >= theClient.numOfAccounts()) {
				System.out.println("Invalid account, please try again");
			}

		} while (toAcct < 0 || toAcct >= theClient.numOfAccounts());

		// get the amount to transfer
		do {
			System.out.printf("Enter the amount to transfer (max $%.2f): $", acctBalance);
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than $0");
			} else if (amount > acctBalance) {
				System.out.printf("Amount must not be greater than \n" + "balance of $%.2f.\n", acctBalance);
			}
		} while (amount < 0 || amount >= acctBalance);

		// finally do the transfer
		theClient.addAccountTransaction(fromAcct, -1 * amount,
				String.format("Transfer to account %s", theClient.getAcctUUID(toAcct)));
		theClient.addAccountTransaction(toAcct, amount,
				String.format("Transfer to account %s", theClient.getAcctUUID(fromAcct)));

	}

	/**
	 * Process a fund withdraw from an account
	 * 
	 * @param theClient
	 * @param sc
	 */
	public static void withdrawFunds(Client theClient, Scanner sc) {
		int fromAcct;
		double amount;
		double acctBalance;
		String memo;
		// get the account to transfer from
		do {

			System.out.printf("Enter the number(1-%d) of the account\n" + "to withdraw from:",
					theClient.numOfAccounts());
			fromAcct = sc.nextInt() - 1;
			if (fromAcct < 0 || fromAcct >= theClient.numOfAccounts()) {
				System.out.println("Invalid account, please try again");
			}

		} while (fromAcct < 0 || fromAcct >= theClient.numOfAccounts());

		acctBalance = theClient.getAcctBalance(fromAcct);

		do {

			System.out.printf("\nEnter the amount to withdraw (max $%.2f): $", acctBalance);
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than $0");
			} else if (amount > acctBalance) {
				System.out.printf("Amount must not be greater than \n" + "balance of $%.2f.\n", acctBalance);
			}
		} while (amount < 0 || amount >= acctBalance);

		sc.nextLine();

		System.out.println("Enter a memo: ");
		memo = sc.nextLine();

		// do the withdrawl
		theClient.addAccountTransaction(fromAcct, -1 * amount, memo);
	}

	/**
	 * Process a fund deposit to an account
	 * 
	 * @param theClient the logged-in user object
	 * @param sc        The scanner object used for user input
	 */
	public static void depositFunds(Client theClient, Scanner sc) {
		int toAcct;
		double amount;
		String memo;
		// get the account to transfer from
		do {

			System.out.printf("Enter the number(1-%d) of the account\n" + "to deposit in: ", theClient.numOfAccounts());
			toAcct = sc.nextInt() - 1;
			if (toAcct < 0 || toAcct >= theClient.numOfAccounts()) {
				System.out.println("Invalid account, please try again");
			}

		} while (toAcct < 0 || toAcct >= theClient.numOfAccounts());

		do {
			System.out.printf("Enter the amount to deposit in: $");
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than $0");
			}
		} while (amount < 0);

		sc.nextLine();

		System.out.println("Enter a memo: ");
		memo = sc.nextLine();

		// do the withdrawl
		theClient.addAccountTransaction(toAcct, amount, memo);
	}

	public static void showTransactionHistory(Client theClient, Scanner sc) {

		int theAcct;

		// get acount whose transaction history to look at

		do {
			System.out.printf("Enter the number (1-%d) of the account\n" + "whose transactions you want to see: ",
					theClient.numOfAccounts());
			theAcct = sc.nextInt() - 1;
			if (theAcct < 0 || theAcct >= theClient.numOfAccounts())
				System.out.println("Invalid account. Please try again");
		} while (theAcct < 0 || theAcct >= theClient.numOfAccounts());

		// print the transaction history
		theClient.printAcctTransHistory(theAcct);
	}

}
