package org.jsp.reservationapp.controller;

//import java.util.List;

import org.jsp.reservationapp.dto.BusRequest;
import org.jsp.reservationapp.dto.BusResponse;
import org.jsp.reservationapp.dto.ResponseStructure;
import org.jsp.reservationapp.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/BUS")
public class BusController {
	@Autowired
	private BusService service;

	@PostMapping("/{admin_id}")
	public ResponseEntity<ResponseStructure<BusResponse>> saveBus(@RequestBody BusRequest busRequest,
			@PathVariable(name = "admin_id") int admin_id) {
		return service.saveBus(busRequest, admin_id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ResponseStructure<BusResponse>> updateBus(@RequestBody BusRequest busRequest,
			@PathVariable(name = "id") int id) {
		return service.updateBus(busRequest, id);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<BusResponse>> findById(@PathVariable int id) {
		return service.findById(id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteBus(@PathVariable int id) {
		return service.deleteBus(id);
	}

//	@GetMapping
//	public ResponseEntity<ResponseStructure<List<Bus>>> findByBusName(@PathVariable String name) {
//		return service.findByBusName(name);
//	}

}
