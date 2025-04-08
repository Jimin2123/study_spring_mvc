package com.springmvc.repository;
import java.util.List;
import com.springmvc.domain.Book;

// repsository = 통신 규약에 대한 설정
public interface BookRepository {
    List<Book> getAllBookList();
    List<Book> getBookListByCategory(String category);
}