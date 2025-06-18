package com.sukream.sukream.domains.user.service;

import com.sukream.sukream.domains.auth.repository.UserInfoRepository;
import com.sukream.sukream.domains.user.domain.entity.Users;
import com.sukream.sukream.domains.user.domain.request.UserRequest;
import com.sukream.sukream.domains.user.domain.response.UserResponse;
import com.sukream.sukream.domains.user.mapper.UserRequestMapper;
import com.sukream.sukream.domains.user.mapper.UserResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRequestMapper userRequestMapper;
    private final UserResponseMapper userResponseMapper;

    public UserResponse myPage(UserRequest userRequest){
        Users userInfo = userRequestMapper.toEntity(userRequest);
        userInfo.updateUserInfo(userRequest);

        return userResponseMapper.toDto(userInfo);
    }
}
