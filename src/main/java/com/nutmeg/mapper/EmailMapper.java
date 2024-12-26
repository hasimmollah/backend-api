package com.nutmeg.mapper;

import com.nutmeg.entity.Email;
import com.nutmeg.model.EmailDto;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmailMapper  extends BaseMapper{

    @InheritConfiguration(name = "toDto")
    EmailDto toEmailDto(Email email);

    @InheritConfiguration(name = "toEntity")
    Email toEmail(EmailDto emailDto);
}
