package com.example.book.dto;

import java.math.BigDecimal;

public record CreateBookDTO(String title, String author, String isbn, String publishedDate, BigDecimal price) {
}
