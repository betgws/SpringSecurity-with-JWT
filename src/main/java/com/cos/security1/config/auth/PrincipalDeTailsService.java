package com.cos.security1.config.auth;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


//시큐리티 설정에서 loginProcessingUrl("/login")으로 되어서
// login 요청이 오면 자동으로 UserDetailsService 타입으로 되어있는 lodaUSer함수가 실행
@Service
public class PrincipalDeTailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // 시큐리티 session(내부 Authentication(내부 UserDetails))
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("username:" + username);
        User userEntity = userRepository.findByUsername(username);
        if(username != null){
            return new PrincipalDetails(userEntity);
        }

        return null;
    }
}
