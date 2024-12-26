package com.nutmeg.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutmeg.model.AddressDto;
import com.nutmeg.model.EmailDto;
import com.nutmeg.model.UserRequest;
import com.nutmeg.model.UserDto;
import com.nutmeg.user.contact.model.UserAddress;
import com.nutmeg.user.contact.model.UserContactResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserFacadeIT {

    @Autowired
    private UserFacade userFacade;

    private static ClientAndServer mockServer;

    @BeforeAll
    static void startMockServer() {
        mockServer = ClientAndServer.startClientAndServer(8686); // Start MockServer on port 1080
    }

    @AfterAll
    static void stopMockServer() {
        mockServer.stop(); // Stop MockServer after tests
    }

    @Test
    void shouldVerifyFetchUser() {
        String userName = "test";
        String userId = "testUserId";
        String email = "test@abc.com";
        String firstLine = "testfirstLine";
        String secondLine = "testsecondLine";
        String postcode = "testpostcode";
        List<String> emails = List.of(email);
        UserRequest user = new UserRequest();
        user.setName(userName);
        user.setUserId(userId);
        UserAddress address = UserAddress.builder()
                .firstLine(firstLine)
                .secondLine(secondLine)
                .postcode(postcode)
                .build();
        UserContactResponse userContactResponse = new UserContactResponse(emails, userId, List.of(address));
        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = null;
        try {
           responseBody = objectMapper.writeValueAsString(userContactResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        mockServer.when(
                request()
                        .withMethod("GET")
                        .withPath("/user/"+userId)
        ).respond(
                response()
                        .withStatusCode(200)
                        .withBody(responseBody)
                        .withHeader("Content-Type", "application/json")
        );
        userFacade.createUser(user);

        List<UserDto> users = userFacade.getUsers();
        assertThat(users).hasSize(1);
        assertThat(users)
                .extracting(UserDto::getName)
                .containsExactly(userName);
        assertThat(users).extracting(UserDto::getEmails).flatExtracting(emailVO -> emailVO.stream().map(EmailDto::getEmail).toList())
                .contains(email);
        assertThat(users).extracting(UserDto::getAddresses).flatExtracting(addressVO -> addressVO.stream().map(AddressDto::getFirstLine).toList())
                .contains(firstLine);
    }
}
