package com.quipux.login.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.quipux.login.Jwt.JwtAuthenticationFilter;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import lombok.RequiredArgsConstructor;

import static com.quipux.login.User.Permission.ADMIN_CREATE;
import static com.quipux.login.User.Permission.ADMIN_DELETE;
import static com.quipux.login.User.Permission.ADMIN_READ;
import static com.quipux.login.User.Permission.ADMIN_UPDATE;
import static com.quipux.login.User.Permission.USER_CREATE;
import static com.quipux.login.User.Permission.USER_DELETE;
import static com.quipux.login.User.Permission.USER_READ;
import static com.quipux.login.User.Permission.USER_UPDATE;
import static com.quipux.login.User.Role.ADMIN;
//import static com.quipux.login.User.Role.MANAGER;
import static com.quipux.login.User.Role.USER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        return http
            .csrf(csrf -> 
                csrf
                .disable())
            .authorizeHttpRequests(authRequest ->
              authRequest
                .requestMatchers("/auth/**").permitAll()

                .requestMatchers("/api/v1/user/**").hasAnyRole(ADMIN.name(),USER.name())
                
                
                .requestMatchers(GET,"/api/v1/user/**").hasAnyAuthority(ADMIN_READ.name(),USER_READ.name())
                .requestMatchers(POST,"/api/v1/user/**").hasAnyAuthority(ADMIN_CREATE.name(),USER_CREATE.name())
                .requestMatchers(PUT,"/api/v1/user/**").hasAnyAuthority(ADMIN_UPDATE.name(),USER_UPDATE.name())
               .requestMatchers(DELETE,"/api/v1/user/**").hasAnyAuthority(ADMIN_DELETE.name(),USER_DELETE.name())
                
                .requestMatchers("/api/v1/admin/**").hasAnyRole(ADMIN.name())
                
               .requestMatchers(GET,"/api/v1/admin/**").hasAnyAuthority(ADMIN_READ.name())
                .requestMatchers(POST,"/api/v1/admin/**").hasAnyAuthority(ADMIN_CREATE.name())
                .requestMatchers(PUT,"/api/v1/admin/**").hasAnyAuthority(ADMIN_UPDATE.name())
               .requestMatchers(DELETE,"/api/v1/admin/**").hasAnyAuthority(ADMIN_DELETE.name())
               .anyRequest().authenticated()
                )
            .sessionManagement(sessionManager->
                sessionManager 
                  .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
            
            
    }

}
