package com.nutmeg.mapper;

import com.nutmeg.model.UserRequest;
import com.nutmeg.model.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRequestMapper {
    UserDto toDto(UserRequest userRequest);
}
