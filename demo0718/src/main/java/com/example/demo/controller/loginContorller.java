package com.example.demo.controller;


import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.entities.Member;
import com.example.demo.entities.blackList;
import com.example.demo.entities.role;
import com.example.demo.repositories.MemberRepository;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.blackListResposity;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@RestController
public class loginContorller {
    private final MemberRepository memberRepository;
    private final blackListResposity blackListResposity;
    private final RoleRepository roleRepository;


    @Autowired
    public loginContorller(MemberRepository memberRepository, blackListResposity blackListResposity, RoleRepository roleRepository){
        this.memberRepository = memberRepository;
        this.blackListResposity = blackListResposity;
        this.roleRepository = roleRepository;

    }
  
 
    // @PostMapping("/login")
    // public ResponseEntity<String> userLogin(@RequestBody Member member) {

    //     Member storedUser = memberRepository.findByaccount(member.getAccount());
    //     String result="請輸入帳號密碼!";

    //     if (storedUser != null && storedUser.getPassword().equals(member.getPassword())) {

    //         result=verifyUser(storedUser.getEmail(), storedUser.getAccount(),storedUser.getPassword());
            
    //         //從黑名單踢出
    //         blackList user = blackListResposity.findByAccount(storedUser.getAccount());
    //         if(user != null){
    //             blackListResposity.delete(user);
    //         }

    //         return ResponseEntity.ok(result);
    //     } else {
    //         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("accout or password error!!!");
    //     }
    // }

    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@RequestBody role role) {

        role storedUser = roleRepository.findByaccount(role.getAccount());
        String result="請輸入帳號密碼!";

        if (storedUser != null && storedUser.getPassword().equals(role.getPassword())) {

            result=verifyUser(storedUser.getEmail(), storedUser.getAccount(),storedUser.getPassword());
            
            //從黑名單踢出
            blackList user = blackListResposity.findByAccount(storedUser.getAccount());
            if(user != null){
                blackListResposity.delete(user);
            }

            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("accout or password error!!!");
        }
    }

    public String verifyUser(String email,String userAcct , String userPasswd){
      
        role user=roleRepository.findByEmail(email);
        String result="";

        if(user.getEmail().equals(email)){
            
            if(user.getAccount().equals(userAcct)){

                if(user.getPassword().equals(userPasswd)){
            
                    //設定3min過期
                    Date expireDate = new Date(System.currentTimeMillis()+ 300 * 60 * 1000);

                    String jwtToken = Jwts.builder()
                    .setSubject(userAcct) //我以account當subject

                    .setExpiration(expireDate)// 設定time

                    //Mray891108是自訂的私鑰，HS512是自選的演算法
                    .signWith(SignatureAlgorithm.HS512,"ray891108")

                    .compact();

                    //直接將JWT傳回
                    result=jwtToken;
                }else{
                    result="0003";
                }
            }else{
                result="0002";       
            }
        }else{
            result="0001";
        }
        return result;        
    }    
    
   

}
