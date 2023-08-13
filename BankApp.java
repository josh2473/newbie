package momo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankApp {
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";
    private double balance;
    private String username;
    private String password;

    // Constructor to initialize the account with a balance of GHs 3000
    public BankApp(String username, String password) {
        this.balance = 3000;
        this.username = username;
        this.password = password;
    }

    // Method to handle deposit operation
    public void deposit(double amount) {
        balance += amount;
    }

    // Method to handle withdrawal operation
    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            showInfo("Withdrawal successful!");
        } else {
            showError("Insufficient balance.");
        }
    }

    // Method to check account balance
    public double getBalance() {
        return balance;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String titleIcon = "BankAppTitleIcon.png"; 
            ImageIcon bankAppIcon = new ImageIcon(titleIcon);

            String adminUsernameInput = JOptionPane.showInputDialog(null, "Enter admin username:");
            String adminPasswordInput = JOptionPane.showInputDialog(null, "Enter admin password:");

            if (!adminUsernameInput.equals(ADMIN_USERNAME) || !adminPasswordInput.equals(ADMIN_PASSWORD)) {
                showError("Invalid admin credentials. Exiting...");
                return;
            }

            BankApp bankApp = performRegistration();
            if (bankApp != null) {
                showInfo("Registration successful!\n" +
                        "Your account has been credited with GHs 3000.00.\n" +
                        "You can now log in.");

                showLoginScreen(bankApp, bankAppIcon);
            }
        });
    }

    public static void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showInfo(String message) {
        JOptionPane.showMessageDialog(null, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public static BankApp performRegistration() {
        String newUsername = JOptionPane.showInputDialog(null, "Enter a new username:");
        String newPassword = JOptionPane.showInputDialog(null, "Enter a new password:");

        return new BankApp(newUsername, newPassword);
    }

    public static void showLoginScreen(BankApp bankApp, ImageIcon titleIcon) {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(300, 200);
        loginFrame.setLayout(new GridLayout(4, 2, 10, 10));
        loginFrame.getContentPane().setBackground(new Color(255, 240, 200)); // Light yellow background color

        loginFrame.setIconImage(titleIcon.getImage());

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JButton loginButton = createStyledButton("LoginButton.png", "Login");
        JButton exitButton = createStyledButton("ExitButton.png", "Exit");

        loginButton.addActionListener(e -> {
            String enteredUsername = usernameField.getText();
            char[] enteredPasswordChars = passwordField.getPassword();
            String enteredPassword = new String(enteredPasswordChars);

            if (enteredUsername.equals(bankApp.username) && enteredPassword.equals(bankApp.password)) {
                showMainTransactionScreen(bankApp, titleIcon);
                loginFrame.dispose(); // Close the login frame
            } else {
                showError("Invalid username or password.");
            }
        });

        exitButton.addActionListener(e -> System.exit(0));

        loginFrame.add(new JLabel()); // Empty cell for layout
        loginFrame.add(new JLabel()); // Empty cell for layout
        loginFrame.add(usernameLabel);
        loginFrame.add(usernameField);
        loginFrame.add(passwordLabel);
        loginFrame.add(passwordField);
        loginFrame.add(loginButton);
        loginFrame.add(exitButton);

        loginFrame.setVisible(true);
    }

    public static void showMainTransactionScreen(BankApp bankApp, ImageIcon titleIcon) {
        JFrame mainFrame = new JFrame("BankApp");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(300, 200);
        mainFrame.setLayout(new GridLayout(2, 2, 10, 10));
        mainFrame.getContentPane().setBackground(new Color(225, 237, 255)); // Light blue background color

        mainFrame.setIconImage(titleIcon.getImage());

        JButton depositButton = createStyledButton("DepositButton.png", "Deposit");
        JButton withdrawButton = createStyledButton("WithdrawButton.png", "Withdraw");
        JButton balanceButton = createStyledButton("BalanceButton.png", "Check Balance");
        JButton exitButton = createStyledButton("ExitButton.png", "Exit");

        depositButton.addActionListener(e -> handleDeposit(bankApp));
        withdrawButton.addActionListener(e -> handleWithdraw(bankApp));
        balanceButton.addActionListener(e -> handleCheckBalance(bankApp));
        exitButton.addActionListener(e -> System.exit(0));

        mainFrame.add(depositButton);
        mainFrame.add(withdrawButton);
        mainFrame.add(balanceButton);
        mainFrame.add(exitButton);

        mainFrame.setVisible(true);
    }

    private static JButton createStyledButton(String iconPath, String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(120, 60));
        button.setBackground(new Color(85, 160, 255)); // Blue background color
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);

        // Set button icon
        ImageIcon buttonIcon = new ImageIcon(iconPath);
        Image scaledImage = buttonIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(scaledImage));

        return button;
    }

    private static void handleDeposit(BankApp bankApp) {
        String amountStr = JOptionPane.showInputDialog(null, "Enter the deposit amount:");
        try {
            double amount = Double.parseDouble(amountStr);
            bankApp.deposit(amount);
            showInfo("Deposit successful!");
        } catch (NumberFormatException e) {
            showError("Invalid amount.");
        }
    }

    private static void handleWithdraw(BankApp bankApp) {
        String amountStr = JOptionPane.showInputDialog(null, "Enter the withdrawal amount:");
        try {
            double amount = Double.parseDouble(amountStr);
            bankApp.withdraw(amount);
            
        } catch (NumberFormatException e) {
            showError("Invalid amount.");
        }
    }

    private static void handleCheckBalance(BankApp bankApp) {
        showInfo("Your current balance: GHs " + bankApp.getBalance());
    }
}
