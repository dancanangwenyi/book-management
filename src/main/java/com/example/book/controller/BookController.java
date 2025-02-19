package com.example.book.controller;

import com.example.book.dto.BookResponseDTO;
import com.example.book.dto.CreateBookDTO;
import com.example.book.dto.UpdateBookDTO;
import com.example.book.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    /**
     * Retrieve a list of all books.
     *
     * @return ResponseEntity containing a list of BookResponseDTO objects.
     */
    @Operation(summary = "Get all books", description = "Fetch a list of all available books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of books retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    /**
     * Retrieve a specific book by its ID.
     *
     * @param id The ID of the book.
     * @return ResponseEntity containing the requested BookResponseDTO.
     */
    @Operation(summary = "Get a book by ID", description = "Fetch details of a book using its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    /**
     * Create a new book. Ensures that a book with the same title and author does not already exist.
     *
     * @param dto The CreateBookDTO containing book details.
     * @return ResponseEntity containing the created BookResponseDTO.
     */
    @Operation(summary = "Create a new book", description = "Add a new book to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "409", description = "Book with the same title and author already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@RequestBody CreateBookDTO dto) {
        return ResponseEntity.ok(bookService.createBook(dto));
    }

    /**
     * Update an existing book by its ID.
     *
     * @param id  The ID of the book to update.
     * @param dto The UpdateBookDTO containing updated book details.
     * @return ResponseEntity containing the updated BookResponseDTO.
     */
    @Operation(summary = "Update a book", description = "Modify details of an existing book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book updated successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long id, @RequestBody UpdateBookDTO dto) {
        return ResponseEntity.ok(bookService.updateBook(id, dto));
    }

    /**
     * Delete a book by its ID.
     *
     * @param id The ID of the book to delete.
     * @return ResponseEntity with no content upon successful deletion.
     */
    @Operation(summary = "Delete a book", description = "Remove a book from the system using its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Book deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
