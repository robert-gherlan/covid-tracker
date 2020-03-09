package com.covid.controller.html;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.covid.dto.CovidCaseStatsDto;
import com.covid.model.LocationStats;
import com.covid.service.CovidService;

@Controller
@RequestMapping("/cases")
public class HTMLCovidCaseController {

	private final CovidService covidService;

	public HTMLCovidCaseController(CovidService covidService) {
		this.covidService = covidService;
	}

	@GetMapping("/confirmed")
	public String confirmedCases(Model model) {
		List<LocationStats> stats = covidService.getConfiredCasesStats();
		addStatsModelAttribute(model, stats);
		return "confirmed";
	}

	@GetMapping("/recovered")
	public String recoveredCases(Model model) {
		List<LocationStats> stats = covidService.getRecoveredCasesStats();
		addStatsModelAttribute(model, stats);
		return "recovered";
	}

	@GetMapping("/deaths")
	public String deathsCases(Model model) {
		List<LocationStats> stats = covidService.getDeathCasesStats();
		addStatsModelAttribute(model, stats);
		return "deaths";
	}

	private void addStatsModelAttribute(Model model, List<LocationStats> stats) {
		model.addAttribute("stats", new CovidCaseStatsDto(stats));
	}
}