package com.covid.controller.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.covid.dto.CovidCaseStatsDto;
import com.covid.dto.DailyReportsCovidCaseStatsDto;
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
	public ResponseEntity<CovidCaseStatsDto> confirmedCases() {
		return mapToDto(covidService.getConfiredCasesStats());
	}

	@GetMapping("/recovered")
	public ResponseEntity<CovidCaseStatsDto> recoveredCases() {
		return mapToDto(covidService.getRecoveredCasesStats());
	}

	@GetMapping("/deaths")
	public ResponseEntity<CovidCaseStatsDto> deathsCases() {
		return mapToDto(covidService.getDeathCasesStats());
	}

	private ResponseEntity<CovidCaseStatsDto> mapToDto(List<LocationStats> locationStats) {
		return ResponseEntity.ok(new CovidCaseStatsDto(locationStats));
	}

	@GetMapping("/daily_reports")
	public ResponseEntity<DailyReportsCovidCaseStatsDto> dailyReportsCases() {
		return ResponseEntity.ok(new DailyReportsCovidCaseStatsDto(covidService.getDailyReportsCasesStats()));
	}

}