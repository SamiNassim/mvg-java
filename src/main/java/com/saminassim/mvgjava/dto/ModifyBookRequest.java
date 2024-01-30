package com.saminassim.mvgjava.dto;

import lombok.Data;

@Data
public class ModifyBookRequest {
    private String title;
    private String author;
    private String imageUrl;
    private Integer year;
    private String genre;
}
