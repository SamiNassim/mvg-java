package com.saminassim.mvgjava.dto;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {
    private String title;
    private String author;
    private MultipartFile image;
    private Integer year;
    private String genre;
    private Integer creatorRating;
}
