package com.saminassim.mvgjava.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BookFrontendRequest {
    private String book;
    private MultipartFile image;
}
