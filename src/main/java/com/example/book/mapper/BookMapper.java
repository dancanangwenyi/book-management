package com.example.book.mapper;

import com.example.book.dto.BookResponseDTO;
import com.example.book.dto.CreateBookDTO;
import com.example.book.dto.UpdateBookDTO;
import com.example.book.model.Book;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BookMapper {

    public BookResponseDTO toDto(Book book) {
        return new BookResponseDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getPublishedDate().toString(),
                book.getPrice()
        );
    }

    public Book toEntity(CreateBookDTO dto) {
        return Book.builder()
                .title(dto.title())
                .author(dto.author())
                .isbn(dto.isbn())
                .publishedDate(dto.publishedDate() != null ? java.time.LocalDate.parse(dto.publishedDate()) : null)
                .price(dto.price())
                .build();
    }

    public void updateEntity(UpdateBookDTO dto, Book book) {
        if (dto.title() != null) book.setTitle(dto.title());
        if (dto.author() != null) book.setAuthor(dto.author());
        if (dto.isbn() != null) book.setIsbn(dto.isbn());
        if (dto.publishedDate() != null) book.setPublishedDate(java.time.LocalDate.parse(dto.publishedDate()));
        if (dto.price() != null) book.setPrice(dto.price());
    }
}
