package com.example.book.service;

import com.example.book.dto.BookResponseDTO;
import com.example.book.dto.CreateBookDTO;
import com.example.book.dto.UpdateBookDTO;
import com.example.book.exception.BookNotFoundException;
import com.example.book.exception.DuplicateBookException;
import com.example.book.mapper.BookMapper;
import com.example.book.model.Book;
import com.example.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    /**
     * Retrieve all books from the database.
     *
     * @return List of BookResponseDTO objects.
     */
    @Override
    public List<BookResponseDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    /**
     * Retrieve a book by its ID.
     *
     * @param id The ID of the book.
     * @return BookResponseDTO object.
     * @throws BookNotFoundException If the book is not found.
     */
    @Override
    public BookResponseDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));
        return bookMapper.toDto(book);
    }

    /**
     * Create a new book after checking if a book with the same title and author already exists.
     *
     * @param dto CreateBookDTO containing book details.
     * @return The created BookResponseDTO object.
     * @throws DuplicateBookException If a similar book already exists.
     */
    @Override
    public BookResponseDTO createBook(CreateBookDTO dto) {
        // Check if a book with the same title and author already exists
        Optional<Book> existingBook = bookRepository.findByTitleAndAuthor(dto.title(), dto.author());
        if (existingBook.isPresent()) {
            throw new DuplicateBookException("A book with the same title and author already exists.");
        }

        Book book = bookMapper.toEntity(dto);
        book = bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    /**
     * Update an existing book's details.
     *
     * @param id  The ID of the book to update.
     * @param dto UpdateBookDTO containing updated book details.
     * @return The updated BookResponseDTO object.
     * @throws BookNotFoundException If the book is not found.
     */
    @Override
    public BookResponseDTO updateBook(Long id, UpdateBookDTO dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));
        bookMapper.updateEntity(dto, book);
        book = bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    /**
     * Delete a book by its ID.
     *
     * @param id The ID of the book to delete.
     * @throws BookNotFoundException If the book is not found.
     */
    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book not found");
        }
        bookRepository.deleteById(id);
    }
}
