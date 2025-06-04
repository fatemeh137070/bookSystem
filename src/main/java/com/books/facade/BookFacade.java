package com.books.facade;

import com.books.dal.entity.Book;
import com.books.dto.BookDto;
import com.books.service.BookService;
import com.books.service.mapper.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookFacade {

    private final BookService bookService;
    private final BookMapper bookMapper;

    public BookFacade(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    public BookDto save(BookDto bookDto) {
        return bookService.create(bookDto);
    }

    public List<BookDto> getAll() {
        return bookService.getAll();
    }
}
