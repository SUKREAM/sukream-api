package com.sukream.sukream.domains.auth.mapper;

import com.sukream.sukream.commons.mapper.EntityMapper;
import com.sukream.sukream.domains.auth.domain.request.SignInRequest;
import com.sukream.sukream.domains.user.domain.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SignInMapper extends EntityMapper<SignInRequest, Users> {
    SignInMapper INSTANCE = Mappers.getMapper(SignInMapper.class);
}

