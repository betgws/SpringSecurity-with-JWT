package com.cos.security1.config.oauth.provider;

import java.util.Map;



public class NaverUserInfo implements OAuth2UserInfo{

    //response={id=04I1cZWnW7uJg3rjL3D_15UAoUvdJ7FT1WHTxOWrSqU, email=55azaz@naver.com, name=윤익중}
    private Map<String, Object> attributes; // getAttributes를 받음

    public NaverUserInfo(Map<String, Object> attributes){
        this.attributes = attributes;
    }
    @Override
    public String getEmail() {
        return String.valueOf(attributes.get("email"));
    }

    @Override
    public String getProviderId() {
        return  String.valueOf(attributes.get("id"));
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getName() {
        return String.valueOf(attributes.get("name"));
    }
}
