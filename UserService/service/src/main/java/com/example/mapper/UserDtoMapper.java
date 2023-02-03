package com.example.mapper;

import com.example.entity.User;
import com.example.model.UserBasic;
import com.example.model.UserDto;
import com.example.model.UserDtoBasic;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR,
unmappedSourcePolicy = ReportingPolicy.WARN, componentModel = "spring")
public interface UserDtoMapper {
    //@Mapping(target = "name", source = "name", qualifiedByName = "mapName")
    User userDtoToUser(UserDto userDto);
    UserDto userToUserDto(User user);

    User userBasicToUser(UserBasic userBasic);

    UserDtoBasic userToUserDtoBasic(User user);

    UserBasic userToUserBasic(User user);

   /* UserBasicCustom userToUserBasicCustom(User user);
    User userBasicCustomToUser (UserBasicCustom userBasicCustom);*/

    /*@Named("mapName")
    default String mapName(String name) {
        return name+"something";
    }*/
}
