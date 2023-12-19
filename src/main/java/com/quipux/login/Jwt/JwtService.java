package com.quipux.login.Jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
//import java.util.Properties;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@RestController
@Service
public class JwtService {

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;
  
    @Value("${jwt.expiration.miliseconds}")
    private String EXPIRATION_JWT;
    //private static final int EXPIRATION_JWT=1000*60*1;
    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user,EXPIRATION_JWT);
    }

    private String getToken(Map<String,Object> extraClaims, UserDetails user,@Value("${jwt.expiration.miliseconds}") String EXPIRATION_JWT) {
        return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(user.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis()+Integer.valueOf(EXPIRATION_JWT)))
            .signWith(getKey(SECRET_KEY), SignatureAlgorithm.HS256)
            .compact();
    }
    @Autowired
    private Key getKey(@Value("${jwt.secret.key}") String SECRET_KEY) {
       byte[] keyBytes=Decoders.BASE64.decode(SECRET_KEY);
       return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username=getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }

    private Claims getAllClaims(String token)
    {
        return Jwts
            .parserBuilder()
            .setSigningKey(getKey(SECRET_KEY))
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public <T> T getClaim(String token, Function<Claims,T> claimsResolver)
    {
        final Claims claims=getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token)
    {
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token)
    {
        return getExpiration(token).before(new Date());
    }
    
}
