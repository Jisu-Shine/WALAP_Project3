package org.example.controller;

import org.example.database.BookDQL;
import org.example.database.BookDML;
import org.example.model.BookModel;

import java.util.ArrayList;
import java.util.List;

public class BookController {
    private final BookDML dmlService;
    private final BookDQL dqlService;

    public BookController(BookDML dmlService, BookDQL dqlService) {
        this.dmlService = dmlService;
        this.dqlService = dqlService;
    }

    // 도서 추가
    public void addBook(String title, String author, String startDate, int totalPages) {
        BookModel newBook = new BookModel(title, author, startDate, totalPages);
        dmlService.insertBook(newBook);
        System.out.println("새로운 도서가 추가되었습니다.");
    }

    // 도서 목록 보기
    public List<BookModel> displayBooks() {
        return dqlService.getAllBooks();
    }

    // 도서 수정하기
    public void updateBook(String title, String title_m, String author, String startDate, int totalPages) {
        BookModel book = dqlService.getBookByTitle(title).stream().findFirst().orElse(null);

        if (book != null) {
            if (title_m != null && !title_m.isEmpty()) {
                book.setTitle(title_m);
            }
            if (author != null && !author.isEmpty()) {
                book.setAuthor(author);
            }
            if (startDate != null && !startDate.isEmpty()) {
                book.setStartDate(startDate);
            }
            if (totalPages != 0) {
                book.setTotalPages(totalPages);
            }
            dmlService.updateBook(book);
            System.out.println("도서가 성공적으로 업데이트되었습니다:)");
        } else {
            System.out.println("도서를 찾을 수 없습니다.");
        }
    }

    // 도서명으로 도서 찾기
    public BookModel findBookByTitle(String title) {
        return dqlService.getBookByTitle(title);
    }

    // 도서 책갈피
    public void saveBookPage(String title, String lastReadDate, int pagesRead) {
        BookModel book = dqlService.getBookByTitle(title).stream().findFirst().orElse(null);

        if (book != null) {
            if (lastReadDate != null && !lastReadDate.isEmpty()) {
                book.setLastReadDate(lastReadDate);
            }
            if (pagesRead != 0) {
                book.setPagesRead(pagesRead);
            }
            book.setProgress(pagesRead / (float) book.getTotalPages() * 100);
            dmlService.updateBook(book);
            System.out.println("책갈피를 설정하였습니다:-)");
        } else {
            System.out.println("도서를 찾을 수 없습니다.");
        }
    }

    // 도서 삭제하기
    public void deleteBook(String title) {
        BookModel book = dqlService.getBookByTitle(title).stream().findFirst().orElse(null);

        if (book != null) {
            dmlService.deleteBook(title);
            System.out.println("도서가 삭제되었습니다.");
        } else {
            System.out.println("도서를 찾을 수 없습니다.");
        }
    }

    // 도서 검색하기 (예시: 제목으로 도서 검색)
    public List<BookModel> searchBook(String searchString) {
        return dqlService.getBooksContainingTitle(searchString);
    }

    // 진행률로 정렬하기
    public List<BookModel> sortByProgress() {
        return dqlService.sortBooksByProgress();
    }
}
