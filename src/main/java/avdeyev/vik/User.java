package avdeyev.vik;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
    public int id;
    public String name;
    public int level;
    public Date date;

    public User(int id, String name, int level) {
        this.id = id;
        this.name = name;
        this.level = level;
        java.util.Date currentDate = new java.util.Date();
        this.date = new java.sql.Date(currentDate.getTime());
    }

    public User() {

    }

    //Метод добавления юзера в базу данных
    public static boolean addUser(Connection connection, User user) throws Exception {

        // Создаем и подготавливаем запрос на вставку данных в таблицу
        String insertQuery = "INSERT INTO user (id, name, level, date) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(insertQuery);

        // Заполняем запрос данными из объекта Employee
        statement.setInt(1, user.id);
        statement.setString(2, user.name);
        statement.setInt(3, user.level);
        statement.setDate(4, (java.sql.Date) user.date);

        // Выполняем наш запрос, и он возвращает true, если новая строка добавилась
        int count = statement.executeUpdate();
        return count > 0;
    }

    //Получение юзера по id
    public static User getUserById(Connection connection, int id) throws Exception {
        // Создаем и подготавливаем запрос для получения сотрудника из таблицы
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE id = ?");
        statement.setInt(1, id);

        // Выполняем наш запрос, и он возвращает null, если строк в результате запроса нет
        ResultSet results = statement.executeQuery();
        if (!results.first())
            return null;

        // Заполняем объект User данными из ResultSet
        User user = new User();
        user.id = results.getInt(1);
        user.name = results.getString(2);
        user.level = results.getInt(3);
        user.date = results.getDate(4);
        return user;
    }

    //Получение списка всех юзеров
    public static List<User> getAllUsers(Connection connection) throws Exception {
        // Создаем и выполняем запрос для получения сотрудников из таблицы
        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery("SELECT * FROM user");

        ArrayList<User> list = new ArrayList<User>();
        while (results.next()) {
            // Заполняем объект Employee данными из текущей строки ResultSet
            User user = new User();
            user.id = results.getInt(1);
            user.name = results.getString(2);
            user.level = results.getInt(3);
            user.date = results.getDate(4);
            list.add(user);
        }
        return list;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", date=" + date +
                '}';
    }
}

