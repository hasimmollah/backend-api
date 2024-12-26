package com.nutmeg.service;

import com.nutmeg.Constants;
import com.nutmeg.exception.ApplicationDownstreamException;
import com.nutmeg.exception.ApplicationNotFoundException;
import com.nutmeg.user.contact.model.UserContactResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserContactProxyService {

	private final RestTemplate restTemplate;


	private final String endpoint;

	UserContactProxyService(@Qualifier(Constants.REST_TEMPLATE) RestTemplate restTemplate, @Value("${contact.httpRequestEndpoint}")
	 String endpoint){
		this.restTemplate = restTemplate;
		this.endpoint = endpoint;
	}
	@CircuitBreaker(name = Constants.CIRCUIT_BREAKER_USER_CONTACT_DETAILS, fallbackMethod= Constants.FALLBACK_USER_CONTACT_DETAILS)
	public UserContactResponse
	getUserContactDetails(String userId) {
		log.trace("getUserEmailDetails started");
		UserContactResponse userContactResponse = null;
		try{
			HttpHeaders headers = new HttpHeaders();
			headers.set("Accept", "application/json");

			// Create HttpEntity with the headers
			HttpEntity<String> entity = new HttpEntity<>(headers);
			final ResponseEntity<UserContactResponse> response = restTemplate.exchange(endpoint, HttpMethod.GET,entity, UserContactResponse.class, userId);
			if(HttpStatus.OK == response.getStatusCode()) {
				userContactResponse = response.getBody();
			}
		} catch (HttpClientErrorException e) {
			if (e.getRawStatusCode() == HttpStatus.NOT_FOUND.value()) {
				throw new ApplicationNotFoundException(String.format("Can't find details for the userid : %s", userId), e);
			} else {
				throw new ApplicationDownstreamException(String.format("Error occurred while processing %s ", endpoint), e);
			}
		}  catch (Exception e) {
			throw new ApplicationDownstreamException(String.format("Error occurred while connecting %s ", endpoint), e);
		}

		log.trace("getUserEmailDetails finished");
		return userContactResponse;
	}

	public UserContactResponse fallbackGetUserEmailDetails(String userId, Throwable t) {
		log.error(String.format("Error while calling downstream api for user id %s",userId),t);
		return null;
	}

}
