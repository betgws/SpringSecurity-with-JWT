package com.cos.security1.config;


import com.cos.security1.config.oauth.PrincipalOauthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;

@Configuration
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터체인에 등록됨
@EnableGlobalMethodSecurity(securedEnabled = true ,prePostEnabled = true) //secured 어노테이션 활성화 preAutho 머시기 활성화
public class SecurityConfig  {

    @Autowired
    private PrincipalOauthUserService principalOauthUserService;


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    //@Bean//해당  메서드의 리턴되는 오브젝트를 IOC로 등록해줌
    //public BCryptPasswordEncoder encodePwd(){
    //    return new BCryptPasswordEncoder();
    //}

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(CsrfConfigurer::disable);
        http.authorizeHttpRequests(authorize ->
                authorize
                        .requestMatchers("/user/**").authenticated()
                        .requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN")

                        .anyRequest().permitAll()

        );

            http.oauth2Login(oauth2-> oauth2.loginPage("/loginForm")
                    .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint.userService(principalOauthUserService) ));
        // 구글 로그인이 완료된 후 후처리가 필요함
        // tip oauth 외부 라이브러리를 쓰면 코드를 받는게 아니라 바로 엑세스 토큰+사용자프로필 정보를 받음
        // 1. 코드 받기(인증O), 2. 엑세스토큰(권한),
        //3.사용자 프로필 정보 가겨오기 4. 그정보를 토대로 바로 회원가입 하기도함
        // 4-2 (이메일,전화번호,이름,아이디) 쇼핑몰 -> (집주소),백화점몰 -> (등급) 등 추가적인 정보 필요



        http.formLogin(formLogin->formLogin
                .loginPage("/loginForm")
                .loginProcessingUrl("/login") //login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행함
                .defaultSuccessUrl("/")
        );


        return http.build();
    }
}
