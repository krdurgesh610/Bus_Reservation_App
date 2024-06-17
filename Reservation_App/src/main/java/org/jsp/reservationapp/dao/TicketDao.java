package org.jsp.reservationapp.dao;

import java.util.Optional;

import org.jsp.reservationapp.model.Ticket;
import org.jsp.reservationapp.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TicketDao {
	@Autowired
	private TicketRepository ticketRepository;

	public Ticket saveTicket(Ticket ticket) {
		return ticketRepository.save(ticket);
	}

	public Optional<Ticket> findById(int id) {
		return ticketRepository.findById(id);
	}

}
