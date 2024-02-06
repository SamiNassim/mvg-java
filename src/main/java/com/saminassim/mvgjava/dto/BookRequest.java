package com.saminassim.mvgjava.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookRequest {
    private String title;
    private String author;
    private MultipartFile image;
    private Integer year;
    private String genre;
    private Integer rating;
}
