package repository;

import database.Database;
import model.Student;
import java.sql.*;
import java.util.ArrayList;

public class StudentRepository {

    public void add(Student student) {
        String query = "INSERT INTO students(name, department) VALUES(?, ?)";

        try (Connection connection = Database.connect();
             PreparedStatement preparedStatement = connection != null ? connection.prepareStatement(query) : null) {

            if (preparedStatement != null) {
                preparedStatement.setString(1, student.getName());
                preparedStatement.setString(2, student.getDepartment());
                preparedStatement.executeUpdate();
                System.out.println("[+] Öğrenci eklendi: " + student.getName());
            }

        } catch (SQLException e) {
            System.out.println("Öğrenci Ekleme Hatası: " + e.getMessage());
        }
    }

    public ArrayList<Student> getAll() {
        ArrayList<Student> studentList = new ArrayList<>();
        String query = "SELECT * FROM students";

        try (Connection connection = Database.connect();
             Statement statement = connection != null ? connection.createStatement() : null;
             ResultSet resultSet = statement != null ? statement.executeQuery(query) : null) {

            if (resultSet != null) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String department = resultSet.getString("department");

                    studentList.add(new Student(id, name, department));
                }
            }

        } catch (SQLException e) {
            System.out.println("Öğrenci Listeleme Hatası: " + e.getMessage());
        }
        return studentList;
    }
}