package com.covid.dto;

import java.util.Map;

import com.covid.model.LocationStats;

public class LocationStatsDto {

	private String state;

	private String country;

	private int latestTotalCases;

	private int differenceFromPreviousDay;

	private Map<String, String> properties;

	public LocationStatsDto(LocationStats locationStats) {
		this.state = locationStats.getState();
		this.country = locationStats.getCountry();
		this.latestTotalCases = locationStats.getLatestTotalCases();
		this.differenceFromPreviousDay = locationStats.getDifferenceFromPreviousDay();
		this.properties = locationStats.getProperties();
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getLatestTotalCases() {
		return latestTotalCases;
	}

	public void setLatestTotalCases(int latestTotalCases) {
		this.latestTotalCases = latestTotalCases;
	}

	public int getDifferenceFromPreviousDay() {
		return differenceFromPreviousDay;
	}

	public void setDifferenceFromPreviousDay(int differenceFromPreviousDay) {
		this.differenceFromPreviousDay = differenceFromPreviousDay;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

}
