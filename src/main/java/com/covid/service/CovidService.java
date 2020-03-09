package com.covid.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.covid.model.LocationStats;
import com.covid.repository.CovidCaseRepository;

@Service
public class CovidService {

	private final CovidCaseRepository covidRepository;

	public CovidService(CovidCaseRepository covidRepository) {
		this.covidRepository = covidRepository;
	}

	public List<LocationStats> getConfiredCasesStats() {
		return covidRepository.getConfirmedCasesStats();
	}

	public List<LocationStats> getRecoveredCasesStats() {
		return covidRepository.getRecoveredCasesStats();
	}

	public List<LocationStats> getDeathCasesStats() {
		return covidRepository.getDeathCasesStats();
	}
}
