package org.jsp.reservationapp.dto;

import java.time.LocalDate;

import org.jsp.reservationapp.model.Admin;

import lombok.Data;

@Data
public class BusRequest {
	private int id;
	private String name;
	private String bus_number;
	private String from_location;
	private String to_location;
	private int number_of_seat;
	private LocalDate date_of_departure;
	private double costPerSeat;
	private Admin admin;
}