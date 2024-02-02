package avdeyev.vik;

import testWorkWithObjectJDBC.Employee;

import java.sql.*;


public class App {
    public static void main(String[] args) {
        try {

            /*В JDBC есть 3 основных интерфейса:
            Connection – отвечает за соединение с базой данных
            Statement – отвечает за запрос к базе данных
            ResultSet – отвечает за результат запроса к базе данных
            */

            /* mysql – это протокол работы с сервером
            localhost – имя хоста в сети
            3306 – порт, по которому идут запросы
            db_scheme – имя схемы (имя базы данных)
             */

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/java_university",
                    "root", "asiwyfka");
            Statement statement = connection.createStatement();

            //Метод executeQuery() выполняет запрос к базе данных и возвращает объект типа ResultSet.

            ResultSet results = statement.executeQuery("SELECT * FROM user");

            /* Ниже идет проверка работы методов для класса ResultSet. Объект класса ResultSet - это результат состоящий
             из большого количества строк, который нам нужно прочитать. Результаты как бы обрамлены пустыми строками с обоих сторон.
             Поэтому изначально текущая строка находится перед первой строкой результата.
             И чтобы получить первую строку, нужно хотя бы раз вызвать метод next().
             Если ты на последней стоке вызвал метод next(), то ты перешел на строку после последней.
             Данных из нее прочитать ты не можешь, но никакой ошибки не произойдет. Тут метод isAfterLast() будет возвещать true в качестве результата.
            */

            System.out.println(results.getRow());            // 0
            System.out.println(results.isBeforeFirst());  // true
            System.out.println(results.isFirst());            // false

            results.next();

            System.out.println(results.getRow());            // 1
            System.out.println(results.isBeforeFirst());  // false
            System.out.println(results.isFirst());            // true

            results.next();

            System.out.println(results.getRow());            // 2
            System.out.println(results.isBeforeFirst());  // false
            System.out.println(results.isFirst());

            // Создаем вывод результата на экран. Метод интерфейса Statement — executeQuery() служит для получения данных.
            // Для изменения данных — executeUpdate() и он выводит количество измененных строк..
            ResultSet results2 = statement.executeQuery("SELECT * FROM user");

            while (results2.next()) {
                System.out.println();
                Integer id = results2.getInt(1);
                String name = results2.getString(2);
                Date date = results2.getDate(4);
                System.out.println(results2.getRow() + ". " + id + "\t" + name + "\t" + date);
            }

            //Выводим информацию читаемой колонки по её номеру с помощью интерфейса ResultSetMetaData и его методов.
            ResultSet results3 = statement.executeQuery("SELECT * FROM user");
            ResultSetMetaData metaData = results3.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int column = 1; column <= columnCount; column++) {
                String name = metaData.getColumnName(column);
                String className = metaData.getColumnClassName(column);
                String typeName = metaData.getColumnTypeName(column);
                int type = metaData.getColumnType(column);

                System.out.println(name + "\t" + className + "\t" + typeName + "\t" + type);
            }


            // Запрос с указанием мест для параметров в виде знака "?" Параметризированный оператор.
            String sql = "INSERT INTO user (id, name, level, date) VALUES (?, ?, ?, ?)";
            // Создание запроса. Переменная connection — это объект типа Connection
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Установка параметров
            preparedStatement.setInt(1, 11);
            preparedStatement.setString(2, "Alex");
            preparedStatement.setInt(3, 50);

            // Получение текущей даты в Java
            java.util.Date currentDate = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
            preparedStatement.setDate(4, sqlDate);

            // Выполнение запроса
            preparedStatement.executeUpdate();

            ResultSet results4 = statement.executeQuery("SELECT * FROM user");
            while (results4.next()) {
                System.out.println();
                Integer id = results4.getInt(1);
                String name = results4.getString(2);
                Date date = results4.getDate(4);
                System.out.println(results4.getRow() + ". " + id + "\t" + name + "\t" + date);
            }


        } catch (SQLException e) {
            System.out.println("Возникла SQL ошибка");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Возникла ошибка");
            e.printStackTrace();
        }
    }
}
