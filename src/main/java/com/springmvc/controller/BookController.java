package com.springmvc.controller;
import java.util.List;

import org.slf4j.Logger; // slf4j란 : 다양한 로깅 프레임워크에 대한 간단한 퍼사드 또는 추상화 역할
import org.slf4j.LoggerFactory; // SLF4J API를 사용하여 로깅을 수행하는 클래스
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.springmvc.domain.Book;
import com.springmvc.service.BookService;

@Controller
public class BookController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BookService bookService;

    @RequestMapping(value="/books", method=RequestMethod.GET)
    public String requestBookList(Model model) {
        this.log.debug("requestBookList");
        List<Book> list = bookService.getAllBookList();
        model.addAttribute("bookList", list);
        return "books";
    }
}