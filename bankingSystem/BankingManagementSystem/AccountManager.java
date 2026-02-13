package BankingManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountManager {
    private Connection connection;
    private Scanner scanner;

    AccountManager(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }

    public void debit_money(long account_number) throws SQLException{
        scanner.nextLine();
        System.out.println("Enter Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Enter Security Pin: ");
        String pin = scanner.nextLine();
        try {
            connection.setAutoCommit(false);
            if (account_number != 0){
                PreparedStatement preparedStatement = connection.prepareStatement("select * from accounts where account_number = ? and security_pin = ?");
                preparedStatement.setLong(1,account_number);
                preparedStatement.setString(2,pin);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()){
                    double curr_balance = resultSet.getDouble("balance");
                    if (curr_balance>=amount){
                        String query = "update accounts set balance = balance - ? where account_number = ? ";
                        PreparedStatement preparedStatement1 = connection.prepareStatement(query);
                        preparedStatement1.setDouble(1,amount);
                        preparedStatement1.setLong(2,account_number);
                        int rowsAffected = preparedStatement1.executeUpdate();
                        if (rowsAffected>0){
                            System.out.println("Rs. "+amount+" Debited Successfully");
                            connection.commit();
                            connection.setAutoCommit(true);
                            return;
                        }else {
                            System.out.println("Transaction Failed");
                            connection.rollback();
                            connection.setAutoCommit(true);
                        }
                    }else {
                        System.out.println("Insufficient Balance!");
                    }
                }else {
                    System.out.println("Invalid Pin!");
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        connection.setAutoCommit(true);

    }

    public void credit_money(Long account_number) throws SQLException{
        scanner.nextLine();
        System.out.print("Enter Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter Security Pin: ");
        String security_pin = scanner.nextLine();

        try {
            connection.setAutoCommit(false);
            if (account_number != 0){
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Accounts WHERE account_number = ? and security_pin = ? ");
                preparedStatement.setLong(1, account_number);
                preparedStatement.setString(2, security_pin);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()){
                    String credit_query = "UPDATE Accounts SET balance = balance + ? WHERE account_number = ?";
                    PreparedStatement preparedStatement1 = connection.prepareStatement(credit_query);
                    preparedStatement1.setDouble(1, amount);
                    preparedStatement1.setLong(2, account_number);
                    int rowsAffected = preparedStatement1.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Rs."+amount+" credited Successfully");
                        connection.commit();
                        connection.setAutoCommit(true);
                        return;
                    } else {
                        System.out.println("Transaction Failed!");
                        connection.rollback();
                        connection.setAutoCommit(true);
                    }
                }else {
                    System.out.println("Invalid Pin");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        connection.setAutoCommit(true);
    }

    public void transfer_money(Long sender_account_number) throws SQLException{
        scanner.nextLine();
        System.out.print("Enter Receiver Account Number: ");
        long receiver_account_number = scanner.nextLong();
        System.out.print("Enter Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter Security Pin: ");
        String security_pin = scanner.nextLine();
        try {
            connection.setAutoCommit(false);
            if (sender_account_number != 0 && receiver_account_number != 0){
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Accounts WHERE account_number = ? AND security_pin = ? ");
                preparedStatement.setLong(1, sender_account_number);
                preparedStatement.setString(2, security_pin);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    double curr_balance = resultSet.getDouble("balance");
                    if (curr_balance>=amount){
                        String query1 = "update accounts set balance = balance+? where account_number = ?";
                        String query2 = "update accounts set balance = balance-? where account_number = ?";
                        PreparedStatement creditPreparedStatement = connection.prepareStatement(query1);
                        PreparedStatement debitPreparedStatement = connection.prepareStatement(query2);

                        creditPreparedStatement.setDouble(1, amount);
                        creditPreparedStatement.setLong(2, receiver_account_number);
                        debitPreparedStatement.setDouble(1, amount);
                        debitPreparedStatement.setLong(2, sender_account_number);
                        int rowsAffected1 = debitPreparedStatement.executeUpdate();
                        int rowsAffected2 = creditPreparedStatement.executeUpdate();
                        if (rowsAffected1 > 0 && rowsAffected2 > 0) {
                            System.out.println("Transaction Successful!");
                            System.out.println("Rs."+amount+" Transferred Successfully");
                            connection.commit();
                            connection.setAutoCommit(true);
                            return;
                        } else {
                            System.out.println("Transaction Failed");
                            connection.rollback();
                            connection.setAutoCommit(true);
                        }
                    }else {
                        System.out.println("Insufficient Balance");
                    }
                }else {
                    System.out.println("Invalid Pin Number!!!");
                }
            }else {
                System.out.println("Invalid Account Number!");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        connection.setAutoCommit(true);
    }

    public void getBalance(Long account_number){
        scanner.nextLine();
        System.out.print("Enter Security Pin: ");
        String security_pin = scanner.nextLine();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT balance FROM Accounts WHERE account_number = ? AND security_pin = ?");
            preparedStatement.setLong(1, account_number);
            preparedStatement.setString(2, security_pin);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                double balance = resultSet.getDouble("balance");
                System.out.println("Balance: "+balance);
            }else{
                System.out.println("Invalid Pin!");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
