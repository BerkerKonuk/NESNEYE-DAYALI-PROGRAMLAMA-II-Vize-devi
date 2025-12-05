package database;

import java.sql.*;

public class Database {

    private static final String CONNECTION_URL = "jdbc:sqlite:smartlibrary.db";

    private static Connection getConnection() {
        try {
            return DriverManager.getConnection(CONNECTION_URL);
        } catch (SQLException e) {
            System.out.println("Bağlantı Hatası: " + e.getMessage());
            return null;
        }
    }

    public static void createTables() {
        String createBooks = "CREATE TABLE IF NOT EXISTS books (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, " +
                "author TEXT, " +
                "year INTEGER)";

        String createStudents = "CREATE TABLE IF NOT EXISTS students (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "department TEXT)";

        String createLoans = "CREATE TABLE IF NOT EXISTS loans (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "bookId INTEGER, " +
                "studentId INTEGER, " +
                "dateBorrowed TEXT, " +
                "dateReturned TEXT)";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            if (connection != null) {
                statement.execute(createBooks);
                statement.execute(createStudents);
                statement.execute(createLoans);
                System.out.println("[+] Tablolar başarıyla oluşturuldu ve sistem hazır.");
            }

        } catch (SQLException e) {
            System.out.println("Tablo Oluşturma Hatası: " + e.getMessage());
        }
    }
}