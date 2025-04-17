package com.springmvc.domain;

import java.util.Objects;

public class Book {
    private String bookId;
    private String name;
    private String author;
    private String publisher;
    private String description;
    private String category;
    private String releaseDate;
    private String condition;
    private long unitsInStock;
    private int unitPrice;

    public Book() {

    }

    public Book(String bookId, String name, int unitPrice) {
        this.bookId = bookId;
        this.name = name;
        this.unitPrice = unitPrice;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public long getUnitsInStock() {
        return unitsInStock;
    }

    public void setUnitsInStock(long unitsInStock) {
        this.unitsInStock = unitsInStock;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return unitsInStock == book.unitsInStock && unitPrice == book.unitPrice && Objects.equals(bookId, book.bookId) && Objects.equals(name, book.name) && Objects.equals(author, book.author) && Objects.equals(publisher, book.publisher) && Objects.equals(description, book.description) && Objects.equals(category, book.category) && Objects.equals(releaseDate, book.releaseDate) && Objects.equals(condition, book.condition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, name, author, publisher, description, category, releaseDate, condition, unitsInStock, unitPrice);
    }
}
