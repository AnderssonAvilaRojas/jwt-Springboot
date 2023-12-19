package com.quipux.login.Auth;

import static com.quipux.login.User.Role.USER;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.quipux.login.Jwt.JwtService;
import com.quipux.login.User.Role;
import com.quipux.login.User.User;
import com.quipux.login.User.UserRepository;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request,HttpServletResponse response) {
        Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user=userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token=jwtService.getToken(user);
         if(authentication.isAuthenticated()){   

        ResponseCookie cookie = ResponseCookie.from("accessToken",token)
                     .httpOnly(true)
                     .secure(false)
                     .path("/")
                     .maxAge(1800)
                     .build();
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        }

            return AuthResponse.builder()
                     .token(token)
                     .build();

    }

    public AuthResponse register(RegisterRequest request) {
        Role rol =null;
      
        if(request.getRole().toString().equals(Role.USER.toString())) {rol =Role.USER;}
        if(request.getRole().toString().equals(Role.ADMIN.toString())) {rol =Role.ADMIN;}
 
        User user = User.builder()
                     .username(request.getUsername())
                     .password(passwordEncoder.encode( request.getPassword()))
                     .firstname(request.getFirstname())
                     .lastname(request.lastname)
                     .country(request.getCountry())
                     .role(rol)
                     .build();

        userRepository.save(user);

        return AuthResponse.builder()
                     .token(jwtService.getToken(user))
                     .build();
        
    }

}
