import java.io.*;
import java.util.*;

abstract class AbstractBankAccount {
    protected String accNumber;
    protected String accHolderName;
    protected double balance;

    public AbstractBankAccount(String accNumber, String accHolderName, double balance) {
        this.accNumber = accNumber;
        this.accHolderName = accHolderName;
        this.balance = balance;
    }

    public abstract void deposit(double amount);
    public abstract void withdraw(double amount);

    public void displayDetails() {
        System.out.println("Account Number: " + accNumber);
        System.out.println("Account Holder: " + accHolderName);
        System.out.println("Balance: " + balance);
    }

    public double getBalance() {
        return balance;
    }
}

class SavingsAccount extends AbstractBankAccount {
    public SavingsAccount(String accNumber, String accHolderName, double balance) {
        super(accNumber, accHolderName, balance);
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited " + amount + " in Savings Account.");
    }

    public void withdraw(double amount) {
        if (balance - amount >= 1000) {
            balance -= amount;
            System.out.println("Withdrawn " + amount + " from Savings Account.");
        } else {
            System.out.println("Withdrawal failed! Minimum balance of 1000 required.");
        }
    }
}

class CurrentAccount extends AbstractBankAccount {
    public CurrentAccount(String accNumber, String accHolderName, double balance) {
        super(accNumber, accHolderName, balance);
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited " + amount + " in Current Account.");
    }

    public void withdraw(double amount) {
        if (balance - amount >= 0) {
            balance -= amount;
            System.out.println("Withdrawn " + amount + " from Current Account.");
        } else {
            System.out.println("Withdrawal failed! Insufficient balance.");
        }
    }
}

class FileHandler {
    private static final String FILE_NAME = "bankdata.txt";

    public static void saveAccount(AbstractBankAccount account) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(account.accNumber + "," + account.accHolderName + "," + account.getBalance());
            bw.newLine();
            System.out.println("Account saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving account: " + e.getMessage());
        }
    }

    public static void displayFileData() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            System.out.println("Accounts in File:");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}

public class BankManagementSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AbstractBankAccount account = null;

        while (true) {
            System.out.println("\nBank Management System");
            System.out.println("1. Create Savings Account");
            System.out.println("2. Create Current Account");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Balance Enquiry");
            System.out.println("6. Display Account Details");
            System.out.println("7. Save Account to File");
            System.out.println("8. Display Accounts from File");
            System.out.println("9. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Account Number: ");
                    String sAccNo = sc.nextLine();
                    System.out.print("Enter Account Holder Name: ");
                    String sName = sc.nextLine();
                    System.out.print("Enter Initial Balance: ");
                    double sBal = sc.nextDouble();
                    account = new SavingsAccount(sAccNo, sName, sBal);
                    System.out.println("Savings Account Created.");
                    break;

                case 2:
                    System.out.print("Enter Account Number: ");
                    String cAccNo = sc.nextLine();
                    System.out.print("Enter Account Holder Name: ");
                    String cName = sc.nextLine();
                    System.out.print("Enter Initial Balance: ");
                    double cBal = sc.nextDouble();
                    account = new CurrentAccount(cAccNo, cName, cBal);
                    System.out.println("Current Account Created.");
                    break;

                case 3:
                    if (account != null) {
                        System.out.print("Enter Deposit Amount: ");
                        double dep = sc.nextDouble();
                        account.deposit(dep);
                    } else {
                        System.out.println("No account created yet.");
                    }
                    break;

                case 4:
                    if (account != null) {
                        System.out.print("Enter Withdrawal Amount: ");
                        double wd = sc.nextDouble();
                        account.withdraw(wd);
                    } else {
                        System.out.println("No account created yet.");
                    }
                    break;

                case 5:
                    if (account != null) {
                        System.out.println("Balance: " + account.getBalance());
                    } else {
                        System.out.println("No account created yet.");
                    }
                    break;

                case 6:
                    if (account != null) {
                        account.displayDetails();
                    } else {
                        System.out.println("No account created yet.");
                    }
                    break;

                case 7:
                    if (account != null) {
                        FileHandler.saveAccount(account);
                    } else {
                        System.out.println("No account created yet.");
                    }
                    break;

                case 8:
                    FileHandler.displayFileData();
                    break;

                case 9:
                    System.out.println("Exiting... Goodbye!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
