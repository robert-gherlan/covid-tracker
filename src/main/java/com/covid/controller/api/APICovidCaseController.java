package com.covid.controller.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.covid.model.LocationStats;
import com.covid.service.CovidService;

@RestController
@RequestMapping("api/v1/cases")
public class APICovidCaseController {

	private final CovidService covidService;

	public APICovidCaseController(CovidService covidService) {
		this.covidService = covidService;
	}

	@GetMapping("/confirmed")
	public ResponseEntity<List<LocationStats>> confirmedCases() {
		return ResponseEntity.ok(covidService.getConfiredCasesStats());
	}

	@GetMapping("/recovered")
	public ResponseEntity<List<LocationStats>> recoveredCases() {
		return ResponseEntity.ok(covidService.getRecoveredCasesStats());
	}

	@GetMapping("/deaths")
	public ResponseEntity<List<LocationStats>> deathsCases(Model model) {
		return ResponseEntity.ok(covidService.getDeathCasesStats());
	}

}