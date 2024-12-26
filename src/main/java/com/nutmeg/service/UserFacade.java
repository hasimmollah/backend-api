package com.nutmeg.service;

import java.util.List;
import java.util.Optional;

import com.nutmeg.mapper.UserRequestMapper;
import com.nutmeg.model.AddressDto;
import com.nutmeg.model.EmailDto;
import com.nutmeg.model.UserRequest;
import com.nutmeg.model.UserDto;
import com.nutmeg.user.contact.model.UserContactResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserFacade {

	private final UserService userService;
	private final UserContactProxyService userContactProxyService;

	private final UserRequestMapper userRequestMapper;


	public UserFacade(UserService userService, UserContactProxyService userContactProxyService, UserRequestMapper userRequestMapper){
		this.userService = userService;
		this.userContactProxyService = userContactProxyService;
		this.userRequestMapper = userRequestMapper;
	}

	
	public List<UserDto> getUsers() {
		log.trace("getUsers started");
		List<UserDto> userDtos = userService.fetchUsers();
		log.trace("getUsers finished");
		return userDtos;
	}

	public UserDto createUser(UserRequest userRequest) {
		log.trace("createUser started");
		UserContactResponse userContactResponse = userContactProxyService.getUserContactDetails(userRequest.getUserId());
		UserDto userDto = userRequestMapper.toDto(userRequest);
		Optional.ofNullable(userContactResponse).ifPresent(response ->{

			userDto.setEmails(Optional.ofNullable(response.getEmails()).map(emails-> emails.stream().map(email->new EmailDto(email, userDto)).toList())
					.orElse(null));
			userDto.setAddresses(Optional.ofNullable(response.getAddresses()).map(addresses-> addresses.stream().map(address->new AddressDto(address.getFirstLine(), address.getSecondLine(), address.getPostcode(), List.of(userDto))).toList())
					.orElse(null));

		});
		UserDto createdUser = userService.createUser(userDto);
		log.trace("createUser finished");
		return createdUser;
	}


}
