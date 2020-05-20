package com.sergeymedvedev.bookstoread;

import java.time.LocalDate;

public class BookBeforePublishedYearFilter implements BookFilter {

    private LocalDate startDate;
    static BookBeforePublishedYearFilter Before(int year) {
        BookBeforePublishedYearFilter filter = new BookBeforePublishedYearFilter();
        filter.startDate = LocalDate.of(year, 1, 1);
        return filter;
    }

    @Override
    public boolean apply(final Book book) {
        return book.getPublishedOn().isBefore(startDate);
    }

}
