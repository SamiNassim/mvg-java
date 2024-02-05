package com.saminassim.mvgjava.controller;

import com.saminassim.mvgjava.dto.BookRatingRequest;
import com.saminassim.mvgjava.dto.BookRequest;
import com.saminassim.mvgjava.dto.ModifyBookRequest;
import com.saminassim.mvgjava.entity.Book;
import com.saminassim.mvgjava.exception.BookAlreadyRatedException;
import com.saminassim.mvgjava.exception.BookCannotBeDeletedException;
import com.saminassim.mvgjava.exception.BookCannotBeModifiedException;
import com.saminassim.mvgjava.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> createBook(@RequestPart("book") BookRequest bookRequest, @RequestPart("image") MultipartFile image) {
        return bookService.createBook(bookRequest, image);
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/bestrating")
    public List<Book> getBestRating() {
        return bookService.getBestRating();
    }

    @GetMapping("/{id}")
    public Optional<Book> getOneBook(@PathVariable UUID id) {
        return bookService.getOneBook(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> modifyBook(@PathVariable UUID id, @ModelAttribute ModifyBookRequest modifyBookRequest) {
        try {
            bookService.modifyBook(id, modifyBookRequest);
            return ResponseEntity.ok().build();
        } catch (BookCannotBeModifiedException e) {
            return ResponseEntity.status(401)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> deleteBook(@PathVariable UUID id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.ok().build();
        } catch (BookCannotBeDeletedException e) {
            return ResponseEntity.status(403)
                    .body(e.getMessage());
        }

    }

    @PostMapping("/{id}/rating")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> createRating(@PathVariable UUID id, @RequestBody BookRatingRequest bookRatingRequest) {
        try {
            bookService.createRating(id, bookRatingRequest);
            return ResponseEntity.ok().build();
        } catch (BookAlreadyRatedException e) {
            return ResponseEntity.status(401)
                    .body(e.getMessage());
        }
    }



}
