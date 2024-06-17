package org.jsp.reservationapp.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.jsp.reservationapp.model.Bus;
import org.jsp.reservationapp.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BusDao {
	@Autowired
	private BusRepository repository;

	public Bus saveBus(Bus bus) {
		return repository.save(bus);
	}

	public Optional<Bus> findById(int id) {
		return repository.findById(id);
	}

//	public List<Bus> findByBusName(String name) {
//		return repository.findByBusName(name);
//	}

//	public Optional<Bus> findBYBusNumber(String bus_number) {
//		return repository.findByBusNumber(bus_number);
//	}

//	public List<Bus> findByDate(LocalDate date_of_departure) {
//		return repository.findByDate(date_of_departure);
//	}

	public List<Bus> findAllBus() {
		return repository.findAll();
	}

	public List<Bus> findBuses(String from_location, String to_location, LocalDate date_of_departure) {
		return repository.findBuses(from_location, to_location, date_of_departure);
	}

	public List<Bus> findBusesByAdminId(int admin_id) {
		return repository.findByAdminId(admin_id);
	}

	public void deleteBus(int id) {
		repository.deleteById(id);
	}

}
