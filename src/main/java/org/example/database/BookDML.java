package org.example.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.example.model.BookModel;

public class BookDML {
    private Connection connection;

    public BookDML(Connection connection) {
        this.connection = connection;
    }

    public void insertBook(BookModel book) {
        String sql = "INSERT INTO Book (title, author, startDate, lastReadDate, pagesRead, totalPages, progress) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getStartDate());
            pstmt.setString(4, book.getLastReadDate());
            pstmt.setInt(5, book.getPagesRead());
            pstmt.setInt(6, book.getTotalPages());
            pstmt.setDouble(7, book.getProgress());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateBook(BookModel book) {
        String sql = "UPDATE Book SET title = ?, author = ?, startDate = ?, lastReadDate = ?, pagesRead = ?, totalPages = ?, progress = ? WHERE title = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getStartDate());
            pstmt.setString(4, book.getLastReadDate());
            pstmt.setInt(5, book.getPagesRead());
            pstmt.setInt(6, book.getTotalPages());
            pstmt.setDouble(7, book.getProgress());
            pstmt.setString(8, book.getTitle());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteBook(String title) {
        String sql = "DELETE FROM Book WHERE title = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<BookModel> getAllBooks() {
        List<BookModel> books = new ArrayList<>();
        String sql = "SELECT * FROM Book";

        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet resultSet = pstmt.executeQuery()) {
            while (resultSet.next()) {
                BookModel book = new BookModel(
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getString("startDate"),
                        resultSet.getInt("totalPages")
                );
                book.setLastReadDate(resultSet.getString("lastReadDate"));
                book.setPagesRead(resultSet.getInt("pagesRead"));
                book.setProgress(resultSet.getDouble("progress"));
                books.add(book);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return books;
    }
}
