package com.nutmeg.mapper;

import com.nutmeg.entity.User;
import com.nutmeg.model.UserDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring", uses = {EmailMapper.class, AddressMapper.class})
public interface UserMapper extends BaseMapper{
    @InheritConfiguration(name = "toDto")
    @Mapping(target = "emails", source = "emails")
    @Mapping(target = "addresses", source = "addresses")
    UserDto toDto(User entity);

    @InheritConfiguration(name = "toEntity")
    @Mapping(target = "emails", source = "emails")
    @Mapping(target = "addresses", source = "addresses")
    User toEntity(UserDto dto);

    @AfterMapping
    default void afterMapping(User user, @MappingTarget UserDto userDto) {
        userDto.getEmails().forEach(email->email.setUserDto(userDto));
        userDto.getAddresses().forEach(addressDto -> addressDto.getUserDtos().add(userDto));
    }

    @AfterMapping
    default void afterMapping( UserDto userDto, @MappingTarget User user) {
        user.getEmails().forEach(email->email.setUser(user));
        user.getAddresses().forEach(address -> address.getUsers().add(user));
    }
}
