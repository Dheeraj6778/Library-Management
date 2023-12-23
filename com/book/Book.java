package com.book;

public class Book {
    private String title;
    private String author;
    private String isbn;
    private int price;
    private int pages;
    private int available;
    public Book(String title,String author,String isbn,int price,int pages,int available){
        this.title=title;
        this.author=author;
        this.isbn=isbn;
        this.price=price;
        this.pages=pages;
        this.available=available;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append("title: "+title);
        sb.append("author: "+author);
        sb.append("price "+Integer.toString(price));
        sb.append("pages "+Integer.toString(pages));
        return sb.toString();
    }
}
