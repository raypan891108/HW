package com.example.demo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



// import javax.servlet.FilterChain;
// import javax.servlet.ServletException;

// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class AuthorizationCheckFilter extends OncePerRequestFilter{

    

    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest req,
            jakarta.servlet.http.HttpServletResponse res, jakarta.servlet.FilterChain chain)
            throws jakarta.servlet.ServletException, IOException {
                //如果不是登入就攔截
                if(!req.getServletPath().equals("/login") &&
                 !req.getServletPath().equals("/register") && !req.getServletPath().equals("/forgotPassword")){
                    //獲取token的頭
                    String authorHeader =  req.getHeader(AUTHORIZATION);
                    String bearer ="Bearer ";
                    //以jjwt驗證token，只要驗證成功就放行
                    //驗證失敗會拋exception，直接將錯誤訊息傳回
                    //token是否Bearer開頭
                    if(authorHeader!= null && authorHeader.startsWith(bearer)){
                        try{

                        String token = authorHeader.substring(bearer.length());

                        Claims claims = Jwts.parser().setSigningKey("ray891108")
                        .parseClaimsJws(token).getBody();

                        System.out.println("JWT payload:"+claims.toString());

                        chain.doFilter(req, res);
                        
                        }catch(Exception e){
                            System.err.println("Error : "+e);
                            res.setStatus(FORBIDDEN.value());
                            
                            Map<String, String> err = new HashMap<>();
                            err.put("jwt_err", e.getMessage());
                            res.setContentType(APPLICATION_JSON_VALUE);
                            new ObjectMapper().writeValue(res.getOutputStream(), err);
                        }
                    }else{
                        res.setStatus(UNAUTHORIZED.value());
                    }
                }else{
                    chain.doFilter(req, res);
                }
    }
    // public String findAcc(HttpServletRequest req){
    //     String authorHeader =  req.getHeader(AUTHORIZATION);
    //     String bearer ="Bearer ";
    //     String token = authorHeader.substring(bearer.length());
    //     Claims claims = Jwts.parser().setSigningKey("ray891108")
    //     .parseClaimsJws(token).getBody();
    //     System.out.println("JWT payload:"+claims.toString());
    //     return claims.toString();
    // }

    
      // @Override
    // protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
    //             //如果不是登入就攔截
    //             if(!req.getServletPath().equals("/login")){
    //                 String authorHeader =  req.getHeader(AUTHORIZATION);
    //                 String bearer ="Bearer ";
    //                 //以jjwt驗證token，只要驗證成功就放行
    //                 //驗證失敗會拋exception，直接將錯誤訊息傳回
    //                 if(authorHeader!= null && authorHeader.startsWith(bearer)){
    //                     try{
    //                     String token = authorHeader.substring(bearer.length());
    //                     Claims claims = Jwts.parser().setSigningKey("MySecret")
    //                     .parseClaimsJws(token).getBody();

    //                     System.out.println("JWT payload:"+claims.toString());

    //                     chain.doFilter(req, res);
                        
    //                     }catch(Exception e){
    //                         System.err.println("Error : "+e);
    //                         res.setStatus(FORBIDDEN.value());
                            
    //                         Map<String, String> err = new HashMap<>();
    //                         err.put("jwt_err", e.getMessage());
    //                         res.setContentType(APPLICATION_JSON_VALUE);
    //                         new ObjectMapper().writeValue(res.getOutputStream(), err);
    //                     }
    //                 }else{
    //                     res.setStatus(UNAUTHORIZED.value());
    //                 }
    //             }else{
    //                 chain.doFilter(req, res);
    //             }

                
        
    // }
    

    

}
