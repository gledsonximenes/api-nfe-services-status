package com.api.viasoft.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.viasoft.constant.States;
import com.api.viasoft.model.NfeServicesStatus;
import com.api.viasoft.service.NfeServicesStatusService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/")
public class NfeServicesStatusController {

	@Autowired
	private NfeServicesStatusService service;

	@GetMapping(value = "status/services")
	public ResponseEntity<?> getCurrentStatusServicesNfe(
			@RequestParam(value = "date", required = false) String date
	) {

		try {
			List<NfeServicesStatus> serviceStatus;
			if(date == null) {
				serviceStatus = service.searchCurrentStatusServicesNfe();
			}else{
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				formatter = formatter.withLocale(new Locale ("pt", "BR"));
				LocalDate dt = LocalDate.parse(date, formatter);
				serviceStatus = service.searchCurrentStatusServicesNfeByDate(dt);
			}
			return ResponseEntity.ok(serviceStatus);

		} catch (Exception e) {
			log.error(e);
			return ResponseEntity.ok("Error " + e);
		}
	}

	@GetMapping(value = "status/services/{state}")
	public ResponseEntity<?> getCurrentStatusServicesNfeByState(
			@PathVariable(value = "state") String state
	) {
		try {
			NfeServicesStatus serviceStatus = service.searchCurrentStatusServicesNfeByState(state);
			return ResponseEntity.ok(serviceStatus);

		} catch (Exception e) {
			log.error(e);
			return ResponseEntity.ok("Error " + e);
		}
	}

	@GetMapping(value = "states/services/unavailability")
	public ResponseEntity<?> getServiceUnavailabilityByState() {
		try {
			States state = service.getServiceUnavailabilityByState();
			return ResponseEntity.ok(state);

		} catch (Exception e) {
			log.error(e);
			return ResponseEntity.ok("Error " + e);
		}
	}

}
