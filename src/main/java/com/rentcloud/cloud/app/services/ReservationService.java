/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rentcloud.cloud.app.services;

import com.rentcloud.cloud.app.entities.Reservation;
import com.rentcloud.cloud.app.repositories.ReservationRepository;
import com.rentcloud.cloud.app.services.report.CountClient;
import com.rentcloud.cloud.app.services.report.ReservationStatus;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tatiana
 */
@Service
public class ReservationService {

    @Autowired
    private ReservationRepository repository;

    /**
     * GET
     *
     * @return Lista de reservation
     */
    public List<Reservation> getAll() {
        return repository.getAll();
    }

    /**
     * GET*{id}
     *
     * @param reservationId
     * @return
     */
    public Optional<Reservation> getReservation(int reservationId) {
        return repository.getReservation(reservationId);
    }

    /**
     * POST
     *
     * @param reservation
     * @return
     */
    public Reservation save(Reservation reservation) {
        if (reservation.getIdReservation() == null) {
            return repository.save(reservation);
        } else {
            Optional<Reservation> existReservation = repository.getReservation(reservation.getIdReservation());
            if (existReservation.isPresent()) {
                return reservation;
            } else {
                return repository.save(reservation);
            }
        }
    }

    /**
     * UPDATE
     *
     * @param reservation
     * @return
     */
    public Reservation update(Reservation reservation) {
        if (reservation.getIdReservation() != null) {
            Optional<Reservation> existReservation = repository.getReservation(reservation.getIdReservation());
            if (existReservation.isPresent()) {
                if (reservation.getClient() != null) {
                    existReservation.get().setClient(reservation.getClient());
                }
                if (reservation.getCloud() != null) {
                    existReservation.get().setCloud(reservation.getCloud());
                }
                if (reservation.getDevolutionDate() != null) {
                    existReservation.get().setDevolutionDate(reservation.getDevolutionDate());
                }
                if (reservation.getScore() != null) {
                    existReservation.get().setScore(reservation.getScore());
                }
                if (reservation.getStartDate() != null) {
                    existReservation.get().setStartDate(reservation.getStartDate());
                }
                if (reservation.getStatus() != null) {
                    existReservation.get().setStatus(reservation.getStatus());
                }
                return repository.save(existReservation.get());
            } else {
                return reservation;
            }
        } else {
            return reservation;
        }
    }

    /**
     * DELETE
     *
     * @param reservationId
     * @return
     */
    public boolean delete(int reservationId) {
        Boolean respuesta = getReservation(reservationId).map(reservation -> {
            repository.delete(reservation);
            return true;
        }).orElse(false);
        return respuesta;
    }

    public ReservationStatus getReservationsStatusReport() {
        List<Reservation> completed = repository.getReservationsByStatus("completed");
        List<Reservation> cancelled = repository.getReservationsByStatus("cancelled");
        return new ReservationStatus(completed.size(), cancelled.size());
    }

    public List<Reservation> getReservationsPeriod(String dateOne, String dateTwo) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = new Date();
        Date endDate = new Date();

        try {
            startDate = dateFormat.parse(dateOne);
            endDate = dateFormat.parse(dateTwo);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (startDate.before(endDate)) {
            return repository.getReservationsPeriod(endDate, endDate);
        } else {
            return new ArrayList<>();
        }

    }

    public List<CountClient> getTopClients() {
        return repository.getTopClients();
    }

}
