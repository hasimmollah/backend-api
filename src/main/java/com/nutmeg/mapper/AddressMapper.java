package com.nutmeg.mapper;

import com.nutmeg.entity.Address;
import com.nutmeg.entity.Email;
import com.nutmeg.model.AddressDto;
import com.nutmeg.model.EmailDto;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper  extends BaseMapper{

    @InheritConfiguration(name = "toDto")
    AddressDto toAddressDto(Address address);
    @InheritConfiguration(name = "toEntity")
    Address toAddress(AddressDto addressDto);
}
