package services;

import db.DBConnection;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InformationProcessor {
    static Scanner sc = new Scanner(System.in);

    public static void addStore() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            System.out.println("Enter Store Information");

            System.out.print("Store Address: ");
            sc.nextLine(); // consume leftover newline
            String storeAddr = sc.nextLine();

            System.out.print("Store Phone: ");
            String storePhone = sc.next();

            String query = "INSERT INTO Store(storeAddress, phoneNumber) VALUES ('" + storeAddr + "', '" + storePhone + "')";
            stmt.executeUpdate(query);

            System.out.println("Store added successfully.");

        } catch (Throwable err) {
            err.printStackTrace();
        }
    }

    public static void addCustomer() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            System.out.println("Enter Customer Information");

            sc.nextLine();
            System.out.print("First Name: ");
            String firstName = sc.nextLine();

            System.out.print("Last Name: ");
            String lastName = sc.nextLine();

            System.out.print("Phone Number: ");
            String phoneNumber = sc.nextLine();

            System.out.print("Address: ");
            String address = sc.nextLine();

            System.out.print("Email: ");
            String email = sc.next();

            System.out.print("Membership Level (Gold/Platinum): ");
            String membershipLevel = sc.next();

            System.out.print("Membership Status (Active/Inactive): ");
            String membershipStatus = sc.next();

            System.out.print("Staff ID who signed up the customer: ");
            int staffId = sc.nextInt();

            System.out.print("Store ID where customer signed up: ");
            int storeId = sc.nextInt();

            LocalDate today = LocalDate.now();
            LocalDate endDate = today.plusDays(365);
            String startDate = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String endDateStr = endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            String insertCustomer = String.format(
                "INSERT INTO Customer (firstName, lastName, phone, homeAddress, Email, membershipLevel, memberShipStartDate, ActiveTillDate, membershipStatus) " +
                "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                firstName, lastName, phoneNumber, address, email, membershipLevel, startDate, endDateStr, membershipStatus
            );

            stmt.executeUpdate(insertCustomer);

            ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
            int customerId = -1;
            if (rs.next()) {
                customerId = rs.getInt(1);
            }

            if (customerId == -1) {
                System.out.println("Failed to retrieve new customer ID.");
                return;
            }

            String insertSignup = String.format(
                "INSERT INTO SignUpInformation (staffId, customerId, signUpDate, storeId) " +
                "VALUES (%d, %d, CURDATE(), %d)",
                staffId, customerId, storeId
            );

            stmt.executeUpdate(insertSignup);
            System.out.println("Customer signed-up and added successfully.");

        } catch (Throwable err) {
            err.printStackTrace();
        }
    }

    // Issue 2:  methods like updateStore, deleteStore, updateCustomer, etc. similarly... are pending
}
