import java.sql.*;

public class FetchEmployeeData {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/companydb";
        String user = "root";
        String password = "your_password";

        try {
            // 1. Load the MySQL JDBC driver (optional in new versions)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. Establish connection
            Connection con = DriverManager.getConnection(url, user, password);

            // 3. Create statement
            Statement stmt = con.createStatement();

            // 4. Execute SQL query
            ResultSet rs = stmt.executeQuery("SELECT * FROM Employee");

            // 5. Display results
            System.out.println("EmpID\tName\t\tSalary");
            System.out.println("----------------------------------");
            while (rs.next()) {
                System.out.println(rs.getInt("EmpID") + "\t" +
                                   rs.getString("Name") + "\t\t" +
                                   rs.getDouble("Salary"));
            }

            // 6. Close connections
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
