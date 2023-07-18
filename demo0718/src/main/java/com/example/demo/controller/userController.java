package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Member;
import com.example.demo.entities.blackList;
import com.example.demo.entities.role;
import com.example.demo.repositories.MemberRepository;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.blackListResposity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class userController {
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final blackListResposity blackListResposity;

    @Autowired
    public userController(MemberRepository memberRepository, RoleRepository roleRepository,blackListResposity blackListResposity){
        this.memberRepository = memberRepository;
        this.roleRepository = roleRepository;
        this.blackListResposity = blackListResposity;
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestBody Member member) {
        Member editmember = memberRepository.findByEmail(member.getEmail());
        if(editmember.getAccount().equals(member.getAccount())){
            editmember.setPassword(member.getPassword());
            memberRepository.save(editmember);
            return ResponseEntity.ok("修改成功");
        }else{
            return ResponseEntity.ok("data error!!!!");
        }

    
        
    }

    @PostMapping("/userlogout")
    public ResponseEntity logoutUser(HttpServletRequest request) {
        
        
        blackList logoutUser = blackListResposity.findByAccount(getTokenBody(request));
         
        if(!islogin(request)){
            return ResponseEntity.ok("please login!!!");
        }else if(getRole(request) == 1){
            if(logoutUser == null){
                blackList logout = new blackList();
                logout.setAccount(getTokenBody(request));
                blackListResposity.save(logout);
            }
            return ResponseEntity.ok("已登出");
        }else{
            return ResponseEntity.ok("no !!!");
        }
        
    }


    public String getTokenBody(HttpServletRequest request){
        String authorHeader =  request.getHeader("Authorization");
        String bearer ="Bearer ";
        String token = authorHeader.substring(bearer.length());
        Claims claims = Jwts.parser().setSigningKey("ray891108")
        .parseClaimsJws(token).getBody();
        System.out.println("JWT payload:"+claims.get("sub", String.class));
        String account = claims.get("sub", String.class);
        return account;
    }


    boolean islogin(HttpServletRequest request){

        blackList user = blackListResposity.findByAccount(getTokenBody(request));

        if(user != null) return false ; else return true;
    }

    Integer getRole(HttpServletRequest request){
        Member member = memberRepository.findByaccount(getTokenBody(request));
        role role = roleRepository.findByaccount(member.getAccount());
        return role.getRole();
    };

}
