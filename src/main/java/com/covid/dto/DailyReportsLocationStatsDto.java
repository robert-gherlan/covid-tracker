package com.covid.dto;

import com.covid.model.DailyReportsLocationStats;

public class DailyReportsLocationStatsDto {

	private String state;

	private String country;

	private String lastUpdate;

	private int confirmed;

	private int recovered;

	private int deaths;

	private float latitude;

	private float longitude;

	public DailyReportsLocationStatsDto(DailyReportsLocationStats dailyReportsLocationStats) {
		this.state = dailyReportsLocationStats.getState();
		this.country = dailyReportsLocationStats.getCountry();
		this.lastUpdate = dailyReportsLocationStats.getLastUpdate();
		this.confirmed = dailyReportsLocationStats.getConfirmed();
		this.recovered = dailyReportsLocationStats.getRecovered();
		this.deaths = dailyReportsLocationStats.getDeaths();
		this.latitude = dailyReportsLocationStats.getLatitude();
		this.longitude = dailyReportsLocationStats.getLongitude();
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

	public String getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public int getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(int confirmed) {
		this.confirmed = confirmed;
	}

	public int getRecovered() {
		return recovered;
	}

	public void setRecovered(int recovered) {
		this.recovered = recovered;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

}
