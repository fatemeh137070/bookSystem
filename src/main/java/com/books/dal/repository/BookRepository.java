package com.books.dal.repository;

import com.books.dal.entity.Author;
import com.books.dal.entity.Book;
import com.books.dal.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByAuthor(Author author);
    List<Book> findByCategory(Category category);
}
