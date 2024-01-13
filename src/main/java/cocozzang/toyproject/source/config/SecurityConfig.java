package cocozzang.toyproject.source.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
        Role Hierarchy :
        A,B,C 권한이 있을 때 A < B < C 와 같은 권한의 계층을 설정하고자 하는 경우 사용

    @Bean
    public RoleHierarchy roleHierarchy() {

        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();

        hierarchy.setHierarchy("ROLE_C > ROLE_B\n" +
                "ROLE_B > ROLE_A");

        return  hierarchy;
    }

    -- 권한이 A<B<C 이므로 --
    hasAnyRole("A")는 A보다 권한이 높은 유저에 대해 허용, --> A, B, C 허용
    hasAnyRole("B")는 B보다 권한이 높은 유저에 대해 허용, --> B, C 허용
    hasAnyRole("C")는 C보다 권한이 높은 유저에 대해 허용, --> C 만 허용
    -- 다음과 같이 계층 권한을 적용할 수 있다. --

     -> http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/login").permitAll()
                .requestMatchers("/").hasAnyRole("A")
                .requestMatchers("/manager").hasAnyRole("B")
                .requestMatchers("/admin").hasAnyRole("C")
                .anyRequest().authenticated();
     */

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 권한은 위에서부터 실행되므로 주의해서 작성할 것

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/login", "/loginProc", "/join", "/joinProc").permitAll()
                        .requestMatchers("/board/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated());
        
        // 커스텀 로그인 - formLogin 방식
        http
                   .formLogin((auth) -> auth.loginPage("/login")
                        .loginProcessingUrl("/loginProc").permitAll().defaultSuccessUrl("/"));

        // Http Basic 인증 방식 -- 아이디와 비밀번호를 Base64 방식으로 인토딩하여
        // HTTP인증 헤더 뒤에 부착하여 서버측으로 요청을 보내는 방식
        // 마이크로서버 ( 유레카 서버나 콘티그 서버 - private이지만 더 엄격한 인증을 요구하는 경우 )
        //http
        //        .httpBasic(Customizer.withDefaults());

        // csrf - enable : 추가적인 설정을 해야함 (GET 이외의 요청에 대한 설정이 필요 - 토큰 검증)
        //http
        //        .csrf((auth) -> auth.disable());

        /*
            cf. API 서버에서는 보통 세션을 STATELESS로 관리함
             ==> 시큐리티 csrf를 enable상태로 두지 않아도 됨
         */

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

    /*
        In-Memory 방식의 회원 관리
        기존에 등록된 회원들만 로그인이 가능하며
        회원가입 및 기존 회원 삭제는 불가능함

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails user1 = User.builder()
                .username("user1")
                .password(bCryptPasswordEncoder().encode("1234"))
                .roles("ADMIN")
                .build();

        UserDetails user2 = User.builder()
                .username("user2")
                .password(bCryptPasswordEncoder().encode("1234"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }
     */
}
