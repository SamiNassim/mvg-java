package com.saminassim.mvgjava.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {
    private String title;
    private String author;
    private String imageUrl;
    private Integer year;
    private String genre;

}
