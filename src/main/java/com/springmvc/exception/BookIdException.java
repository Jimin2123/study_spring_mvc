package com.springmvc.exception;

public class BookIdException extends RuntimeException {
  private String bookdId;

  public BookIdException(String bookId) {
    this.bookdId = bookId;
  }

  public String getBookId() {
    return this.bookdId;
  }
}
