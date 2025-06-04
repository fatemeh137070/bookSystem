package com.books.controller;

import com.books.dal.entity.Book;
import com.books.dto.BookDto;
import com.books.facade.BookFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")

public class BookController {

    private final BookFacade bookFacade;

    public BookController(BookFacade bookFacade) {
        this.bookFacade = bookFacade;
    }


    @PostMapping
    public BookDto addBook(@RequestBody BookDto book) {
        return bookFacade.save(book);
    }

    @GetMapping
    public List<BookDto> getAllBooks() {
        return bookFacade.getAll();
    }
    @GetMapping("/author/{authorId}")
    public List<BookDto> getBooksByAuthor(@PathVariable Long authorId) {
        return bookFacade.getBooksByAuthorId(authorId);
    }


    @GetMapping("/category/{categoryId}")
    public List<BookDto> getBooksByCategory(@PathVariable Long categoryId) {
        return bookFacade.getBooksByCategoryId(categoryId);
    }
}
