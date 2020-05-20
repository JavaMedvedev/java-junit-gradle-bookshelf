package com.sergeymedvedev.bookstoread;

import java.util.ArrayList;
import java.util.List;

public class CompositeFilter implements BookFilter {

    private List<BookFilter> filters;

    CompositeFilter() {
        filters = new ArrayList<>();
    }

    @Override
    public boolean apply(final Book book) {
        return filters.stream()
                .map(bookFilter -> bookFilter.apply(book))
                .reduce(true, (book1, book2) -> book1 && book2);
    }

    void addFilter(final BookFilter bookFilter) {
        filters.add(bookFilter);
    }
}
