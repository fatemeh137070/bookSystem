package com.books.service;

import com.books.dto.BookDto;

import java.util.List;

public interface BookService {

    BookDto create(BookDto dto);
    List<BookDto> getAll();
}
