package com.books.service.mapper;


import com.books.dal.entity.Book;
import com.books.dto.BookDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(source = "author.id", target = "authorId")
    @Mapping(source = "category.id", target = "categoryId")
    BookDto toDto(Book book);

    @Mapping(source = "authorId", target = "author.id")
    @Mapping(source = "categoryId", target = "category.id")
    Book toEntity(BookDto dto);

}
