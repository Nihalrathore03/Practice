package BusReservation;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BookingMain {

    private Connection connection;
    private Scanner scanner;
    private static String url = "jdbc:mysql://localhost:3306/project";
    private static String user = "root";
    private static String password = "nihal6263616698";

    public BookingMain() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            scanner = new Scanner(System.in);
        } catch (SQLException | ClassNotFoundException e) {
            e.getMessage();
        }
    }

    // for starting the project
    public void start() {
        while (true) {
            System.out.println();
            System.out.println("---------------------------------------");
            System.out.println(" 1. Add the Bus");
            System.out.println(" 2. Add the Customer");
            System.out.println(" 3. Book the Ticket");
            System.out.println(" 4. Cancel the Ticket");
            System.out.println(" 5. Show All the Buses");
            System.out.println(" 6. Show All the Customer Details");

            System.out.println(" 7. Exit");

            System.out.println();
            System.out.println("---------------------------------------");
            System.out.println(" Enter the choice ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    AddBus();
                    break;
                case 2:
                    AddCustomer();
                    break;
                case 3:
                    BookTicket();
                    break;

                case 4:
                    CancelTicket();
                    break;
                case 5:
                    ShowBus();
                    break;
                case 6:
                    ShowCustomer();
                    break;
                case 7:
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice is Entered by the User ");

            }

        }
    }

    private void AddBus() {

        // 1

            System.out.println(" 1. Enter the Bus Name");
        String BusName = scanner.nextLine();
        System.out.println(" 2. Enter the Bus Route");
        String BusRoute = scanner.nextLine();
        System.out.println(" 3. Enter the Bus type AC / NonAc");
        String BusType = scanner.nextLine();
        System.out.println("4. Enter the number of Seats");
        int Seats = scanner.nextInt();

        try {

            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into bus (busname,busroute,bustype,seat) values(?,?,?,?)");
            preparedStatement.setString(1, BusName);
            preparedStatement.setString(2, BusRoute);
            preparedStatement.setString(3, BusType);
            preparedStatement.setInt(4, Seats);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println(row + "  are affected  and bus added succesfully");

            } else {

                System.out.println("error insertion");
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        // } else{
        //     System.out.println("wrong password Entered");
        // }

        
    }

    private void AddCustomer() {

        System.out.println(" 1. Enter the Customer Name");
        String CustomerName = scanner.nextLine();

        System.out.println(" 1. Enter the Customer Phone");
        String CustomerPhone = scanner.nextLine();

        try {

            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into customer (name,phone) values(?,?)");
            preparedStatement.setString(1, CustomerName);
            preparedStatement.setString(2, CustomerPhone);

            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println(row + "  are affected  and customer added succesfully");

            } else {

                System.out.println("error insertion");
            }
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    private void BookTicket() {

        System.out.println(" 1. Enter the Customer_ID");
        int Customer_ID = scanner.nextInt();
        System.out.println(" 2. Enter the Bus ID");
        int BusId = scanner.nextInt();

        try {

            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into booking (CustomerId,busid) values(?,?)");
            preparedStatement.setInt(1, Customer_ID);
            preparedStatement.setInt(2, BusId);

            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println(row + "  are affected  and Booked succesfully");

            } else {

                System.out.println("error insertion");
            }
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    private void CancelTicket() {

        System.out.println(" 1. Enter the Booking ID");
        int BookingId = scanner.nextInt();

        try {

            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from booking  where id = ? ");
            preparedStatement.setInt(1, BookingId);

            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println(row + "  are affected  and Cancelled succesfully");

            } else {

                System.out.println("error in Cancellation id not found !!!");
            }
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    private void ShowBus() {

        try {

            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from bus ");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                System.out.println("Id -  " + resultSet.getInt("id"));
                System.out.println("BusName -  " + resultSet.getString("busname"));
                System.out.println("BusRoute -  " + resultSet.getString("busroute"));
                System.out.println("Bustype -  " + resultSet.getString("bustype"));
                System.out.println("seats -  " + resultSet.getInt("seat"));
                System.out.println();
                System.out.println("---------------------------------------");
            }

        } catch (SQLException e) {
            e.getMessage();
        }

    }

    private void ShowCustomer() {

        try {

            PreparedStatement preparedStatement = connection.prepareStatement("select * from  customer");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println("id  -" + resultSet.getInt("id"));
                System.out.println("Name = " + resultSet.getString("name"));
                System.out.println("Phone Number = " + resultSet.getString("phone"));

            }

        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public static void main(String[] args) {
        BookingMain bookingMain = new BookingMain();
        bookingMain.start();
    }

}