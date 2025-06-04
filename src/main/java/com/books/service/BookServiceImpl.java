package com.books.service;

import com.books.dal.entity.Author;
import com.books.dal.entity.Book;
import com.books.dal.entity.Category;
import com.books.dal.repository.AuthorRepository;
import com.books.dal.repository.BookRepository;
import com.books.dal.repository.CategoryRepository;
import com.books.dto.BookDto;
import com.books.service.mapper.BookMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
    @Transactional
    public BookDto create(BookDto dto) {
        Author author = authorRepo.findById(dto.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        Category category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

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

    public void createBooksInParallel(Long authorId, Long categoryId) {
        ExecutorService executor = Executors.newFixedThreadPool(5); // 5 ترد همزمان

        for (int i = 0; i < 10; i++) {
            int index = i;
            executor.submit(() -> {
                BookDto dto = new BookDto();
                dto.setTitle("Parallel Book " + index);
                dto.setAuthorId(authorId);
                dto.setCategoryId(categoryId);

                try {
                    create(dto);
                    System.out.println("✅ Book " + index + " created in " + Thread.currentThread().getName());
                } catch (Exception e) {
                    System.err.println("❌ Failed to create Book " + index + ": " + e.getMessage());
                }
            });
        }

        executor.shutdown();
    }
    @Override
    public void createBooksInParallelCreate() {
        Author author = authorRepo.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("No author found"));
        Category category = categoryRepo.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("No category found"));

        Long authorId = author.getId();
        Long categoryId = category.getId();

        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 10; i++) {
            int index = i;
            executor.submit(() -> {
                BookDto dto = new BookDto();
                dto.setTitle("Dynamic Parallel Book " + index);
                dto.setAuthorId(authorId);
                dto.setCategoryId(categoryId);

                try {
                    create(dto);
                    System.out.println("✅ Book " + index + " created in " + Thread.currentThread().getName());
                } catch (Exception e) {
                    System.err.println("❌ Failed to create Book " + index + ": " + e.getMessage());
                }
            });
        }

        executor.shutdown();
    }
}