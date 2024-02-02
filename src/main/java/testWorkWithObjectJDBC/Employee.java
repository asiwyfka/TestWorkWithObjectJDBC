package testWorkWithObjectJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Employee {
    public Integer id;
    public String name;
    public String occupation;
    public Integer salary;
    public Date joinDate;

    public Employee() {
    }

    public Employee(Integer id, String name, String occupation, Integer salary) {
        this.id = id;
        this.name = name;
        this.occupation = occupation;
        this.salary = salary;
        java.util.Date currentDate = new java.util.Date();
        this.joinDate = new java.sql.Date(currentDate.getTime());
    }

    public static boolean addEmployee(Connection connection, Employee employee) throws Exception {
        // Создаем и подготавливаем запрос на вставку данных в таблицу
        String insertQuery = "INSERT INTO employee(name, occupation, salary, join_date ) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(insertQuery);

        // Заполняем запрос данными из объекта Employee
        statement.setString(1, employee.name);
        statement.setString(2, employee.occupation);
        statement.setInt(3, employee.salary);
        statement.setDate(4, (java.sql.Date) employee.joinDate);

        // Выполняем наш запрос, и он возвращает true, если новая строка добавилась
        int count = statement.executeUpdate();
        return count > 0;
    }


    public static Employee getEmployeeById(Connection connection, int id) throws Exception {
        // Создаем и подготавливаем запрос для получения сотрудника из таблицы
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM employee WHERE id = ?",ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, id);

        // Выполняем наш запрос, и он возвращает null, если строк в результате запроса нет
        ResultSet results = statement.executeQuery();


        if (!results.first())
            return null;

        // Заполняем объект Employee данными из ResultSet
        Employee employee = new Employee();
        employee.id = results.getInt(1);
        employee.name = results.getString(2);
        employee.occupation = results.getString(3);
        employee.salary = results.getInt(4);
        employee.joinDate = results.getDate(5);
        return employee;
    }

    public static List<Employee> getAllEmployees(Connection connection) throws Exception {
        // Создаем и выполняем запрос для получения сотрудников из таблицы
        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery("SELECT * FROM employee");

        ArrayList<Employee> list = new ArrayList<Employee>();
        while (results.next()) {
            // Заполняем объект Employee данными из текущей строки ResultSet
            Employee employee = new Employee();
            employee.id = results.getInt(1);
            employee.name = results.getString(2);
            employee.occupation = results.getString(3);
            employee.salary = results.getInt(4);
            employee.joinDate = results.getDate(5);

            list.add(employee);
        }
        return list;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", occupation='" + occupation + '\'' +
                ", salary=" + salary +
                ", joinDate=" + joinDate +
                '}';
    }
}

