package org.jsp.reservationapp.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.jsp.reservationapp.dao.AdminDao;
import org.jsp.reservationapp.dao.BusDao;
import org.jsp.reservationapp.dto.BusRequest;
import org.jsp.reservationapp.dto.ResponseStructure;
import org.jsp.reservationapp.exception.AdminNotFoundException;
import org.jsp.reservationapp.exception.BusNotFoundException;
import org.jsp.reservationapp.model.Admin;
import org.jsp.reservationapp.model.Bus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BusService {
	@Autowired
	private BusDao busDao;

	@Autowired
	private AdminDao adminDao;

	public ResponseEntity<ResponseStructure<Bus>> saveBus(BusRequest busRequest, int admin_id) {
		Optional<Admin> recAdmin = adminDao.findById(admin_id);
		ResponseStructure<Bus> structure = new ResponseStructure<>();

		if (recAdmin.isPresent()) {
			Bus bus = mapToBus(busRequest);
			bus.setAvailableSeats(bus.getNumber_of_seat());
			bus.setAdmin(recAdmin.get());
			recAdmin.get().getBuses().add(bus);
			adminDao.saveAdmin(recAdmin.get());
			busDao.saveBus(bus);
			structure.setData(bus);
			structure.setMessage("Bus Added");
			structure.setStatusCode(HttpStatus.CREATED.value());
			return ResponseEntity.status(HttpStatus.CREATED).body(structure);
		}

		throw new AdminNotFoundException("Can't Add Bus as Admin Id is Invalid");
	}

	public ResponseEntity<ResponseStructure<Bus>> updateBus(BusRequest busRequest, int id) {
		ResponseStructure<Bus> structure = new ResponseStructure<>();
		Optional<Bus> recAdmin = busDao.findById(id);

		if (recAdmin.isEmpty())
			throw new BusNotFoundException("Can't update bus, as Id is Invalid");

		Bus dbBus = recAdmin.get();
		dbBus.setBus_number(busRequest.getBus_number());
		dbBus.setDate_of_departure(busRequest.getDate_of_departure());
		dbBus.setFrom_location(busRequest.getFrom_location());
		dbBus.setTo_location(busRequest.getTo_location());
		dbBus.setName(busRequest.getName());
		dbBus.setNumber_of_seat(busRequest.getNumber_of_seat());
		dbBus.setCostPerSeat(busRequest.getCostPerSeat());
		dbBus.setAvailableSeats(busRequest.getNumber_of_seat());
		dbBus = busDao.saveBus(dbBus);
		structure.setData(dbBus);
		structure.setMessage("Bus Updated");
		structure.setStatusCode(HttpStatus.ACCEPTED.value());
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(structure);

	}

	public ResponseEntity<ResponseStructure<Bus>> findById(int id) {
		ResponseStructure<Bus> structure = new ResponseStructure<>();
		Optional<Bus> recAdmin = busDao.findById(id);

		if (recAdmin.isEmpty())
			throw new BusNotFoundException("Invalid Bus id");
		structure.setMessage("Bus Found");
		structure.setData(recAdmin.get());
		structure.setStatusCode(HttpStatus.OK.value());
		return ResponseEntity.status(HttpStatus.OK).body(structure);

	}

	public ResponseEntity<ResponseStructure<String>> deleteBus(int admin_id, int id) {
		Optional<Admin> recAdminAdmin = adminDao.findById(admin_id);
		Optional<Bus> recAdmin = busDao.findById(id);
		ResponseStructure<String> structure = new ResponseStructure<>();

		if (recAdminAdmin.isPresent()) {
			if (recAdmin.isPresent()) {
				busDao.deleteBus(id);
				structure.setData("BUs Found");
				structure.setMessage("Bus Deleted");
				structure.setStatusCode(HttpStatus.OK.value());
				return ResponseEntity.status(HttpStatus.OK).body(structure);
			}
			throw new BusNotFoundException("Can't Delete Bus as Id is Invalid");
		}
		throw new AdminNotFoundException("Admin Id Invalid");
	}

	public ResponseEntity<ResponseStructure<List<Bus>>> findAllBus() {
		List<Bus> recAdmin = busDao.findAllBus();
		ResponseStructure<List<Bus>> structure = new ResponseStructure<>();
		structure.setData(recAdmin);
		if (recAdmin.isEmpty()) {
			throw new BusNotFoundException("List of BUses Not Present");
		}
		structure.setMessage("List of Buses");
		structure.setStatusCode(HttpStatus.OK.value());
		return ResponseEntity.status(HttpStatus.OK).body(structure);
	}

	public ResponseEntity<ResponseStructure<List<Bus>>> findBuses(String from_location, String to_location,
			LocalDate date_of_departure) {
		List<Bus> recAdmin = busDao.findBuses(from_location, to_location, date_of_departure);
		ResponseStructure<List<Bus>> structure = new ResponseStructure<>();
		if (recAdmin.isEmpty())
			throw new BusNotFoundException("List Not Found");
		structure.setData(recAdmin);
		structure.setMessage("List of Buses for entered route on this Date");
		structure.setStatusCode(HttpStatus.OK.value());
		return ResponseEntity.status(HttpStatus.OK).body(structure);
	}

	public ResponseEntity<ResponseStructure<List<Bus>>> findByAdminId(int admin_id) {
		ResponseStructure<List<Bus>> structure = new ResponseStructure<>();
		List<Bus> buses = busDao.findBusesByAdminId(admin_id);
		if (buses.isEmpty())
			throw new BusNotFoundException("No Buses for entered Admin Id");
		structure.setData(buses);
		structure.setMessage("List of Buses for entered Admin Id");
		structure.setStatusCode(HttpStatus.OK.value());
		return ResponseEntity.status(HttpStatus.OK).body(structure);
	}

	private Bus mapToBus(BusRequest busRequest) {
		return Bus.builder().name(busRequest.getName()).bus_number(busRequest.getBus_number())
				.date_of_departure(busRequest.getDate_of_departure()).from_location(busRequest.getFrom_location())
				.to_location(busRequest.getTo_location()).number_of_seat(busRequest.getNumber_of_seat())
				.costPerSeat(busRequest.getCostPerSeat()).build();
	}

}
