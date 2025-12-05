package com.smartlibrary;

import java.sql.*;

public class DatabaseHelper {

    private static final String CONNECTION_URL = "jdbc:sqlite:smartlibrary.db";

    private static Connection getConnection() {
        try {
            return DriverManager.getConnection(CONNECTION_URL);
        } catch (SQLException e) {
            System.out.println("Bağlantı Hatası: " + e.getMessage());
            return null;
        }
    }

    public static void createTable() {
        String query = "CREATE TABLE IF NOT EXISTS books (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "author TEXT NOT NULL)";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            if (connection != null) {
                statement.execute(query);
            }

        } catch (SQLException e) {
            System.out.println("Tablo Hatası: " + e.getMessage());
        }
    }

    public static void addBook(String bookName, String authorName) {
        String query = "INSERT INTO books(name, author) VALUES(?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            if (connection != null) {
                preparedStatement.setString(1, bookName);
                preparedStatement.setString(2, authorName);
                preparedStatement.executeUpdate();
                System.out.println("[+] Eklendi: " + bookName);
            }

        } catch (SQLException e) {
            System.out.println("Ekleme İşlemi Başarısız: " + e.getMessage());
        }
    }

    public static void listBooks() {
        String query = "SELECT * FROM books";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (connection != null) {
                System.out.println("\n--- KÜTÜPHANE LİSTESİ ---");
                System.out.printf("%-5s %-25s %-20s%n", "ID", "KİTAP ADI", "YAZAR");
                System.out.println("----------------------------------------------------");

                while (resultSet.next()) {
                    System.out.printf("%-5d %-25s %-20s%n",
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("author"));
                }
                System.out.println("----------------------------------------------------\n");
            }

        } catch (SQLException e) {
            System.out.println("Listeleme Hatası: " + e.getMessage());
        }
    }

    public static void deleteBook(int bookId) {
        String query = "DELETE FROM books WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            if (connection != null) {
                preparedStatement.setInt(1, bookId);
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("[-] Silindi (ID: " + bookId + ")");
                } else {
                    System.out.println("[!] Silinecek kayıt bulunamadı (ID: " + bookId + ")");
                }
            }

        } catch (SQLException e) {
            System.out.println("Silme İşlemi Başarısız: " + e.getMessage());
        }
    }
}