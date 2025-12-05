package repository;

import database.Database;
import model.Loan;
import java.sql.*;
import java.util.ArrayList;

public class LoanRepository {

    public void add(Loan loan) {
        String query = "INSERT INTO loans(bookId, studentId, dateBorrowed) VALUES(?, ?, ?)";

        try (Connection connection = Database.connect();
             PreparedStatement preparedStatement = connection != null ? connection.prepareStatement(query) : null) {

            if (preparedStatement != null) {
                preparedStatement.setInt(1, loan.getBookId());
                preparedStatement.setInt(2, loan.getStudentId());
                preparedStatement.setString(3, loan.getDateBorrowed());
                preparedStatement.executeUpdate();
                System.out.println("[+] Ödünç verildi.");
            }

        } catch (SQLException e) {
            System.out.println("Ödünç Verme Hatası: " + e.getMessage());
        }
    }

    public ArrayList<Loan> getAll() {
        ArrayList<Loan> loanList = new ArrayList<>();
        String query = "SELECT * FROM loans";

        try (Connection connection = Database.connect();
             Statement statement = connection != null ? connection.createStatement() : null;
             ResultSet resultSet = statement != null ? statement.executeQuery(query) : null) {

            if (resultSet != null) {
                while (resultSet.next()) {
                    loanList.add(new Loan(
                            resultSet.getInt("id"),
                            resultSet.getInt("bookId"),
                            resultSet.getInt("studentId"),
                            resultSet.getString("dateBorrowed"),
                            resultSet.getString("dateReturned")
                    ));
                }
            }

        } catch (SQLException e) {
            System.out.println("Ödünç Listeleme Hatası: " + e.getMessage());
        }
        return loanList;
    }

    public void returnBook(int loanId, String dateReturned) {
        String query = "UPDATE loans SET dateReturned = ? WHERE id = ?";

        try (Connection connection = Database.connect();
             PreparedStatement preparedStatement = connection != null ? connection.prepareStatement(query) : null) {

            if (preparedStatement != null) {
                preparedStatement.setString(1, dateReturned);
                preparedStatement.setInt(2, loanId);
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("[+] Kitap teslim alındı (İşlem ID: " + loanId + ")");
                } else {
                    System.out.println("[!] Teslim işlemi için kayıt bulunamadı (ID: " + loanId + ")");
                }
            }

        } catch (SQLException e) {
            System.out.println("Teslim Alma Hatası: " + e.getMessage());
        }
    }
}