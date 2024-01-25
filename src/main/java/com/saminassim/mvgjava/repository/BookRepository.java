package com.saminassim.mvgjava.repository;

import com.saminassim.mvgjava.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findTop3BooksByOrderByAverageRatingDesc();
    Boolean deleteBookById(Long bookId);
}
