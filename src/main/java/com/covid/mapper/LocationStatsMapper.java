package com.covid.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.covid.dto.DailyReportsLocationStatsDto;
import com.covid.model.DailyReportsLocationStats;

public class LocationStatsMapper {

	private LocationStatsMapper() {
	}

	public static List<DailyReportsLocationStatsDto> mapToDto(List<DailyReportsLocationStats> list) {
		return list.stream().map(DailyReportsLocationStatsDto::new).collect(Collectors.toList());
	}
}
