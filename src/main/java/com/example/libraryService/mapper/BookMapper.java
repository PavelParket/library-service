package com.example.libraryService.mapper;

import com.example.libraryService.dto.BookDTO;
import com.example.libraryService.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(target = "formattedIsbn", expression = "java(bookDTO.formatIsbn(book.getIsbn()))")
    BookDTO bookToBookDto(Book book);

    List<BookDTO> booksToBookDtos(List<Book> books);
}
