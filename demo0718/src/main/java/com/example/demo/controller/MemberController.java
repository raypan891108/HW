package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

// @Api(tags = "Todo list 相關api")
@RestController
@RequestMapping("/")
public class MemberController {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final blackListResposity blackListResposity;

    @Autowired
    public MemberController(MemberRepository memberRepository,RoleRepository roleRepository, blackListResposity blackListResposity){
        this.memberRepository = memberRepository;
        this.roleRepository = roleRepository;
        this.blackListResposity = blackListResposity;
    }


   

    @GetMapping("/Member")
    public ResponseEntity members(HttpServletRequest request){

        if(!islogin(request)){
            return ResponseEntity.ok("please login!!!");
        }else if(getRole(request) == 1){
            return ResponseEntity.ok(memberRepository.findAll( ));
        }else{
            return ResponseEntity.ok("permission denied !!!");
        }

    }
    

    
    @DeleteMapping("/Member/{id}")
    public ResponseEntity deleteMemberById(@PathVariable("id") Integer id, HttpServletRequest request) {
        
        if(!islogin(request)){
            return ResponseEntity.ok("please login!!!");
        }else if(getRole(request) == 1){
            memberRepository.deleteById(id);
            return ResponseEntity.ok("delete success");
        }else{
            return ResponseEntity.ok("permission denied !!!");
        }
    }

    
    @Transactional //保证延迟加载的属性能够被正确初始化
    @PutMapping("/Member/{id}")
    public ResponseEntity updateMember(@PathVariable("id") Integer id, @RequestBody Member updateMember, HttpServletRequest request) {
        
        if(!islogin(request)){
            return ResponseEntity.ok("please login!!!");
        }else if(getRole(request) == 1){

            Member existingMember = memberRepository.getById(id);

            if (existingMember != null) {
                existingMember.setAccount(updateMember.getAccount());
                existingMember.setPassword(updateMember.getPassword());
                existingMember.setUserName(updateMember.getUserName());
                existingMember.setPhone(updateMember.getPhone());
                existingMember.setEmail(updateMember.getEmail());

                memberRepository.saveAndFlush(existingMember);

                return ResponseEntity.ok("edit success");
            }else{
                return  ResponseEntity.ok("NO DATA!!!");
            }
        }else{
            return ResponseEntity.ok("permission denied !!!");
        }
    }

     
    @Transactional //保证延迟加载的属性能够被正确初始化
    @GetMapping("/Member/{account}")
    public ResponseEntity SearchMember(@PathVariable("account") String account, HttpServletRequest request) {
        
        if(!islogin(request)){
            return ResponseEntity.ok("please login!!!");
        }else if(getRole(request) == 1){
                Member member = memberRepository.findByaccount(account);
                if(member != null)
                    return ResponseEntity.ok(member);
                else
                    return ResponseEntity.ok("NO DATA!!!!");
            
        }else{
            return ResponseEntity.ok("permission denied !!!");
        }
    }



    @PostMapping("/Member")
    public ResponseEntity createMember(@RequestBody Member member, HttpServletRequest request) {
      
        if(!islogin(request)){
            return ResponseEntity.ok("please login!!!");
        }else if(getRole(request) == 1){
            memberRepository.save(member);
            return ResponseEntity.ok("新增成功");
        }else{
            return ResponseEntity.ok("permission denied!!!");
        }
        
    }

    //Function

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
