package database;

import java.sql.*;

public class Database {

    private static final String CONNECTION_URL = "jdbc:sqlite:smartlibrary.db";

    public static Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(CONNECTION_URL);
            // Her işlemde log basmaması için bu satırı opsiyonel tutabilirsin,
            // ama istediğin kodda olduğu için ekledim:
            System.out.println("[+] SQL bağlantısı başarılı.");
        } catch (SQLException e) {
            System.out.println("SQL Bağlantı Hatası: " + e.getMessage());
        }
        return connection;
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

        try (Connection connection = connect();
             Statement statement = connection != null ? connection.createStatement() : null) {

            if (statement != null) {
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