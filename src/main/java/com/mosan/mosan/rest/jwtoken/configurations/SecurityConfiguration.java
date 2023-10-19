package com.mosan.mosan.rest.jwtoken.configurations;

import com.mosan.mosan.rest.jwtoken.Services.JwtAuthenticationFilter;
import com.mosan.mosan.rest.jwtoken.Services.JwtUsernameAndPasswordAuthenticationFilter;
import com.mosan.mosan.rest.jwtoken.utils.IJwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        prePostEnabled = true

)
public class SecurityConfiguration {
    private final UserDetailsService userDetailsService;
    private final IJwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    public SecurityConfiguration(UserDetailsService userDetailsService, IJwtTokenProvider jwtTokenProvider, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Order(1)
    @Bean
    public SecurityFilterChain apiSecurityConfiguration(HttpSecurity http) throws Exception {
        return http
                .securityMatcher(antMatcher("/api/v1/**"))
                .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->auth.requestMatchers(antMatcher(HttpMethod.GET,"/api/v1/animals")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.POST,"/api/v1/auth/signup")).permitAll()
                        .anyRequest().authenticated())
                .authenticationProvider(authenticationProvider())
             .addFilterBefore(jwtUsernameAndPasswordAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()), UsernamePasswordAuthenticationFilter.class)
             .addFilterAfter(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(15);
    }
    @Order(2)
    @Bean
    public SecurityFilterChain h2SecurityConfig(HttpSecurity http) throws Exception {
        return http.securityMatcher(antMatcher("/h2-console/**"))
                .authorizeHttpRequests(auth->
                        auth.requestMatchers(antMatcher("/h2-console/**")).permitAll())
                .csrf(csrf->csrf.ignoringRequestMatchers(antMatcher("/h2-console/**")))
                .headers(headers->headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .build();
    }
//    @Bean
//    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer(){
//        return factory -> factory.setContextPath("/api/v1");
//}
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){

        var manager=new DaoAuthenticationProvider();
        manager.setUserDetailsService(userDetailsService);
        manager.setPasswordEncoder(passwordEncoder());
        return manager;

    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

    public JwtUsernameAndPasswordAuthenticationFilter jwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager){
        JwtUsernameAndPasswordAuthenticationFilter filter=new JwtUsernameAndPasswordAuthenticationFilter("/api/v1/auth/signin",jwtTokenProvider);
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter(userDetailsService,jwtTokenProvider);
    }
}
