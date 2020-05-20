package com.sergeymedvedev.bookstoread;

public class BookAuthorFilter implements BookFilter {

    private String name;

    public static BookFilter Contains(String name) {
        BookAuthorFilter filter = new BookAuthorFilter();
        filter.name = name;
        return filter;
    }

    @Override
    public boolean apply(final Book book) {
        return book.getAuthor().contains(name);
    }

}
