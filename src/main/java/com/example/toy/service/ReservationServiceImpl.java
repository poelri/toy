package com.example.toy.service;

import com.example.toy.dao.ReservationDao;
import com.example.toy.dto.Post;
import com.example.toy.dto.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    private ReservationDao reservationDao;


    @Override
    public void createReservation(Reservation reservation) throws Exception {
        reservationDao.createReservation(reservation);
    }

    @Override
    public Reservation getReadReservation(int reservationId) throws Exception {
        return reservationDao.getReadReservation(reservationId);
    }

    @Override
    public void updateReservation(Reservation reservation) throws Exception {
        reservationDao.updateReservation(reservation);
    }

    @Override
    public List<Reservation> getReservationList(Map map) throws Exception {
        return reservationDao.getReservationList(map);
    }
    @Override
    public int getDataCount(Map map) throws Exception {
        return reservationDao.getDataCount(map);
    }

    @Override
    public void deleteReservation(int reservationId) throws Exception {
        reservationDao.deleteReservation( reservationId);
    }
}
