package com.sergeymedvedev.bookstoread;

import java.time.LocalDate;
public class BookAfterPublishedYearFilter implements BookFilter {

    private LocalDate startDate;
    static BookAfterPublishedYearFilter After(int year) {
        BookAfterPublishedYearFilter filter = new BookAfterPublishedYearFilter();
        filter.startDate = LocalDate.of(year, 12, 31);
        return filter;
    }

    @Override
    public boolean apply(final Book book) {
        return book.getPublishedOn().isAfter(startDate);
    }

}
