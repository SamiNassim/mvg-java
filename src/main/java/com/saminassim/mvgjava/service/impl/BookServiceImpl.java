package com.saminassim.mvgjava.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saminassim.mvgjava.dto.*;
import com.saminassim.mvgjava.entity.Book;
import com.saminassim.mvgjava.entity.BookRating;
import com.saminassim.mvgjava.exception.BookAlreadyRatedException;
import com.saminassim.mvgjava.exception.BookCannotBeDeletedException;
import com.saminassim.mvgjava.exception.BookCannotBeModifiedException;
import com.saminassim.mvgjava.repository.BookRatingRepository;
import com.saminassim.mvgjava.repository.BookRepository;
import com.saminassim.mvgjava.repository.UserRepository;
import com.saminassim.mvgjava.service.BookService;
import com.saminassim.mvgjava.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookRatingRepository bookRatingRepository;
    private final StorageService storageService;

    @Override
    public ResponseEntity<String> createBook(BookFrontendRequest bookFrontendRequest) throws JsonProcessingException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID currentUserId = Objects.requireNonNull(userRepository.findByEmail(authentication.getName()).orElse(null)).getId();

        ObjectMapper objectMapper = new ObjectMapper();
        BookRequest bookRequest = objectMapper.readValue(bookFrontendRequest.getBook(), BookRequest.class);
        bookRequest.setImage(bookFrontendRequest.getImage());
        JsonNode gradeNode = objectMapper.readTree(bookFrontendRequest.getBook());
        bookRequest.setRating(gradeNode.get("ratings").get(0).get("grade").asInt());

        storageService.store(bookRequest.getImage());

        Book newBook = new Book();
        BookRating newBookRating = new BookRating();

        newBook.setTitle(bookRequest.getTitle());
        newBook.setAuthor(bookRequest.getAuthor());
        newBook.setImageUrl("http://localhost:4000/images/" + bookRequest.getImage().getOriginalFilename());
        newBook.setYear(bookRequest.getYear());
        newBook.setGenre(bookRequest.getGenre());
        newBook.setUserId(currentUserId);
        newBook.setAverageRating(bookRequest.getRating());

        newBookRating.setBook(newBook);
        newBookRating.setUserId(currentUserId);
        newBookRating.setGrade(bookRequest.getRating());

        bookRepository.save(newBook);
        bookRatingRepository.save(newBookRating);

        return new ResponseEntity<>("Livre enregistré !", HttpStatus.CREATED);

    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> getOneBook(UUID bookId) {
        return bookRepository.findById(bookId);
    }

    @Override
    public List<Book> getBestRating() {
        return bookRepository.findTop3BooksByOrderByAverageRatingDesc();
    }

    @Override
    public Book modifyBook(ModifyBookFrontendRequest modifyBookFrontendRequest, ModifyBookRequest modifyBookRequest, UUID bookId) throws JsonProcessingException {

        Optional<Book> selectedBook = bookRepository.findById(bookId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID currentUserId = Objects.requireNonNull(userRepository.findByEmail(authentication.getName()).orElse(null)).getId();

        if(!currentUserId.equals(selectedBook.orElseThrow().getUserId())) {
            throw new BookCannotBeModifiedException("Vous ne pouvez pas modifier ce livre.");
        }

        if(modifyBookFrontendRequest.getBook() != null){
            ObjectMapper objectMapper = new ObjectMapper();
            BookRequest bookRequest = objectMapper.readValue(modifyBookFrontendRequest.getBook(), BookRequest.class);

            selectedBook.orElseThrow().setTitle(bookRequest.getTitle());
            selectedBook.orElseThrow().setAuthor(bookRequest.getAuthor());
            selectedBook.orElseThrow().setYear(bookRequest.getYear());
            selectedBook.orElseThrow().setGenre(bookRequest.getGenre());
        }

       if(modifyBookFrontendRequest.getImage() != null){
            String oldFilename = selectedBook.orElseThrow().getImageUrl().substring(29);
            storageService.deleteFile(oldFilename);
            storageService.store(modifyBookFrontendRequest.getImage());
            selectedBook.orElseThrow().setImageUrl("http://localhost:4000/images/" + modifyBookFrontendRequest.getImage().getOriginalFilename());
        }

        if(modifyBookRequest.getId() != null) {
            selectedBook.orElseThrow().set_id(modifyBookRequest.getId());
        }

        if(modifyBookRequest.getTitle() != null) {
            selectedBook.orElseThrow().setTitle(modifyBookRequest.getTitle());
        }

        if(modifyBookRequest.getAuthor() != null) {
            selectedBook.orElseThrow().setAuthor(modifyBookRequest.getAuthor());
        }

        if(modifyBookRequest.getYear() != null) {
            selectedBook.orElseThrow().setYear(modifyBookRequest.getYear());
        }

        if(modifyBookRequest.getGenre() != null) {
            selectedBook.orElseThrow().setGenre(modifyBookRequest.getGenre());
        }

        return bookRepository.save(selectedBook.orElseThrow());

    }


    public void deleteBook(UUID bookId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID currentUserId = Objects.requireNonNull(userRepository.findByEmail(authentication.getName()).orElse(null)).getId();
        Optional<Book> selectedBook = bookRepository.findById(bookId);
        if(!currentUserId.equals(selectedBook.orElseThrow().getUserId())) {
            throw new BookCannotBeDeletedException("Vous ne pouvez pas supprimer ce livre.");
        }
        String imageNameToDelete = selectedBook.orElseThrow().getImageUrl().substring(29);
        storageService.deleteFile(imageNameToDelete);
        bookRepository.deleteBookBy_id(bookId);
    }

    @Override
    public Book createRating(UUID bookId, BookRatingRequest bookRatingRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID currentUserId = Objects.requireNonNull(userRepository.findByEmail(authentication.getName()).orElse(null)).getId();
        Optional<Book> selectedBook = bookRepository.findById(bookId);

        // Check if the user has already rated the book
        for (BookRating rating : selectedBook.orElseThrow().getRatings()) {
            if(rating.getUserId().equals(currentUserId)) {
                throw new BookAlreadyRatedException("Vous avez déjà noté ce livre.");
            }
        }

        BookRating newRating = new BookRating();
        newRating.setUserId(currentUserId);
        newRating.setBook(selectedBook.orElseThrow());
        newRating.setGrade(bookRatingRequest.getRating());

        bookRatingRepository.save(newRating);

        int allRatings = selectedBook.orElseThrow().getRatings().size();
        Integer sum = 0;
        for(BookRating rating : selectedBook.orElseThrow().getRatings()) {
            sum += rating.getGrade();
        }

        Integer newAverage = Math.toIntExact(Math.round((double) sum / allRatings));

        selectedBook.orElseThrow().setAverageRating(newAverage);

        return bookRepository.save(selectedBook.orElseThrow());

    }


}
