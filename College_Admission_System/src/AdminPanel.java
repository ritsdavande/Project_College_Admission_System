import java.sql.*;
import java.util.Scanner;

public class AdminPanel {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String url = "jdbc:mysql://localhost:3306/CollegeAdmissionDB";
        String user = "root";
        String pass = "Rits@0104"; // <- your MySQL password

        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
            Statement stmt = conn.createStatement();

            // Fetch pending applications with all cutoff info
            String query = "SELECT a.application_id, s.student_id, s.name, s.category, " +
                           "c.course_name, s.marks, " +
                           "c.cutoff_general, c.cutoff_obc, c.cutoff_sc, c.cutoff_st " +
                           "FROM Applications a " +
                           "JOIN Students s ON a.student_id = s.student_id " +
                           "JOIN Courses c ON a.course_id = c.course_id " +
                           "WHERE s.status = 'pending'";

            ResultSet rs = stmt.executeQuery(query);

            System.out.println("Pending Applications:\n");
            System.out.printf("%-7s %-15s %-10s %-15s %-8s %-20s %-10s\n",
                    "App ID", "Student Name", "Category", "Branch", "Marks", "Respective Cutoff", "Chances");

            while (rs.next()) {
                int appId = rs.getInt("application_id");
                int studentId = rs.getInt("student_id");
                String name = rs.getString("name");
                String category = rs.getString("category").toLowerCase();
                String course = rs.getString("course_name");
                float marks = rs.getFloat("marks");

                float cutoff;

                switch (category) {
                    case "obc":
                        cutoff = rs.getFloat("cutoff_obc");
                        break;
                    case "sc":
                        cutoff = rs.getFloat("cutoff_sc");
                        break;
                    case "st":
                        cutoff = rs.getFloat("cutoff_st");
                        break;
                    default:
                        cutoff = rs.getFloat("cutoff_general");
                        break;
                }


                String chances = (marks >= cutoff) ? "High" : "Low";

                System.out.printf("%-7d %-15s %-10s %-15s %-8.2f %-20.2f %-10s\n",
                        appId, name, category.toUpperCase(), course, marks, cutoff, chances);
            }

            // Manual approval or rejection
            System.out.print("\nEnter Application ID to approve/reject: ");
            int appId = sc.nextInt();

            System.out.print("Approve (A) or Reject (R): ");
            char decision = sc.next().toUpperCase().charAt(0);

            String newStatus = (decision == 'A') ? "approved" : "rejected";

            String update = "UPDATE Students SET status = ? " +
                            "WHERE student_id = (SELECT student_id FROM Applications WHERE application_id = ?)";
            PreparedStatement pstmt = conn.prepareStatement(update);
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, appId);

            int updated = pstmt.executeUpdate();
            if (updated > 0) {
                System.out.println("Application " + appId + " has been " + newStatus + ".");
            } else {
                System.out.println("Failed to update status. Application ID may not exist.");
            }

            // Close resources
            rs.close();
            stmt.close();
            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        sc.close();
    }
}
