package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private static final String URL = "jdbc:sqlite:smartlibrary.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
            if (conn != null) {
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute("PRAGMA foreign_keys = ON;");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void initialize() {
        String sqlBooks = "CREATE TABLE IF NOT EXISTS books (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " title TEXT NOT NULL,\n"
                + " author TEXT NOT NULL,\n"
                + " year INTEGER\n"
                + ");";

        String sqlStudents = "CREATE TABLE IF NOT EXISTS students (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " name TEXT NOT NULL,\n"
                + " department TEXT\n"
                + ");";

        String sqlLoans = "CREATE TABLE IF NOT EXISTS loans (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " bookId INTEGER,\n"
                + " studentId INTEGER,\n"
                + " dateBorrowed TEXT,\n"
                + " dateReturned TEXT,\n"
                + " FOREIGN KEY (bookId) REFERENCES books (id),\n"
                + " FOREIGN KEY (studentId) REFERENCES students (id)\n"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sqlBooks);
            stmt.execute(sqlStudents);
            stmt.execute(sqlLoans);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
