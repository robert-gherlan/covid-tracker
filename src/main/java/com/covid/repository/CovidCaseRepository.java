package com.covid.repository;

import java.io.IOException;
import java.io.StringReader;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import com.covid.exception.DataNotFoundException;
import com.covid.mapper.LocationStatsMapperFactory;
import com.covid.model.DailyReportsLocationStats;
import com.covid.model.LocationStats;

@Repository
public class CovidCaseRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(CovidCaseRepository.class);

	private static final String CONFIRMED_CASES_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";

	private static final String RECOVERED_CASES_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Recovered.csv";

	private static final String DEATHS_CASES_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Deaths.csv";

	private static final String DAILY_REPORST_CASES_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports/{0}.csv";

	private static final AtomicReference<List<LocationStats>> CONFIRMED_CASES_STATS = new AtomicReference<>();

	private static final AtomicReference<List<LocationStats>> RECOVERED_CASES_STATS = new AtomicReference<>();

	private static final AtomicReference<List<LocationStats>> DEATHS_CASES_STATS = new AtomicReference<>();

	private static final AtomicReference<List<DailyReportsLocationStats>> DAILY_REPORTS_CASES_STATS = new AtomicReference<>();

	private final URLRepository urlRepository;

	public CovidCaseRepository(URLRepository urlRepository) {
		this.urlRepository = urlRepository;
	}

	public List<LocationStats> getConfirmedCasesStats() {
		return CONFIRMED_CASES_STATS.get();
	}

	public List<LocationStats> getRecoveredCasesStats() {
		return RECOVERED_CASES_STATS.get();
	}

	public List<LocationStats> getDeathCasesStats() {
		return DEATHS_CASES_STATS.get();
	}

	public List<DailyReportsLocationStats> getDailyReportsCasesStats() {
		return DAILY_REPORTS_CASES_STATS.get();
	}

	@Scheduled(fixedDelayString = "PT4H")
	private void populateConfirmedCasesStats() {
		LOGGER.info("Start loading confirmed cases stats at {}", LocalTime.now());
		List<LocationStats> data = requestData(CONFIRMED_CASES_URL);
		CONFIRMED_CASES_STATS.set(data);
		LOGGER.info("End loading {} confirmed cases stats at {}", data.size(), LocalTime.now());
	}

	@Scheduled(fixedDelayString = "PT4H")
	private void populateRecoveredCasesStats() {
		LOGGER.info("Start loading recovered cases stats at {}", LocalTime.now());
		List<LocationStats> data = requestData(RECOVERED_CASES_URL);
		RECOVERED_CASES_STATS.set(data);
		LOGGER.info("End loading {} recovered cases stats at {}", data.size(), LocalTime.now());
	}

	@Scheduled(fixedDelayString = "PT4H")
	private void populateDeathsCasesStats() {
		LOGGER.info("Start loading death cases stats at {}", LocalTime.now());
		List<LocationStats> data = requestData(DEATHS_CASES_URL);
		DEATHS_CASES_STATS.set(data);
		LOGGER.info("End loading {} death cases stats at {}", data.size(), LocalTime.now());
	}

	@Scheduled(fixedDelayString = "PT8H")
	private void populateDailyReportsCasesStats() {
		LOGGER.info("Start loading daily reports cases stats at {}", LocalTime.now());
		List<DailyReportsLocationStats> data = requestDailyReportsData(DAILY_REPORST_CASES_URL);
		DAILY_REPORTS_CASES_STATS.set(data);
		LOGGER.info("End loading {} daily reports cases stats at {}", data.size(), LocalTime.now());
	}

	private List<DailyReportsLocationStats> requestDailyReportsData(String url) {
		Optional<String> requestedDataForCurrentDate = requestDailyReports(url);
		return requestedDataForCurrentDate.map(this::mapToDailyReportsCovidCaseStatsList)
				.orElseThrow(DataNotFoundException::new);
	}

	private Optional<String> requestDailyReports(String url) {
		int numberOfRetries = 5;
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		LocalDate localDate = LocalDate.now();
		String currentDate = dateFormatter.format(localDate);
		Optional<String> requestedDataForCurrentDate = urlRepository
				.requestData(MessageFormat.format(url, currentDate));
		int count = 1;
		while (!requestedDataForCurrentDate.isPresent() || count > numberOfRetries) {
			String yesterdayDate = dateFormatter.format(localDate.minusDays(count));
			requestedDataForCurrentDate = urlRepository.requestData(MessageFormat.format(url, yesterdayDate));
			count++;
		}
		return requestedDataForCurrentDate;
	}

	private List<LocationStats> requestData(String url) {
		return urlRepository.requestData(url).map(this::mapToCovidCaseStatsList)
				.orElseThrow(DataNotFoundException::new);
	}

	private List<LocationStats> mapToCovidCaseStatsList(String data) {
		return applyMapperOnData(data, LocationStatsMapperFactory::mapToLocationStats);
	}

	private List<DailyReportsLocationStats> mapToDailyReportsCovidCaseStatsList(String data) {
		return applyMapperOnData(data, LocationStatsMapperFactory::mapToDailyReportsLocationStats);
	}

	private <R> List<R> applyMapperOnData(String data, Function<CSVRecord, Optional<R>> mapper) {
		try {
			StringReader csvBodyReader = new StringReader(data);
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
			return StreamSupport.stream(records.spliterator(), false).map(mapper).filter(Optional::isPresent)
					.map(Optional::get).collect(Collectors.toList());
		} catch (IOException e) {
			LOGGER.error("Failed to map the extracted data.", e);
		}

		return Collections.emptyList();
	}
}
