package com.example.book.service;

import com.example.book.dto.BookResponseDTO;
import com.example.book.dto.CreateBookDTO;
import com.example.book.dto.UpdateBookDTO;
import java.util.List;

public interface BookService {
    List<BookResponseDTO> getAllBooks();
    BookResponseDTO getBookById(Long id);
    BookResponseDTO createBook(CreateBookDTO dto);
    BookResponseDTO updateBook(Long id, UpdateBookDTO dto);
    void deleteBook(Long id);
}

