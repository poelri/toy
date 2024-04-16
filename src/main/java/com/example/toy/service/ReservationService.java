package com.example.toy.service;

import com.example.toy.dto.Post;
import com.example.toy.dto.Reservation;

import java.util.List;
import java.util.Map;

public interface ReservationService {


    public void createReservation(Reservation reservation) throws Exception;

    public List<Reservation> getReservationList(Map map) throws Exception;

    public Reservation getReadReservation(int readeservation) throws Exception;

    public void updateReservation(Reservation reservation) throws Exception;

    public int getDataCount(Map map) throws Exception;

    public void deleteReservation(int reservationId) throws Exception;
}
