package aiversum.backend.rest.mapper;

import aiversum.backend.model.UserConfig;
import aiversum.backend.rest.dto.UserConfigDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserConfigMapper {
    UserConfigDto toDto(UserConfig userConfig);
    UserConfig fromDto(UserConfigDto userConfigDto);
}
