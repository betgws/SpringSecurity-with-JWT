package com.cos.security1.config.auth;


//시큐리티가 /login으로 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
//로그인을 진행이 완료가 되면 시큐리티 ssesion을 만들어줍니다. (Security ContextHolder)
//오브젝트 타입 => Authentication 타입 객체
//Authentication 안에 User정보가 있어야됨
//User 오브젝트 타입 => UserDetails 타입 객체

import com.cos.security1.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

// Security Session => Authentication => UserDetails(PrincipalDetails)

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private User user;

    public PrincipalDetails(User user){
        this.user = user;
   }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public String getUsername() {
        return user.getUsername() ;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    //해당 user의 권한을 return 하는 곳!
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    @Override
    public boolean isEnabled() {

        //우리 사이트에서 1년동안 회원이 로그인을 안하면 휴먼 계정으로 하기로 함
        //user.getLoginDate();로 가져와서 현재 시간 = 로긴 시간 => 1년 초과시 false되게
        return true;
    }

    @Override
    public <A> A getAttribute(String name) {
        return OAuth2User.super.getAttribute(name);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }
}
