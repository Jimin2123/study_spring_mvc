package com.springmvc.repository;

import com.springmvc.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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
        String sql = "select b_bookId, b_name, b_unitPrice, b_author, b_description, b_publisher, b_category, b_unitsInStock, b_releaseDate, b_condition from book";
        List<Book> listOfBook = this.template.query(sql, new BookRowMapper());
        return listOfBook;
    }

    @Override
    public List<Book> getBookListByCategory(String category) {
        String sql = "select b_bookId, b_name, b_unitPrice, b_author, b_description, " +
                "b_publisher, b_category, b_unitsInStock, b_releaseDate, b_condition from book"
                + " where b_category like concat('%', ?, '%')";
        List<Book> listOfBooks = this.template.query(sql, new BookRowMapper(), category);
        return listOfBooks;
    }
}