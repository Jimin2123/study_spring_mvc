package com.springmvc.repository;

import com.springmvc.domain.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRowMapper implements RowMapper<Book> {

    // RowMapper 인터페이스를 구현하여 ResultSet을 Book 객체로 매핑하는 메서드
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        // ResultSet에서 각 열의 값을 가져와 Book 객체를 생성
        Book book = new Book();

        book.setBookId(rs.getString("b_bookId"));
        book.setName(rs.getString("b_name"));
        book.setUnitPrice(rs.getInt("b_unitPrice"));
        book.setAuthor(rs.getString("b_author"));
        book.setDescription(rs.getString("b_description"));
        book.setPublisher(rs.getString("b_publisher"));
        book.setCategory(rs.getString("b_category"));
        book.setUnitsInStock(rs.getLong("b_unitsInStock"));
        book.setReleaseDate(rs.getString("b_releaseDate"));
        book.setCondition(rs.getString("b_condition"));
        book.setFileName(rs.getString("b_fileName"));

        return book;
    }
}
