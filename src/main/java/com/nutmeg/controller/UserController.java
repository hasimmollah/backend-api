package com.nutmeg.controller;

import com.nutmeg.model.UserRequest;
import com.nutmeg.model.UserDto;
import com.nutmeg.service.UserFacade;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@Slf4j
public class UserController {
    private final UserFacade userFacade;
    public UserController(UserFacade userFacade){
        this.userFacade = userFacade;
    }
    @GetMapping(value = "/users")
    public ResponseEntity<List<UserDto>> getUsers(){
        log.trace("getUsers Started");
        List<UserDto> users = userFacade.getUsers();
        log.trace("getUsers Finished");
        return ResponseEntity.status(HttpStatus.OK)
                .body(users);
    }
    @PostMapping(value = "/users/user")
    public ResponseEntity<UserDto> createUser(@Valid  @RequestBody UserRequest userRequest){
        log.trace("createUser Started");
        UserDto userDtoOutput = userFacade.createUser(userRequest);
        log.trace("createUser Finished");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userDtoOutput);
    }
}
