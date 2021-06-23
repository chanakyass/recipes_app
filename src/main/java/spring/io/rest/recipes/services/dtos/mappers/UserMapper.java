package spring.io.rest.recipes.services.dtos.mappers;

import org.mapstruct.Mapper;
import spring.io.rest.recipes.models.entities.User;
import spring.io.rest.recipes.services.dtos.UserProxyDto;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    public abstract User toUser(UserProxyDto userProxyDto);
}
