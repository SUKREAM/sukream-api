package com.sukream.sukream.domains.user.mapper;

import com.sukream.sukream.commons.mapper.EntityMapper;
import com.sukream.sukream.domains.user.domain.entity.Users;
import com.sukream.sukream.domains.user.domain.request.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserRequestMapper extends EntityMapper<UserRequest, Users> {
    UserRequestMapper INSTANCE = Mappers.getMapper(UserRequestMapper.class);
}

