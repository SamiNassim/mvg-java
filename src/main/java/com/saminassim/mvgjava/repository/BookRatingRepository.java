package com.saminassim.mvgjava.repository;

import com.saminassim.mvgjava.entity.BookRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRatingRepository extends JpaRepository<BookRating, Long> {
}
