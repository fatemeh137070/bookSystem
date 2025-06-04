package com.books.service;

import com.books.dal.entity.Author;
import com.books.dal.entity.Book;
import com.books.dal.entity.Category;
import com.books.dal.repository.AuthorRepository;
import com.books.dal.repository.BookRepository;
import com.books.dal.repository.CategoryRepository;
import com.books.dto.BookDto;
import com.books.service.mapper.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepo;
    private final AuthorRepository authorRepo;
    private final CategoryRepository categoryRepo;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepo, AuthorRepository authorRepo, CategoryRepository categoryRepo, BookMapper bookMapper) {
        this.bookRepo = bookRepo;
        this.authorRepo = authorRepo;
        this.categoryRepo = categoryRepo;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookDto create(BookDto dto) {
        Author author = authorRepo.findById(dto.getAuthorId()).orElseThrow();
        Category category = categoryRepo.findById(dto.getCategoryId()).orElseThrow();

        Book book = bookMapper.toEntity(dto);
        book.setAuthor(author);
        book.setCategory(category);

        return bookMapper.toDto(bookRepo.save(book));
    }

    @Override
    public List<BookDto> getAll() {
        return bookRepo.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDto> getBooksByAuthorId(Long authorId) {
        Author author = authorRepo.findById(authorId).orElseThrow(() -> new RuntimeException("Author not found"));
        return bookRepo.findByAuthor(author).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDto> getBooksByCategoryId(Long categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
        return bookRepo.findByCategory(category).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }
}