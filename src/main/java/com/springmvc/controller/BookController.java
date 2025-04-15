package com.springmvc.controller;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger; // slf4j란 : 다양한 로깅 프레임워크에 대한 간단한 퍼사드 또는 추상화 역할
import org.slf4j.LoggerFactory; // SLF4J API를 사용하여 로깅을 수행하는 클래스
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.springmvc.domain.Book;
import com.springmvc.service.BookService;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/books")
public class BookController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BookService bookService;

    @RequestMapping
    public String requestBookList(Model model) {
        this.log.debug("requestBookList");

        List<Book> list = bookService.getAllBookList();
        model.addAttribute("bookList", list);

        return "books";
    }

    @RequestMapping("all")
    public ModelAndView requestAllBook(Model model) {
        this.log.debug("requestAllBook");

        ModelAndView mav = new ModelAndView();

        List<Book> list = this.bookService.getAllBookList();
        mav.addObject("bookList", list);
        mav.setViewName("books");

        return mav;
    }

    @RequestMapping("{category}")
    public ModelAndView requestBookListByCategory(Model model, @PathVariable("category") String category) {
        this.log.debug("requestBookListByCategory");

        ModelAndView mav = new ModelAndView();

        List<Book> list = this.bookService.getBookListByCategory(category);
        mav.addObject("bookList", list);
        mav.setViewName("books");

        return mav;
    }

    @RequestMapping("/filter/{bookFilter}")
    public String requestBooksByFilter(
            @MatrixVariable(pathVar="bookFilter") Map<String, List<String>> bookFilter,
            Model model
    ) {
        Set<Book> booksByFilter = this.bookService.getBookListByFilter(bookFilter);
        model.addAttribute("bookList", booksByFilter);
        return "books";
    }

    @GetMapping("/book")
    public String requestBookById(@RequestParam("id") String bookId, Model model) {
        Book bookById = this.bookService.getBookById(bookId);
        model.addAttribute("book", bookById);
        return "book";
    }

    @GetMapping("/add")
    public String requestAddBookForm(Book book) {
        return "addbook";
    }

}