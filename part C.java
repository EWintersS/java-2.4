public class Student {
    private int studentID;
    private String name;
    private String department;
    private double marks;

    public Student(int studentID, String name, String department, double marks) {
        this.studentID = studentID;
        this.name = name;
        this.department = department;
        this.marks = marks;
    }

    public int getStudentID() { return studentID; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getMarks() { return marks; }
}
import java.sql.*;
import java.util.*;

public class StudentDAO {
    private Connection con;

    public StudentDAO() throws SQLException {
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/schooldb", "root", "your_password");
    }

    public void addStudent(Student s) throws SQLException {
        String query = "INSERT INTO Student VALUES (?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, s.getStudentID());
        ps.setString(2, s.getName());
        ps.setString(3, s.getDepartment());
        ps.setDouble(4, s.getMarks());
        ps.executeUpdate();
    }

    public List<Student> getAllStudents() throws SQLException {
        List<Student> list = new ArrayList<>();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM Student");
        while (rs.next()) {
            list.add(new Student(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4)));
        }
        return list;
    }

    public void updateStudentMarks(int id, double marks) throws SQLException {
        String query = "UPDATE Student SET Marks=? WHERE StudentID=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setDouble(1, marks);
        ps.setInt(2, id);
        ps.executeUpdate();
    }

    public void deleteStudent(int id) throws SQLException {
        String query = "DELETE FROM Student WHERE StudentID=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
    }
}
import java.util.*;

public class StudentApp {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            StudentDAO dao = new StudentDAO();
            int choice;

            do {
                System.out.println("\n=== Student Management ===");
                System.out.println("1. Add Student");
                System.out.println("2. View Students");
                System.out.println("3. Update Marks");
                System.out.println("4. Delete Student");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                choice = sc.nextInt();

                switch (choice) {
                    case 1 -> {
                        System.out.print("ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Name: ");
                        String name = sc.nextLine();
                        System.out.print("Department: ");
                        String dept = sc.nextLine();
                        System.out.print("Marks: ");
                        double marks = sc.nextDouble();
                        dao.addStudent(new Student(id, name, dept, marks));
                        System.out.println("Student added!");
                    }

                    case 2 -> {
                        List<Student> students = dao.getAllStudents();
                        for (Student s : students) {
                            System.out.println(s.getStudentID() + " | " +
                                               s.getName() + " | " +
                                               s.getDepartment() + " | " +
                                               s.getMarks());
                        }
                    }

                    case 3 -> {
                        System.out.print("Enter Student ID to update marks: ");
                        int id = sc.nextInt();
                        System.out.print("New Marks: ");
                        double marks = sc.nextDouble();
                        dao.updateStudentMarks(id, marks);
                        System.out.println("Updated successfully!");
                    }

                    case 4 -> {
                        System.out.print("Enter Student ID to delete: ");
                        int id = sc.nextInt();
                        dao.deleteStudent(id);
                        System.out.println("Deleted successfully!");
                    }

                    case 5 -> System.out.println("Exiting...");
                    default -> System.out.println("Invalid choice!");
                }
            } while (choice != 5);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
