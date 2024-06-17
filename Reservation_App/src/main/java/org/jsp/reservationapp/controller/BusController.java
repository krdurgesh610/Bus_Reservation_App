package org.jsp.reservationapp.controller;

import java.time.LocalDate;
import java.util.List;

import org.jsp.reservationapp.dto.BusRequest;
import org.jsp.reservationapp.dto.ResponseStructure;
import org.jsp.reservationapp.model.Bus;
import org.jsp.reservationapp.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/BUS")
public class BusController {
	@Autowired
	private BusService service;

	@PostMapping("/{admin_id}")
	public ResponseEntity<ResponseStructure<Bus>> saveBus(@RequestBody BusRequest busRequest,
			@PathVariable(name = "admin_id") int admin_id) {
		return service.saveBus(busRequest, admin_id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ResponseStructure<Bus>> updateBus(@RequestBody BusRequest busRequest,
			@PathVariable(name = "id") int id) {
		return service.updateBus(busRequest, id);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Bus>> findById(@PathVariable int id) {
		return service.findById(id);
	}

	@GetMapping
	public ResponseEntity<ResponseStructure<List<Bus>>> findAllBus() {
		return service.findAllBus();
	}

	@GetMapping("/find")
	public ResponseEntity<ResponseStructure<List<Bus>>> findBuses(@RequestParam String from_location,
			@RequestParam String to_location, @RequestParam LocalDate date_of_departure) {
		return service.findBuses(from_location, to_location, date_of_departure);
	}

	@GetMapping("/find/{admin_id}")
	public ResponseEntity<ResponseStructure<List<Bus>>> findByAdminId(@PathVariable int admin_id) {
		return service.findByAdminId(admin_id);
	}

	@DeleteMapping("/{admin_id}/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteBus(@PathVariable(name = "admin_id") int admin_id,
			@PathVariable int id) {
		return service.deleteBus(admin_id, id);
	}

}
