package com.covid.mapper;

import java.util.Optional;

import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.covid.model.DailyReportsLocationStats;
import com.covid.model.LocationStats;

class CSVLocationStatsMapper {

	private static final Logger LOGGER = LoggerFactory.getLogger(CSVLocationStatsMapper.class);

	private CSVLocationStatsMapper() {
	}

	static Optional<LocationStats> mapToLocationStats(CSVRecord record) {
		try {
			LocationStats locationStats = new LocationStats();
			String state = record.get("Province/State");
			locationStats.setState(state);
			String country = record.get("Country/Region");
			locationStats.setCountry(country);
			int latestCases = Integer.parseInt(numberOfLatestCases(record));
			String numberOfLastDayCases = record.get(record.size() - 2);
			int previousDayCases = Integer.parseInt(numberOfLastDayCases);
			locationStats.setLatestTotalCases(latestCases);
			locationStats.setDifferenceFromPreviousDay(latestCases - previousDayCases);
			locationStats.setProperties(record.toMap());
			return Optional.of(locationStats);
		} catch (NumberFormatException e) {
			LOGGER.error("Failed to extract the location stats.", e);
		}

		return Optional.empty();
	}

	private static String numberOfLatestCases(CSVRecord record) {
		return record.get(record.size() - 1);
	}

	public static Optional<DailyReportsLocationStats> mapToDailyReportsLocationStats(CSVRecord record) {
		try {
			DailyReportsLocationStats dailyReportsLocationStats = new DailyReportsLocationStats();
			String state = record.get("Province/State");
			dailyReportsLocationStats.setState(state);
			String country = record.get("Country/Region");
			dailyReportsLocationStats.setCountry(country);
			String lastUpdate = record.get("Last Update");
			dailyReportsLocationStats.setLastUpdate(lastUpdate);
			int confirmed = Integer.parseInt(record.get("Confirmed"));
			dailyReportsLocationStats.setConfirmed(confirmed);
			int deaths = Integer.parseInt(record.get("Deaths"));
			dailyReportsLocationStats.setDeaths(deaths);
			int recovered = Integer.parseInt(record.get("Recovered"));
			dailyReportsLocationStats.setRecovered(recovered);
			float latitude = Float.parseFloat(record.get("Latitude"));
			dailyReportsLocationStats.setLatitude(latitude);
			float longitude = Float.parseFloat(record.get("Longitude"));
			dailyReportsLocationStats.setLongitude(longitude);
			return Optional.of(dailyReportsLocationStats);
		} catch (NumberFormatException e) {
			LOGGER.error("Failed to extract the location stats.", e);
		}

		return Optional.empty();
	}
}
