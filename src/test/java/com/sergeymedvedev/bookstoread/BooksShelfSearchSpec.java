package com.sergeymedvedev.bookstoread;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Nested
@DisplayName("search")
@ExtendWith(BooksParameterResolver.class)
public class BooksShelfSearchSpec {

    private BookShelf shelf;
    private Book effectiveJava;
    private Book codeComplete;
    private Book mythicalManMonth;
    private Book cleanCode;
    private Book refactoring;

    @BeforeEach
    void setup() {
        shelf = new BookShelf();
        effectiveJava = new Book("Effective Java", "Joshua Bloch", LocalDate.of(2008, Month.MAY, 8));
        codeComplete = new Book("Code Complete", "Steve McConnel", LocalDate.of(2004, Month.JUNE, 9));
        mythicalManMonth = new Book("The Mythical Man-Month", "Frederick Phillips Brooks", LocalDate.of(1975, Month.JANUARY, 1));
        cleanCode = new Book("Clean Code", "Robert C. Martin", LocalDate.of(2008, Month.AUGUST, 1));
        refactoring = new Book("Refactoring: Improving the Design of Existing Code", "Martin Fowler", LocalDate.of(2002, Month.MARCH, 9));
        shelf.add(codeComplete, effectiveJava, mythicalManMonth, cleanCode);
    }

    @Test
    @DisplayName(" should find books with title containing text")
    void shouldFindBooksWithTitleContainingText() {
        List<Book> books = shelf.findBooksByTitle("code");
        assertThat(books.size()).isEqualTo(2);
    }

    @Test
    @DisplayName(" should find books with title containing text and filter by date")
    void shouldFindBooksWithTitleContainingTextAndFilterByDate() {
        List<Book> books = shelf.findBooksByTitle("code", b ->
                b.getPublishedOn().isBefore(LocalDate.of(2006, 12, 31)));
        assertThat(books.size()).isEqualTo(1);
    }

}
