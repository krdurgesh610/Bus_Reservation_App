package org.jsp.reservationapp.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Bus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false, name = "bus_name")
	private String name;
	@Column(nullable = false, unique = true, name = "bus_number")
	private String bus_number;
	@Column(nullable = false, name = "from_location")
	private String from_location;
	@Column(nullable = false, name = "to_location")
	private String to_location;
	@Column(nullable = false, name = "number_of_seat")
	private int number_of_seat;
	@Column(nullable = false, name = "date_of_departure")
	private LocalDate date_of_departure;

	@Column(nullable = false, name = "available_seats")
	private int availableSeats;
	@Column(nullable = false, name = "cost_per_seat")
	private double costPerSeat;

	@ManyToOne
	@JoinColumn(name = "admin_id")
	@JsonIgnore
	private Admin admin;
	
	@OneToMany(mappedBy = "bus")
	private List<Ticket> bookedTickets;
}