package com.covid.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.covid.model.LocationStats;

public class CovidCaseStatsDto {

	private List<LocationStatsDto> locationStats;

	private int totalReportedCases;

	private int totalNewCases;

	public CovidCaseStatsDto(List<LocationStats> covidCaseStats) {
		this.locationStats = covidCaseStats.stream().map(LocationStatsDto::new).collect(Collectors.toList());
		this.totalReportedCases = covidCaseStats.stream().mapToInt(LocationStats::getLatestTotalCases).sum();
		this.totalNewCases = covidCaseStats.stream().mapToInt(LocationStats::getDifferenceFromPreviousDay).sum();
	}

	public List<LocationStatsDto> getLocationStats() {
		return locationStats;
	}

	public void setLocationStats(List<LocationStatsDto> locationStats) {
		this.locationStats = locationStats;
	}

	public int getTotalReportedCases() {
		return totalReportedCases;
	}

	public void setTotalReportedCases(int totalReportedCases) {
		this.totalReportedCases = totalReportedCases;
	}

	public int getTotalNewCases() {
		return totalNewCases;
	}

	public void setTotalNewCases(int totalNewCases) {
		this.totalNewCases = totalNewCases;
	}

}
