package com.nutmeg;

import com.nutmeg.mapper.UserRequestMapper;
import com.nutmeg.model.UserRequest;
import com.nutmeg.model.UserDto;
import com.nutmeg.service.UserContactProxyService;
import com.nutmeg.service.UserFacade;
import com.nutmeg.service.UserService;
import com.nutmeg.user.contact.model.UserAddress;
import com.nutmeg.user.contact.model.UserContactResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserFacadeTest {
    UserFacade userFacade;

    UserService userService;

    UserContactProxyService userContactProxyService;

    UserRequestMapper userRequestMapper;

    @BeforeEach
    void setup(){
        userContactProxyService = Mockito.mock(UserContactProxyService.class);
        userService = Mockito.mock(UserService.class);
        userRequestMapper = Mockito.mock(UserRequestMapper.class);
        userFacade = new UserFacade(userService, userContactProxyService, userRequestMapper);
    }
    @Test
    void shouldVerifyCreateUser(){
        String name = "testName";
        List<String> emails = List.of("test@abc.com");
        String userId = "testUserId";
        UserRequest userRequest = new UserRequest();
        userRequest.setName(name);
        userRequest.setUserId(userId);

        UserDto expectedUserDto = new UserDto();
        expectedUserDto.setName(name);
        expectedUserDto.setUserId(userId);
        UserAddress address = UserAddress.builder()
                .firstLine("firstLine")
                .secondLine("secondLine")
                .postcode("postcode")
                .build();
        UserContactResponse userContactResponse = new UserContactResponse(emails, userId, List.of(address));
        Mockito.when(userContactProxyService.getUserContactDetails(userRequest.getUserId())).thenReturn(userContactResponse);
        Mockito.when(userRequestMapper.toDto(userRequest)).thenReturn(expectedUserDto);
        Mockito.when(userService.createUser(expectedUserDto)).thenReturn(expectedUserDto);
        UserDto userDto = userFacade.createUser(userRequest);
        assertEquals(expectedUserDto, userDto);

    }
}
