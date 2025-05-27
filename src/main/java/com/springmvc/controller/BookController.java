package com.springmvc.controller;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.springmvc.exception.BookIdException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger; // slf4j란 : 다양한 로깅 프레임워크에 대한 간단한 퍼사드 또는 추상화 역할
import org.slf4j.LoggerFactory; // SLF4J API를 사용하여 로깅을 수행하는 클래스
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import com.springmvc.domain.Book;
import com.springmvc.service.BookService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/books")
public class BookController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BookService bookService;

    @Value("${uploadPath}")
    private String uploadPath;

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
    public String requestAddBookForm(@ModelAttribute("NewBook") Book book) {
        return "addBook";
    }

    // @ModelAttribute : 요청 파라미터를 객체에 바인딩하는 메소드
    @PostMapping("/add")
    public String submitAddNewBook(@ModelAttribute("NewBook") Book book) {
        MultipartFile bookImage = book.getBookImage();

        if(bookImage != null && !bookImage.isEmpty()) {
            String saveName = bookImage.getOriginalFilename();
            File saveFile = new File(this.uploadPath, saveName);
            try {
                bookImage.transferTo(saveFile);
                book.setFileName(saveName);
            }catch(IOException e) {
                e.printStackTrace();
            }
        }
        this.bookService.setNewBook(book);
        return "redirect:/books";
    }

    // @ModelAttribute : 모델에 데이터를 추가하는 메소드
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("addTitle", "신규 도서 등록");
    }

    // @InitBinder : 특정 컨트롤러의 요청을 처리하기 전에 바인딩 설정을 초기화하는 메소드
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields("bookId", "name", "unitPrice", "author", "description", "publisher",
                "category", "unitsInStock", "releaseDate", "condition", "bookImage");
    }

    @ExceptionHandler(value= {BookIdException.class})
    public ModelAndView handlerException(HttpServletRequest req
            , BookIdException exception) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("invalidBookId", exception.getBookId());
        mav.addObject("exception", exception);
        mav.addObject("url", req.getRequestURL() + "?" + req.getQueryString());
        mav.setViewName("errorBook");
        return mav;
    }
}