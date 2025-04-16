import java.util.ArrayList;
import java.util.Scanner;

public class Banking_Liguan {
    public static void main(String[] args) {
        new Banking_Liguan().run();
    }

    ArrayList<String[]> users;
    String[] currentUser;
    ArrayList<String> transactions;
    Scanner input = new Scanner(System.in);
    boolean isLoggedIn;
    double balance;

    public Banking_Liguan() {
        users = new ArrayList<>();
        transactions = new ArrayList<>();
        isLoggedIn = false;
        balance = 0.0;
        users.add(new String[]{"Admin", "User", "1990-01-01", "admin@example.com", "1234567890", "Admin Address", "1234"});
    }

    public void run() {
        while (true) {
            isLoggedIn = false;
            System.out.println("\nWelcome to Zcash");
            System.out.println("\nLogin Portal");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            int choice = getIntInput("\nEnter your choice: ");

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    if (isLoggedIn) {
                        systemMenu();
                    }
                    break;
                case 3:
                    while (true) {
                        System.out.print("Do you really want to exit? (y/n): ");
                        String exit = input.nextLine().trim().toLowerCase();
                        if (exit.equals("y")) {
                            System.out.println("Exiting...");
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.out.println("Program exited. Goodbye!");
                            input.close();
                            return;
                        } else if (exit.equals("n")) {
                            System.out.println("Exiting cancelled.");
                            break;
                        } else {
                            System.out.println("Invalid input. Please enter only 'y' or 'n'.");
                        }
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    void registerUser() {
        System.out.println("\n--- User Registration ---");
        System.out.print("First Name: ");
        String firstName = input.nextLine();
        System.out.print("Last Name: ");
        String lastName = input.nextLine();

        System.out.print("Birthdate (YYYY-MM-DD): ");
        String birthDate = input.nextLine();
        while (!birthDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            System.out.println("Invalid birthdate format. Please use the format YYYY-MM-DD.");
            System.out.print("Birthdate (YYYY-MM-DD): ");
            birthDate = input.nextLine();
        }

        System.out.print("Email: ");
        String email = input.nextLine();
        while (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            System.out.println("Invalid email format. Please enter a valid email address.");
            System.out.print("Email: ");
            email = input.nextLine();
        }

        System.out.print("Mobile Number: ");
        String mobileNo = input.nextLine();
        while (!mobileNo.matches("\\d+")) {
            System.out.println("Invalid mobile number. Please enter a valid mobile number with digits only.");
            System.out.print("Mobile Number: ");
            mobileNo = input.nextLine();
        }

        System.out.print("Set a 4-digit PIN: ");
        String pin = input.nextLine();
        while (pin.length() != 4 || !pin.matches("\\d+")) {
            System.out.println("Invalid PIN. It must be exactly 4 digits and contain only numbers.");
            System.out.print("Set a 4-digit PIN: ");
            pin = input.nextLine();
        }

        users.add(new String[]{firstName, lastName, birthDate, email, mobileNo, "Home Address", pin});
        System.out.println("Record saved...");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Registration successful! You can now log in.");
    }

    public void loginUser() {
        System.out.println("\n--- User Login ---");

        String[] loggedInUser = null;
        for (int attempts = 1; attempts <= 3; attempts++) {
            System.out.print("Email: ");
            String email = input.nextLine().trim();
            while (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
                System.out.println("Invalid email format. Please enter a valid email address.");
                System.out.print("Email: ");
                email = input.nextLine().trim();
            }
                                    
            for (String[] user : users) {
                if (user[3].equalsIgnoreCase(email)) {
                    loggedInUser = user;
                    break;
                }
            }

            if (loggedInUser == null) {
                System.out.println("Error: Email not found. Please try again. Attempts left: " + (3 - attempts));
                continue;
            }           

            for (String[] user : users) {
                if (user[3].equalsIgnoreCase(email)) {
                    loggedInUser = user;
                    break;
                }
            }

            if (loggedInUser == null) {
                System.out.println("Error: Email not found. Please try again. Attempts left: " + (3 - attempts));
                continue;
            }

            for (int pinAttempts = 1; pinAttempts <= 3; pinAttempts++) {
                System.out.print("Enter PIN: ");
                String pin = input.nextLine().trim();

                if (loggedInUser[6].equals(pin)) {
                    isLoggedIn = true;
                    currentUser = loggedInUser;
                    System.out.println("Logging in...");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Login successful.");
                    return;
                } else {
                    System.out.println("Incorrect PIN! Attempts left: " + (3 - pinAttempts));
                }
            }

            System.out.println("Too many failed PIN attempts. Returning to the main menu...");
            return;
        }

        System.out.println("Too many failed email attempts. Returning to the main menu...");
    }

    private void systemMenu() {
        while (isLoggedIn) {
            System.out.println("\nWelcome, " + currentUser[0] + "!");
            System.out.println("--- Banking Menu ---");
            System.out.println("1. Deposit");
            System.out.println("2. Withdrawal");
            System.out.println("3. Check Balance");
            System.out.println("4. Pay Bills");
            System.out.println("5. Transactions");
            System.out.println("6. Profile");
            System.out.println("7. Logout");

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    deposit();
                    break;
                case 2:
                    withdraw();
                    break;
                case 3:
                    checkBalance();
                    break;
                case 4:
                    payBills();
                    break;
                case 5:
                    showTransactions();
                    break;
                case 6:
                    profile();
                    break;
                case 7:
                    Logout();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    void deposit() {
        double amount = getDoubleInput("Enter amount to deposit: ");
        if (amount > 0) {
            balance += amount;
            transactions.add("Deposited: $" + amount);
            System.out.println("Depositing...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Deposit successful!");
            System.out.println("You deposited $" + amount + " to your account.");
        } else {
            System.out.println("Invalid amount. Try again.");
        }
    }

    void withdraw() {
        double amount = getDoubleInput("Enter amount to withdraw: ");
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactions.add("Withdrew: $" + amount);
            System.out.println("Withdrawing...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Withdrawal successful!");
            System.out.println("You withdrew $" + amount + " from your account.");
        } else {
            System.out.println("Invalid amount or insufficient funds.");
        }
    }

    void checkBalance() {
        System.out.println("Your current balance is: $" + balance);
    }

    void payBills() {
        System.out.println("\nChoose bill to pay:");
        System.out.println("1. Electricity Bill");
        System.out.println("2. Water Bill");

        int billChoice = getIntInput("Enter your choice: ");

        if (billChoice == 1 || billChoice == 2) {
            double amount = getDoubleInput("Enter bill amount: ");

            if (amount > 0 && amount <= balance) {
                balance -= amount;
                String billType = (billChoice == 1) ? "Electricity" : "Water";
                transactions.add("Paid " + billType + " Bill: $" + amount);
                System.out.println("Paying bills...");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(billType + " bill payment successful!");
            } else {
                System.out.println("Invalid amount or insufficient funds.");
            }
        } else {
            System.out.println("Invalid choice. Returning to menu.");
        }
    }

    void showTransactions() {
        System.out.println("\n--- Transaction History ---");
        if (transactions.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (String transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }

   // Updated Profile Method to restrict 'y/n' inputs
    void profile() {
        if (currentUser == null) {
            System.out.println("No user is currently logged in.");
            return;
        }

        System.out.println("\n--- User Profile ---");
        System.out.println("First Name: " + currentUser[0]);
        System.out.println("Last Name: " + currentUser[1]);
        System.out.println("Birthdate: " + currentUser[2]);
        System.out.println("Email: " + currentUser[3]);
        System.out.println("Mobile No: " + currentUser[4]);
        System.out.println("Home Address: " + currentUser[5]);
        System.out.println("PIN: " + currentUser[6]);

        // Asking for profile update confirmation
        System.out.println("\nDo you want to update your profile? (y/n): ");
        String choice = input.nextLine().trim().toLowerCase();

        // Ensuring only 'y' or 'n' is accepted
        while (!choice.equals("y") && !choice.equals("n")) {
            System.out.println("Invalid input. Please enter only 'y' or 'n'.");
            System.out.print("Do you want to update your profile? (y/n): ");
            choice = input.nextLine().trim().toLowerCase();
        }

        if (choice.equals("y")) {
            System.out.println("--- Update Profile ---");

            // Update First Name (optional)
            System.out.print("New First Name (Press Enter to skip): ");
            String newFirstName = input.nextLine();
            if (!newFirstName.isEmpty()) currentUser[0] = newFirstName;

            // Update Last Name (optional)
            System.out.print("New Last Name (Press Enter to skip): ");
            String newLastName = input.nextLine();
            if (!newLastName.isEmpty()) currentUser[1] = newLastName;

            // Update Birthdate (optional, skip allowed)
            System.out.print("New Birthdate (YYYY-MM-DD) (Press Enter to skip): ");
            String newBirthdate = input.nextLine();
                while (!newBirthdate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    System.out.println("Invalid birthdate format. Please use the format YYYY-MM-DD.");
                    System.out.print("New Birthdate (YYYY-MM-DD) (Press Enter to skip): ");
                    newBirthdate = input.nextLine();
                }
                if (!newBirthdate.isEmpty()) {
                currentUser[2] = newBirthdate;
            }

            // Update Email (optional, skip allowed)
            System.out.print("New Email (Press Enter to skip): ");
            String newEmail = input.nextLine();
                while (!newEmail.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
                    System.out.println("Invalid email format. Please enter a valid email address.");
                    System.out.print("New Email (Press Enter to skip): ");
                    newEmail = input.nextLine();
                }
                
            if (!newEmail.isEmpty()) {
                currentUser[3] = newEmail;
            }

            // Update Mobile Number (optional, skip allowed)
            System.out.print("New Mobile Number (Press Enter to skip): ");
            String newMobile = input.nextLine();
                while (!newMobile.matches("\\d+")) {
                    System.out.println("Invalid mobile number. Please enter a valid mobile number with digits only.");
                    System.out.print("New Mobile Number (Press Enter to skip): ");
                    newMobile = input.nextLine();
                }
                
            if (!newMobile.isEmpty()) {
                currentUser[4] = newMobile;
            }

            // Update Home Address (optional)
            System.out.print("New Home Address (Press Enter to skip): ");
            String newAddress = input.nextLine();
            if (!newAddress.isEmpty()) currentUser[5] = newAddress;

            // Update PIN (optional)
            System.out.print("Set a New 4-digit PIN (Press Enter to skip): ");
            String newPin = input.nextLine();
            if (!newPin.isEmpty()) {
                while (newPin.length() != 4 || !newPin.matches("\\d+")) {
                    System.out.println("Invalid PIN. It must be exactly 4 digits and contain only numbers.");
                    System.out.print("Set a New 4-digit PIN (Press Enter to skip): ");
                    newPin = input.nextLine();
                }
                currentUser[6] = newPin;
            }

            System.out.println("Updating profile...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Profile updated successfully!");
        } else {
            System.out.println("Returning to menu...");
        }
    }


    void Logout() {
        while (true) {
            System.out.print("Are you sure you want to logout? (y/n): ");
            String confirm = input.nextLine().trim().toLowerCase();
            if (confirm.equals("y")) {
                System.out.println("Logging out... Returning to login menu.");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Logged out successfully.");
                isLoggedIn = false;
                return;
            } else if (confirm.equals("n")) {
                System.out.println("Logout canceled. Returning to menu...");
                return;
            } else {
                System.out.println("Invalid input. Please input only 'y' or 'n'.");
            }
        }
    }

    int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    double getDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid amount.");
            }
        }
    }
}
