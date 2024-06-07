import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Statement;
import java.sql.ResultSet;

public class HotelReservationSystem {
    private static final String url="jdbc:mysql://localhost:3306/hotel_db";
    private static final String username="root";
    private static final String password="********";

    public static void main(String[] args) throws ClassNotFoundException,SQLException{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        try{
            Connection connection=DriverManager.getConnection(url,username,password);
            while (true){
                System.out.println();
                System.out.println("HOTEL MANAGEMENT SYSTEM");
                Scanner scanner=new Scanner(System.in);
                System.out.println("1. Reserve a room");
                System.out.println("2. View Reservation");
                System.out.println("3. Get Room Number");
                System.out.println("4. Update Reservation");
                System.out.println("5. Delete Reservation");
                System.out.println("0 Exit");
                System.out.println("Choose an option");
                int choice=scanner.nextInt();
                switch (choice){
                    case 1:
                        reserveRoom(connection,scanner);
                        break;
                    case 2:
                        viewReservations(connection);
                        break;
                    case 3:
                        getRoomNumber(connection,scanner);
                        break;
                    case 4:
                        updateReservation(connection,scanner);
                        break;
                    case 5:
                        deleteReservation(connection,scanner);
                        break;
                    case 0:
                        exit();
                        scanner.close();
                }
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        catch (InterruptedException e){
            throw new RuntimeException(e);
        }

    }
    private static void reserveRoom(Connection connection,Scanner scanner){
        try{
            System.out.println("Enter guest name: ");
            String guestName=scanner.next();
            scanner.nextLine();
            System.out.println("Enter Room Number: ");
            int roomNumber=scanner.nextInt();
            System.out.println("Enter Contact Number: ");
            String contactNumber=scanner.next();
            String sql="INSERT INTO reservations(guest_name,room_number,contact_number)"+
                    "VALUES('"+ guestName +"',"+roomNumber+",'"+contactNumber+"')";
            try(Statement statement=connection.createStatement()){
                int affectedRows=statement.executeUpdate(sql);
                if (affectedRows>0){
                    System.out.println("Reservation Successful!");
                }
                else {
                    System.out.println("Reservation Failed.");
                }
                }
            }
            catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
        private static void viewReservations(Connection connection)throws SQLException{
        String sql="SELECT reservation_id,guest_name,room_number,contact_number,reservation_date FROM reservations";
        try(Statement statement=connection.createStatement();
        ResultSet resultSet=statement.executeQuery(sql)){
            while (resultSet.next()){
                int reservationId=resultSet.getInt("reservation_id");
                String guestName=resultSet.getString("guest_name");
                int roomNumber=resultSet.getInt("room_number");
                String contactNumber=resultSet.getString("contact_number");
                String reservationDate=resultSet.getTimestamp("reservation_date").toString();

                System.out.printf(
                        "| %-14d | %-15s | %-13d | %-20s | %-19s  |\n",
                        reservationId, guestName, roomNumber, contactNumber, reservationDate);
            }
            System.out.println("----------------------------------------------------");
        }
    }
    private static void getRoomNumber(Connection connection,Scanner sc){
        try{
            System.out.println("Enter Reservation ID:");
            int reservationId=sc.nextInt();
            System.out.println("Enter guest name");
            String guestName=sc.next();
            String sql="SELECT room_number FROM reservations"+
                    "WHERE reservation_id = "+reservationId +"AND guest_name = "+guestName+"'";
            try(Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(sql)){
                if (resultSet.next()){
                    int roomNumber=resultSet.getInt("room_number");
                    System.out.println("Room number for Reservation ID"+reservationId+"and Guest"+guestName+"is:"+roomNumber);
                }
                else {
                    System.out.println("NOT FOUND !!");
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    private static void updateReservation(Connection connection,Scanner scanner){
        try {
            System.out.println("Enter reservation ID to update:");
            int reservationId=scanner.nextInt();
            scanner.nextLine();
            if (!reservationExists(connection,reservationId)){
                System.out.println("Reservation Not found");
                return;
            }
            System.out.println("Enter new guest name: ");
            String newGuestname=scanner.nextLine();
            System.out.println("Enter new room number: ");
            int newRoomnumber=scanner.nextInt();
            System.out.println("Enter new contact number: ");
            String newContact=scanner.next();
            String sql="UPDATE reservations SET guest_name= '"+ newGuestname+"',"+
                    "room_number="+newRoomnumber+","+
                    "contact_number= '"+newContact+"'"+
                    "WHERE reservation_id="+reservationId;
            try(Statement statement=connection.createStatement()){
                int affectedRows=statement.executeUpdate(sql);
                if (affectedRows>0){
                    System.out.println("Updated Successfully");
            }
                else {
                    System.out.println("Reservation Update failed");
                }
        }
        }
        catch (SQLException e) {
        e.printStackTrace();
        }
        }
        private static void deleteReservation(Connection connection,Scanner scanner) {
            try {
                System.out.println("Enter reservation ID to delete:");
                int reservationId=scanner.nextInt();

                if (!reservationExists(connection,reservationId)){
                    System.out.println("Reservation Not found");
                    return;
                }

                String sql="DELETE FROM reservations WHERE reservation_id= "+reservationId;
                try(Statement statement=connection.createStatement()){
                    int affectedRows=statement.executeUpdate(sql);
                    if (affectedRows>0){
                        System.out.println("Updated Successfully");
                    }
                    else {
                        System.out.println("Reservation Update failed");
                    }
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
    }
    }
    private static boolean reservationExists(Connection connection,int reservationId){
        try{
            String sql="SELECT reservation_id FROM reservations WHERE reservation_id = "+reservationId;
            try(Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(sql)){
                return resultSet.next();
            }

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public static void exit() throws InterruptedException{
        System.out.println("Exiting System");
        int i=5;
        while (i!=0){
            System.out.print(".");
            Thread.sleep(450);
            i--;
        }
        System.out.println();
        System.out.println("Thanks For using Hotel management System");
    }
}
