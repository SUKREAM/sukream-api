package com.sukream.sukream.domains.auth.mapper;

import com.sukream.sukream.domains.auth.domain.request.SignInRequest;
import com.sukream.sukream.domains.user.domain.entity.Users;
import com.sukream.sukream.domains.user.domain.entity.Users.UsersBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-18T20:14:28+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.13.jar, environment: Java 17.0.12 (Amazon.com Inc.)"
)
@Component
public class SignInMapperImpl implements SignInMapper {

    @Override
    public Users toEntity(SignInRequest dto) {
        if ( dto == null ) {
            return null;
        }

        UsersBuilder users = Users.builder();

        users.name( dto.getName() );
        users.email( dto.getEmail() );
        users.phoneNumber( dto.getPhoneNumber() );
        users.password( dto.getPassword() );
        users.profileImage( dto.getProfileImage() );

        return users.build();
    }

    @Override
    public SignInRequest toDto(Users entity) {
        if ( entity == null ) {
            return null;
        }

        SignInRequest signInRequest = new SignInRequest();

        signInRequest.setName( entity.getName() );
        signInRequest.setEmail( entity.getEmail() );
        signInRequest.setPhoneNumber( entity.getPhoneNumber() );
        signInRequest.setPassword( entity.getPassword() );
        signInRequest.setProfileImage( entity.getProfileImage() );

        return signInRequest;
    }

    @Override
    public List<Users> toEntity(List<SignInRequest> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Users> list = new ArrayList<Users>( dtoList.size() );
        for ( SignInRequest signInRequest : dtoList ) {
            list.add( toEntity( signInRequest ) );
        }

        return list;
    }

    @Override
    public List<SignInRequest> toDto(List<Users> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<SignInRequest> list = new ArrayList<SignInRequest>( entityList.size() );
        for ( Users users : entityList ) {
            list.add( toDto( users ) );
        }

        return list;
    }
}
