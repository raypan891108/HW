package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repositories.MemberRepository;
import com.example.demo.repositories.RoleRepository;

@RestController
public class RoleController {
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public RoleController(MemberRepository memberRepository, RoleRepository roleRepository){
        this.memberRepository = memberRepository;
        this.roleRepository = roleRepository;
    }

    
}
