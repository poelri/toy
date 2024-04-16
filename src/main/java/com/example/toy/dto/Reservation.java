package com.example.toy.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class Reservation {
    private int reservationId;
    private String reservationDate;
    private String startDate;
    private String lastDate;
    private int memberNo;
    private String campName;
    private String categoryId;
    private Category category;
    private int price;
    private int people;
}
