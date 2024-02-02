package testWorkWithObjectJDBC;

import java.sql.*;

import static testWorkWithObjectJDBC.Employee.getAllEmployees;

public class App {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/java_university",
                    "root", "asiwyfka");
            Employee employee = new Employee(1, "Vasya", "Her", 5000);

            // Убираем этот комментарий, чтобы записать нового работника Employee.addEmployee(connection, employee);
            System.out.println(Employee.getEmployeeById(connection, 1));
            for (Employee x : getAllEmployees(connection)) {
                System.out.println(x);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
