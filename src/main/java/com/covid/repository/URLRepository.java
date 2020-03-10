package com.covid.repository;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class URLRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(URLRepository.class);

	protected Optional<String> requestData(String url) {
		try {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder(URI.create(url)).build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			if (response.statusCode() == 200) {
				return Optional.ofNullable(response.body());
			}
		} catch (Exception e) {
			LOGGER.error("Failed to retrieve the data.", e);
		}

		return Optional.empty();
	}
}
