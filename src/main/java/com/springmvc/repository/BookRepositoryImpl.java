package com.springmvc.repository;

import com.springmvc.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class BookRepositoryImpl implements BookRepository {
    private JdbcTemplate template;
    public BookRepositoryImpl() {
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Book> getAllBookList() {
        String sql = "select b_bookId, b_name, b_unitPrice, b_author, b_description, b_publisher, b_category, b_unitsInStock, b_releaseDate, b_condition, b_fileName from book";
        List<Book> listOfBook = this.template.query(sql, new BookRowMapper());
        return listOfBook;
    }

    @Override
    public List<Book> getBookListByCategory(String category) {
        String sql = "select b_bookId, b_name, b_unitPrice, b_author, b_description, " +
                "b_publisher, b_category, b_unitsInStock, b_releaseDate, b_condition, b_fileName from book"
                + " where b_category like concat('%', ?, '%')";
        List<Book> listOfBooks = this.template.query(sql, new BookRowMapper(), category);
        return listOfBooks;
    }

    @Override
    public Set<Book> getBookListByFilter(Map<String, List<String>> filter) {
        // 출판사 기준으로 조회된 도서들을 담을 Set
        Set<Book> booksByPublisher = new HashSet<Book>();
        // 카테고리 기준으로 조회된 도서들을 담을 Set
        Set<Book> booksByCategory = new HashSet<Book>();
        // 필터의 key 값들 (예: "publisher", "category")
        Set<String> booksByFilter = filter.keySet();

        // 출판사 필터가 존재하는 경우
        if (booksByFilter.contains("publisher")) {
            for (int j = 0; j < filter.get("publisher").size(); j++) {
                String publisherName = filter.get("publisher").get(j);
                // 출판사명이 포함된 도서들을 조회
                String SQL = "SELECT b_bookId, b_name, b_unitPrice, b_author, b_description, " +
                        "b_publisher, b_category, b_unitsInStock, b_releaseDate, b_condition, b_fileName FROM book "
                        + "where b_publisher LIKE '%' || ? || '%'";
                booksByPublisher.addAll(this.template.query(SQL, new BookRowMapper(), publisherName));
            }
        }

        // 카테고리 필터가 존재하는 경우
        if (booksByFilter.contains("category")) {
            for (int i = 0; i < filter.get("category").size(); i++) {
                String categoryName = filter.get("category").get(i);
                // 카테고리명이 포함된 도서들을 조회
                String sql = "select b_bookId, b_name, b_unitPrice, b_author, b_description, " +
                        "b_publisher, b_category, b_unitsInStock, b_releaseDate, b_condition, b_fileName from book "
                        + "where b_category like concat('%', ?, '%')";
                booksByCategory.addAll(template.query(sql, new BookRowMapper(), categoryName));
            }
        }

        // 출판사와 카테고리 필터가 모두 있는 경우, 공통된 도서만 남김 (AND 조건 효과)
        booksByCategory.retainAll(booksByPublisher);

        return booksByCategory;
    }

    @Override
    public Book getBookById(String bookId) {
        Book bookInfo = null;
        String sql = "SELECT count(*) FROM book where b_bookId=?";
        int rowCount = this.template.queryForObject(sql, Integer.class, bookId);
        if(rowCount != 0) {
            sql = "SELECT b_bookId, b_name, b_unitPrice, b_author, b_description, b_publisher," +
                    "b_category, b_unitsInStock, b_releaseDate, b_condition, b_fileName FROM book where b_bookId=?";
            bookInfo = this.template.queryForObject(sql, new BookRowMapper(), bookId);
        }
        if(bookInfo == null)
            throw new IllegalArgumentException("도서 ID가 " + bookId + "인 도서를 찾을 수 없습니다.");
        return bookInfo;
    }

    @Override
    public void setNewBook(Book book) {
        String sql = "INSERT INTO book (b_bookId, b_name, b_unitPrice, b_author, "
                + " b_description, b_publisher, b_category, b_unitsInStock, "
                + " b_releaseDate, b_condition, b_fileName) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

        this.template.update(sql, book.getBookId(), book.getName(), book.getUnitPrice(),
                book.getAuthor(), book.getDescription(), book.getPublisher(),
                book.getCategory(), book.getUnitsInStock(), book.getReleaseDate(),
                book.getCondition(), book.getFileName());
    }
}