package com.example.demo.Service;

import java.util.Calendar;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JwtService {
    

     private AuthenticationManager authenticationManager;

     private final String KEY = "VincentIsRunningBlogForProgrammingBeginner";

    // public String generateToken(AuthRequest request) {
    //     Authentication authentication =
    //             new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
    //     authentication = authenticationManager.authenticate(authentication);
    //     UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    //     Calendar calendar = Calendar.getInstance();
    //     calendar.add(Calendar.MINUTE, 2);

    //     Claims claims = Jwts.claims();
    //     claims.put("username", userDetails.getUsername());
    //     claims.setExpiration(calendar.getTime());
    //     claims.setIssuer("Programming Classroom");

    //     Key secretKey = Keys.hmacShaKeyFor(KEY.getBytes());

    //     return Jwts.builder()
    //             .setClaims(claims)
    //             .signWith(secretKey)
    //             .compact();
    // }
}
