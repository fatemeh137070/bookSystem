package com.books.service;

import com.books.dto.BookDto;

import java.util.List;

public interface BookService {

    BookDto create(BookDto dto);
    List<BookDto> getAll();

    List<BookDto> getBooksByAuthorId(Long authorId);
    List<BookDto> getBooksByCategoryId(Long categoryId);

    public void createBooksInParallel(Long authorId, Long categoryId) ;

    void createBooksInParallelCreate();
}
