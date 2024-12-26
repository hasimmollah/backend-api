package com.nutmeg.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.nutmeg.Constants;
import com.nutmeg.entity.User;
import com.nutmeg.exception.ApplicationSQLException;
import com.nutmeg.mapper.UserMapper;
import com.nutmeg.model.UserDto;
import com.nutmeg.repositories.UserRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;



@Service
@Slf4j
public class UserService {


	private final UserRepository userRepository;


	private final UserMapper userMapper;
	
	public UserService(UserRepository userRepository, UserMapper userMapper){
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}

	@CircuitBreaker(name = Constants.CIRCUIT_BREAKER_FETCH_USERS, fallbackMethod= Constants.FALLBACK_FETCH_USERS)
	@Transactional(readOnly = true)
	public List<UserDto> fetchUsers() {
		log.trace("getUsers started");
		List<User> users = userRepository.findAll();
		List<UserDto> userDtos = users.stream().map(userMapper::toDto).collect(Collectors.toList());
		log.trace("getUsers finished");
		return userDtos;
	}

	public List<UserDto> fallbackFetchUsers(Throwable t) {
		throw new ApplicationSQLException("Error while getting users",t);

	}

	@CircuitBreaker(name = Constants.CIRCUIT_BREAKER_CRTEATE_USER, fallbackMethod= Constants.FALLBACK_CRTEATE_USER)
	@Transactional
	public UserDto createUser(UserDto userDto) {
		log.trace("createUser started");
		User user = userRepository.saveAndFlush(userMapper.toEntity(userDto));
		UserDto createdUser = Optional.ofNullable(user).map(userMapper::toDto).get();
		log.trace("createUser finished");
		return createdUser;
	}
	public UserDto fallbackCreateUser(UserDto userDto, String userEmail, Throwable t) {
		throw new ApplicationSQLException("Error while creating user",t);

	}

}
