package com.sukream.sukream.domains.auth.service;

import com.sukream.sukream.commons.constants.SuccessCode;
import com.sukream.sukream.domains.auth.repository.UserInfoRepository;
import com.sukream.sukream.domains.user.domain.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import net.nurigo.java_sdk.api.Message;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MessageDelegate {

    private final UserInfoRepository userInfoRepository;

    @Value("${coolsms.apikey}")
    private String apiKey;

    @Value("${coolsms.apisecret}")
    private String apiSecret;

    @Value("${coolsms.fromnumber}")
    private String fromNumber;

    private HashMap<String, String> makeParams(String to, String randomNum) {
        HashMap<String, String> params = new HashMap<>();
        params.put("from", fromNumber);
        params.put("type", "SMS");
        params.put("app_version", "test app 1.2");
        params.put("to", to);
        params.put("text", randomNum);
        return params;
    }

    public HttpStatus sendSMS(String phoneNumber) {
        Message coolsms = new Message(apiKey, apiSecret);
        Users userInfo = userInfoRepository.findUsersByPhoneNumber(phoneNumber).get();

        HashMap<String, String> params = makeParams(phoneNumber, userInfo.getEmail());

        try {
            JSONObject obj = coolsms.send(params);
            System.out.println(obj.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return HttpStatus.OK;
    }
}
