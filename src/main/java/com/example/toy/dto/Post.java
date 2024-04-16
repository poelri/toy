package com.example.toy.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Post {

    private int postId;
    private int memberNo;
    private int reservationId;
    private String writeDate;
    private String startDate;
    private String lastDate;
    private Category category;
    private int people;
    private String subject;
    private String content;
}
