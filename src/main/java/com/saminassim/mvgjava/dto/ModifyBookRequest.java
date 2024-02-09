package com.saminassim.mvgjava.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ModifyBookRequest {
    private UUID id;
    private String title;
    private String author;
    private Integer year;
    private String genre;
}
