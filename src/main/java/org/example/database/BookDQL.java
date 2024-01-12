package org.example.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.example.model.BookModel;

public class BookDQL {
    private Connection connection;

    public BookDQL(Connection connection) {
        this.connection = connection;
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

    public List<BookModel> sortBooksByProgress() {
        List<BookModel> books = new ArrayList<>();
        String sql = "SELECT * FROM Book ORDER BY progress";

        try (Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)) {

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

    public List<BookModel> getBookByTitle(String title) {
        List<BookModel> books = new ArrayList<>();
        String sql = "SELECT * FROM Book WHERE title = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, title);
            ResultSet resultSet = pstmt.executeQuery();

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

    public List<BookModel> getBooksContainingTitle(String titleSubstring) {
        List<BookModel> books = new ArrayList<>();
        String sql = "SELECT * FROM Book WHERE title LIKE ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "%" + titleSubstring + "%");
            ResultSet resultSet = pstmt.executeQuery();

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
