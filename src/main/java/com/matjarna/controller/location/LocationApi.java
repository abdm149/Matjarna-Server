package com.matjarna.controller.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import com.matjarna.dto.location.LocationDto;
import com.matjarna.constants.Constants;

@RestController
@RequestMapping(value = "api")
public class LocationApi {

	private static final String IP_ADDRESS = "82.213.0.223";
	
	private static final String LOCAL_IPV6 = "0:0:0:0:0:0:0:1";
	
	private static final String LOCAL_IPV4 = "127.0.0.1";
	
	private static final String IP_API_URL = "http://ip-api.com/json/%s";
	
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private ObjectMapper objectMapper;


	@GetMapping(value = "/countryCode")
	public ResponseEntity<String> getCountryCode() throws JsonMappingException, JsonProcessingException {
		String ipAddress = request.getHeader("X-Forwarded-For");
		if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
		}
		ipAddress = sanitizeAddress(ipAddress);
		RestTemplate restTemplate = new RestTemplate();
		String url = String.format(IP_API_URL, ipAddress);
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		String countryCode = "US";
		if (response.getStatusCode().is2xxSuccessful()) {
			LocationDto locationDto = objectMapper.readValue(response.getBody(), LocationDto.class);
			countryCode = locationDto.getCountryCode();
		}
		return ResponseEntity.ok(countryCode);
	}

	private String sanitizeAddress(String ipAddress) {
		// IP address might contain multiple IP addresses separated by a comma,
		// we can split and return the first one (which is typically the client's IP).
		if (ipAddress.contains(",")) {
			ipAddress = ipAddress.split(",")[0].trim();
		}
		if (ipAddress.equals(LOCAL_IPV6) || ipAddress.equals(LOCAL_IPV4)) {
			return IP_ADDRESS;
		} else {
			return ipAddress;
		}

	}

}
