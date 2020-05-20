package com.sergeymedvedev.bookstoread;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.Year;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("A bookshelf")
@ExtendWith(BooksParameterResolver.class)
public class BookShelfSpec {

    private BookShelf shelf;
    private Book effectiveJava;
    private Book codeComplete;
    private Book mythicalManMonth;
    private Book cleanCode;
    private Book refactoring;

    @BeforeEach
    void init(Map<String, Book> books) {
        shelf = new BookShelf();
        this.effectiveJava = books.get("Effective Java");
        this.codeComplete = books.get("Code Complete");
        this.mythicalManMonth = books.get("The Mythical Man-Month");
        this.cleanCode = books.get("Clean Code");
        this.refactoring = books.get("Refactoring: Improving the Design of Existing Code");
    }

    @Nested
    @DisplayName("is empty")
    class IsEmpty {

        @Test
        @DisplayName("bookshelf is empty when no book is added to it")
        public void emptyBookShelfWhenNoBookAdded(TestInfo testInfo) throws Exception {
            System.out.println("Working on test case named " + testInfo.getDisplayName());
//        BookShelf shelf = new BookShelf();
            List<Book> books = shelf.books();
            assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");
        }

        @Test
        @DisplayName("empty bookshelf remains empty when add is called without books")
        public void emptyBookShelfWhenAddIsCalledWithoutBooks() {
            shelf.add();
            List<Book> books = shelf.books();
            assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");
        }
    }

    @Nested
    @DisplayName("after adding books")
    class BooksAreAdded {

        @Test
        @DisplayName("bookshelf contains two books when two books are added")
        void bookshelfContainsTwoBooksWhenTwoBooksAdded(TestInfo testInfo) throws Exception {
            System.out.println("Working on test case named " + testInfo.getDisplayName());        BookShelf shelf = new BookShelf();
            shelf.add(effectiveJava, codeComplete);
            List<Book> books = shelf.books();
            assertEquals(2, books.size(), () -> "BookShelf should have two books.");
        }

        @Test
        @DisplayName("bookshelf returns an immutable books collection to client")
        void booksReturnedFromBookShelfIsImmutableForClient() {
//        BookShelf shelf = new BookShelf();
            shelf.add(effectiveJava, codeComplete);
            List<Book> books = shelf.books();
            try {
                books.add(mythicalManMonth);
                fail(() -> "Should not be able to add book to books");
            } catch (Exception e) {
                assertTrue(e instanceof UnsupportedOperationException, () -> "Should throw UnsupportedOperationException.");
            }
        }
    }

    @Nested
    @DisplayName("is arranged")
    class BooksAreArranged {

        @Test
        @DisplayName("bookshelf is arranged lexicographically by book title")
        void bookshelfArrangedByBookTitle() {
            shelf.add(effectiveJava, codeComplete, mythicalManMonth);
            List<Book> books = shelf.arrange();
            assertEquals(Arrays.asList(codeComplete, effectiveJava, mythicalManMonth),
                    books, () -> "Books in a bookshelf should be arranged lexicographically by book title");
        }

        @Test
        @DisplayName("bookshelf is arranged by user provided criteria(by book title lexicographically descending")
//    @Disabled
        void bookshelfArrangedByUserProvidedCriteria() {
            shelf.add(effectiveJava, codeComplete, mythicalManMonth);
            Comparator<Book> reversed = Comparator.<Book>naturalOrder().reversed();
            List<Book> books = shelf.arrange(reversed);
            org.assertj.core.api.Assertions.assertThat(books).isSortedAccordingTo(reversed);
        }

        @Test
        void booksInBookShelfAreInInsertionOrderAfterCallingArrange() {
            shelf.add(effectiveJava, codeComplete, mythicalManMonth);
            shelf.arrange();
            List<Book> books = shelf.books();
            assertEquals(Arrays.asList(effectiveJava, codeComplete, mythicalManMonth),
                    books, () -> "Books in bookshelf are in insertion order");
        }
    }

    @Nested
    @DisplayName("books are grouped by")
    class BooksAraGrouped {

        @Test
        @DisplayName("books inside bookshelf are grouped by publication year")
        void groupBooksInsideBookShelfByPublicationYear() {
            shelf.add(effectiveJava, codeComplete, mythicalManMonth, cleanCode);
            Map<Year, List<Book>> booksByPublicationYear = shelf.groupByPublicationYear();

            assertThat(booksByPublicationYear)
                    .containsKey(Year.of(2008))
                    .containsValues(Arrays.asList(effectiveJava, cleanCode));

            assertThat(booksByPublicationYear)
                    .containsKey(Year.of(2004))
                    .containsValues(Collections.singletonList(codeComplete));

            assertThat(booksByPublicationYear)
                    .containsKey(Year.of(1975))
                    .containsValues(Collections.singletonList(mythicalManMonth));
        }

        @Test
        @DisplayName("books inside bookshelf are grouped by user provided criteria (by author name)")
        void groupBooksByUserProvidedCriteria() {
            shelf.add(effectiveJava, codeComplete, mythicalManMonth, cleanCode);
            Map<String, List<Book>> booksByAuthor = shelf.groupBy(Book::getAuthor);

            assertThat(booksByAuthor)
                    .containsKey("Joshua Bloch")
                    .containsValues(Collections.singletonList(effectiveJava));

            assertThat(booksByAuthor)
                    .containsKey("Steve McConnel")
                    .containsValues(Collections.singletonList(codeComplete));

            assertThat(booksByAuthor)
                    .containsKey("Frederick Phillips Brooks")
                    .containsValues(Collections.singletonList(mythicalManMonth));

            assertThat(booksByAuthor)
                    .containsKey("Robert C. Martin")
                    .containsValues(Collections.singletonList(cleanCode));
        }
    }
}
