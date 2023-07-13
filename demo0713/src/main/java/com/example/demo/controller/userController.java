package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Member;
import com.example.demo.entities.Register;
import com.example.demo.entities.book;
import com.example.demo.repositories.MemberRepository;

@RestController
public class userController {
    private final MemberRepository memberRepository;

    @Autowired
    public userController(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestBody Member member) {
        Member editmember = memberRepository.getByEmail(member.getEmail());
        if(editmember.getAccount().equals(member.getAccount())){
            editmember.setpassword(member.getPassword());
            memberRepository.save(editmember);
            return ResponseEntity.ok("修改成功");
        }else{
            return ResponseEntity.ok("data error!!!!");
        }

        
    }

}
