package com.example.toy.dao;


import com.example.toy.dto.Post;
import com.example.toy.dto.Reservation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReservationDao {
    public void createReservation(Reservation reservation) throws Exception;

    public Reservation getReadReservation(int readeservation) throws Exception;

    public void updateReservation(Reservation reservation) throws Exception;

    public List<Reservation> getReservationList(Map map) throws Exception;


    public int getDataCount(Map map) throws Exception;


    public void deleteReservation(int reservationId) throws Exception;
}
