package com.example.libraryService.Mapper;

import com.example.libraryService.DTO.BookDTO;
import com.example.libraryService.Entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "string")
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(target = "formatedIsbn", expression = "java(bookDTO.formatIsbn())")
    BookDTO bookToBookDto(Book book);
}