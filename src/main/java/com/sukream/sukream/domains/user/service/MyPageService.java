package com.sukream.sukream.domains.user.service;

import com.sukream.sukream.domains.auth.repository.UserInfoRepository;
import com.sukream.sukream.domains.auth.security.LoginManager;
import com.sukream.sukream.domains.auth.service.AuthDelegate;
import com.sukream.sukream.domains.user.domain.entity.Users;
import com.sukream.sukream.domains.user.domain.request.UserRequest;
import com.sukream.sukream.domains.user.domain.response.UserResponse;
import com.sukream.sukream.domains.user.mapper.UserRequestMapper;
import com.sukream.sukream.domains.user.mapper.UserResponseMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserInfoRepository userInfoRepository;
    private final UserResponseMapper userResponseMapper;
    private final AuthDelegate authDelegate;

    @Transactional
    public UserResponse myPage(UserRequest userRequest){
        Users userInfo = userInfoRepository.findById(Objects.requireNonNull(LoginManager.getUserDetails()).getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRequest.setPassword((authDelegate.passwordEncoding(userRequest.getPassword())));

        userInfo.updateUserInfo(userRequest);

        return userResponseMapper.toDto(userInfo);
    }

    public UserResponse getUserInfo(){
        Users userInfo = userInfoRepository.findById(Objects.requireNonNull(LoginManager.getUserDetails()).getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userResponseMapper.toDto(userInfo);
    }
}
