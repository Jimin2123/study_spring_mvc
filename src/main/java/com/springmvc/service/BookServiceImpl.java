package com.springmvc.service;

import com.springmvc.domain.Book;
import com.springmvc.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired // 자동으로 디팬던시 인젝션을 처리하도록 하는 역할?
    private BookRepository bookRepository;

    @Override
    public List<Book> getAllBookList() {
        return this.bookRepository.getAllBookList();
    }
}