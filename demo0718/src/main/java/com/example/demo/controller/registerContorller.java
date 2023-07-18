package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Member;
import com.example.demo.entities.Register;
import com.example.demo.repositories.MemberRepository;

@RestController
public class registerContorller {
    
    
    private final MemberRepository memberRepository;

    @Autowired
    public registerContorller(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }
    
    
    @PostMapping("/register")
    public ResponseEntity<String> userRegister(@RequestBody Register newUser) {

         Member member = new Member();
        
        if (newUser.getAccount() != null && newUser.getPassword() != null &&
            newUser.getUserName() != null && newUser.getPhone() != null &&
            newUser.getEmail() != null && newUser.getAgainpassword() != null 
            && newUser.getAgainpassword().equals(newUser.getPassword()))
            {

            member.setAccount(newUser.getAccount());
            member.setPassword(newUser.getPassword());
            member.setUserName(newUser.getUserName());
            member.setPhone(newUser.getPhone());
            member.setEmail(newUser.getEmail());
            memberRepository.save(member);
            return ResponseEntity.ok("新增成功");

        } else {
            System.out.println(newUser.getAccount()) ;
            System.out.println(newUser.getPassword()) ;
            System.out.println(newUser.getAgainpassword()) ;
            System.out.println(newUser.getUserName()) ;
            System.out.println(newUser.getPhone()) ;
            System.out.println(newUser.getEmail()) ;
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("資料有誤");
        }
        
    }
}
