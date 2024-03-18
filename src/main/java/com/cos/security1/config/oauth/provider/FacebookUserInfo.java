package com.cos.security1.config.oauth.provider;

import java.util.Map;
import java.util.Objects;

public class FacebookUserInfo implements OAuth2UserInfo{

    private Map<String, Object> attributes; // getAttributes를 받음

    public FacebookUserInfo(Map<String, Object> attributes){
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
        return "facebook";
    }

    @Override
    public String getName() {
        return String.valueOf(attributes.get("name"));
    }
}
