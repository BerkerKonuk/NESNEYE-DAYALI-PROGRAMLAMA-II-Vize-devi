package repository;

import database.Database;
import model.Book;
import java.sql.*;
import java.util.ArrayList;

public class BookRepository {

    public void add(Book b) {
        try (Connection conn = Database.connect()) {
            String sql = "INSERT INTO books(title, author, year) VALUES(?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, b.getTitle());
            ps.setString(2, b.getAuthor());
            ps.setInt(3, b.getYear());
            ps.executeUpdate();
            System.out.println("Kitap eklendi.");
        } catch (Exception e) { System.out.println(e.getMessage()); }
    }

    public ArrayList<Book> getAll() {
        ArrayList<Book> list = new ArrayList<>();
        try (Connection conn = Database.connect(); Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM books");
            while (rs.next())
                list.add(new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"), rs.getInt("year")));
        } catch (Exception e) { System.out.println(e.getMessage()); }
        return list;
    }
}
