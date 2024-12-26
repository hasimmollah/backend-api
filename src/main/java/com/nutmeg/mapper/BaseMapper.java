package com.nutmeg.mapper;


import com.nutmeg.entity.BaseEntity;
import com.nutmeg.model.BaseDto;
import org.mapstruct.Mapper;


/**
 * @author Hasim Mollah
 */
@Mapper(componentModel = "spring")
public interface BaseMapper {

    BaseDto toDto(BaseEntity entity);

    BaseEntity toEntity(BaseDto dto);
}
