import java.sql.*;
import java.util.Scanner;

public class StudentRegistration {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Collect student details
        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter DOB (YYYY-MM-DD): ");
        String dob = sc.nextLine();

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        System.out.print("Enter Phone: ");
        String phone = sc.nextLine();

        System.out.print("Enter Marks: ");
        float marks = sc.nextFloat();
        sc.nextLine(); // consume newline

        System.out.print("Enter Category (General/OBC/SC/ST): ");
        String category = sc.nextLine();

        // JDBC Connection Setup
        String url = "jdbc:mysql://localhost:3306/CollegeAdmissionDB";
        String user = "root";
        String pass = "Rits@0104";  // <-- change to your MySQL password

        try {
            Connection conn = DriverManager.getConnection(url, user, pass);

            String query = "INSERT INTO Students (name, dob, email, phone, marks, category, status) VALUES (?, ?, ?, ?, ?, ?, 'pending')";
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setString(1, name);
            pstmt.setDate(2, Date.valueOf(dob));
            pstmt.setString(3, email);
            pstmt.setString(4, phone);
            pstmt.setFloat(5, marks);
            pstmt.setString(6, category);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Student registered successfully!");
            }

            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }

        sc.close();
    }
}
