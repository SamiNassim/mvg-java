package com.saminassim.mvgjava.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ModifyBookRequest {
    private String title;
    private String author;
    private MultipartFile image;
    private Integer year;
    private String genre;
}
