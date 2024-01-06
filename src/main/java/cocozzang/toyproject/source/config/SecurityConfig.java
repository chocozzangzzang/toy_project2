package cocozzang.toyproject.source.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 권한은 위에서부터 실행되므로 주의해서 작성할 것

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/login", "/loginProc", "/join", "/joinProc").permitAll()
                        .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated());
        
        // 커스텀 로그인
        http
                .formLogin((auth) -> auth.loginPage("/login")
                        .loginProcessingUrl("/loginProc").permitAll().defaultSuccessUrl("/"));

        http
                .csrf((auth) -> auth.disable());

        // 다중 로그인 설정
        http
                .sessionManagement((auth) -> auth
                        .maximumSessions(1) // 최대 중복 로그인 수
                        .maxSessionsPreventsLogin(true));
                        /*
                            최대 세션을 초과하는 경우 기존 계정을 처리하는 방법
                            TRUE : 새로운 로그인을 차단
                            FALSE : 기존 로그인 중인 세션 하나를 로그아웃
                        */

        http
                .sessionManagement((auth) -> auth
                        .sessionFixation().changeSessionId());
        /*
            none : 세션 정보 변경 안함
            newSession : 세션 정보를 변경
            changeSessionId : 세션은 동일하나 id 값을 변경함
         */
        return http.build();
    }
}
