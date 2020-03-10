package com.covid.dto;

import java.util.List;

import com.covid.mapper.LocationStatsMapper;
import com.covid.model.DailyReportsLocationStats;

public class DailyReportsCovidCaseStatsDto {

	private List<DailyReportsLocationStatsDto> dailyReportsLocationStats;

	private int totalConfirmed;

	private int totalRecovered;

	private int totalDeaths;

	public DailyReportsCovidCaseStatsDto(List<DailyReportsLocationStats> covidCaseStats) {
		this.dailyReportsLocationStats = LocationStatsMapper.mapToDto(covidCaseStats);
		this.totalConfirmed = covidCaseStats.stream().mapToInt(DailyReportsLocationStats::getConfirmed).sum();
		this.totalRecovered = covidCaseStats.stream().mapToInt(DailyReportsLocationStats::getRecovered).sum();
		this.totalDeaths = covidCaseStats.stream().mapToInt(DailyReportsLocationStats::getDeaths).sum();
	}

	public List<DailyReportsLocationStatsDto> getDailyReportsLocationStats() {
		return dailyReportsLocationStats;
	}

	public void setDailyReportsLocationStats(List<DailyReportsLocationStatsDto> dailyReportsLocationStats) {
		this.dailyReportsLocationStats = dailyReportsLocationStats;
	}

	public int getTotalConfirmed() {
		return totalConfirmed;
	}

	public void setTotalConfirmed(int totalConfirmed) {
		this.totalConfirmed = totalConfirmed;
	}

	public int getTotalRecovered() {
		return totalRecovered;
	}

	public void setTotalRecovered(int totalRecovered) {
		this.totalRecovered = totalRecovered;
	}

	public int getTotalDeaths() {
		return totalDeaths;
	}

	public void setTotalDeaths(int totalDeaths) {
		this.totalDeaths = totalDeaths;
	}

}
