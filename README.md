# Banking Management System

A simple, console-based Banking Management System implemented in Java. This application handles user authentication and fundamental banking operations like managing accounts, deposits, withdrawals, and transfers.

---

## Project Structure

The project includes the following core source files:

*   **`BankingApp.java`**: The main entry point of the application containing the primary logic and user interface loop.
*   **`Accounts.java`**: Handles account-specific data and core financial transactions (e.g., checking balances, debiting, and crediting funds).
*   **`AccountManager.java`**: Manages broader operations across accounts, such as transferring money between different users.
*   **`User.java`**: Represents the customer profile, managing user registration and login credentials.

---

## Features

*   **User Registration & Login**: Handles user sessions and registration.
*   **Account Management**: Allows users to open and manage bank accounts.
*   **Financial Transactions**: Support for depositing and withdrawing funds.
*   **Fund Transfers**: Transfer money smoothly to other registered accounts.
*   **Balance Inquiry**: Real-time balance checking.

---

## Getting Started

### Prerequisites
*   Java Development Kit (JDK) 8 or higher.
*   An IDE (like IntelliJ IDEA) or a terminal text editor.

### Running the Application
1. Clone or download the source files into a directory.
2. Open your terminal in the project folder.
3. Compile the Java files:
   ```bash
   javac BankingApp.java Accounts.java AccountManager.java User.java
