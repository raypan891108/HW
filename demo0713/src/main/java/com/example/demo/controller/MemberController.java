package com.example.demo.controller;

 

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.AuthorizationCheckFilter;
//import com.example.demo.JwtTokenUtil;
import com.example.demo.entities.Member;
import com.example.demo.entities.role;
import com.example.demo.repositories.MemberRepository;
import com.example.demo.repositories.RoleRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class MemberController {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public MemberController(MemberRepository memberRepository,RoleRepository roleRepository){
        this.memberRepository = memberRepository;
        this.roleRepository = roleRepository;
    }





    // private final AuthenticationManager authenticationManager;
    // private final JwtTokenUtil jwtTokenUtil;
    // private final UserViewMapper userViewMapper;

    // public AuthApi(AuthenticationManager authenticationManager,
    //             JwtTokenUtil jwtTokenUtil,
    //             UserViewMapper userViewMapper) {
    //     this.authenticationManager = authenticationManager;
    //     this.jwtTokenUtil = jwtTokenUtil;
    //     this.userViewMapper = userViewMapper;
    // }






    // @GetMapping("/Member/{userName}")
    // public ResponseEntity members(@PathVariable("userName") String userName){
    //     // HttpSession session = request.getSession();
    //     // ResponseEntity.ok(session.getAttribute("user"));
    //     Member member = memberRepository.getByuserName(userName);
    //     role role = roleRepository.findByaccount(member.getAccount());
    //     if(role.getRole() == 1){
    //         return ResponseEntity.ok(memberRepository.findAll( ));
    //     }else{
    //         return ResponseEntity.ok("no !!!");
    //     }

        
    // }

 

    @GetMapping("/Member")
    public ResponseEntity members(HttpServletRequest request){
        // String authorHeader =  request.getHeader("Authorization");
        // String bearer ="Bearer ";
        // String token = authorHeader.substring(bearer.length());
        // Claims claims = Jwts.parser().setSigningKey("ray891108")
        // .parseClaimsJws(token).getBody();
        // System.out.println("JWT payload:"+claims.get("sub", String.class));


        // HttpSession session = request.getSession();
        // ResponseEntity.ok(session.getAttribute("user"));

        //Member member = memberRepository.findByaccount(claims.get("sub", String.class));
        Member member = memberRepository.findByaccount(getTokenBody(request));
        
        System.out.println("JWT payload:"+ member);
        role role = roleRepository.findByaccount(member.getAccount());
        if(role.getRole() == 1){
            return ResponseEntity.ok(memberRepository.findAll( ));
        }else{
            return ResponseEntity.ok("no !!!");
        }

    }
    

    
    @DeleteMapping("/Member/{id}")
    public ResponseEntity deleteMemberById(@PathVariable("id") Integer id, HttpServletRequest request) {
        
        Member member = memberRepository.findByaccount(getTokenBody(request));   
        System.out.println("JWT payload:"+ member);
        role role = roleRepository.findByaccount(member.getAccount());

        if(role.getRole() == 1){
            memberRepository.deleteById(id);

            return ResponseEntity.ok("delete success");
        }else{
            return ResponseEntity.ok("no !!!");
        }
       

        //memberRepository.deleteById(id);
    //     return ResponseEntity.ok("delete success");
    }

    
    @Transactional //保证延迟加载的属性能够被正确初始化
    @PutMapping("/Member/{id}")
    public ResponseEntity updateMember(@PathVariable("id") Integer id, @RequestBody Member updateMember, HttpServletRequest request) {
        Member member = memberRepository.findByaccount(getTokenBody(request));   
        System.out.println("JWT payload:"+ member);
        role role = roleRepository.findByaccount(member.getAccount());

        if(role.getRole() == 1){
            
            Member existingMember = memberRepository.getById(id);
            if (existingMember != null) {
                existingMember.setAccount(updateMember.getAccount());
                existingMember.setpassword(updateMember.getPassword());
                existingMember.setuserName(updateMember.getuserName());
                existingMember.setphone(updateMember.getphone());
                existingMember.setEmail(updateMember.getEmail());
                //memberRepository.save(existingMember);
                memberRepository.saveAndFlush(existingMember);
                return ResponseEntity.ok("edit success");
            }else{
                return  ResponseEntity.ok("NO DATA!!!");
            }
        }else{
            return ResponseEntity.ok("no !!!");
        }
       
        
        
        // Member existingMember = memberRepository.getById(id);
        // if (existingMember != null) {
        //     existingMember.setAccount(updateMember.getAccount());
        //     existingMember.setpassword(updateMember.getPassword());
        //     existingMember.setuserName(updateMember.getuserName());
        //     existingMember.setphone(updateMember.getphone());
        //     existingMember.setEmail(updateMember.getEmail());
        //     //memberRepository.save(existingMember);
        //     return memberRepository.saveAndFlush(existingMember);
        // }
        // return null;// 或者抛出异常等适当的处理
    }



    // @PostMapping("/Member")
    // public Member createMember(@RequestBody Member member) {
    //     return memberRepository.save(member);
    // }



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

    // @GetMapping("/login")
    // public String userLogin(@RequestBody Member member) {

    //     Member storedUser = memberRepository.findByaccount(member.getAccount());
        
    //     if (storedUser != null && storedUser.getPassword().equals(member.getPassword())) {
    //         ResponseEntity.ok("登录成功");
    //         return "login";
    //     } else {
    //         return "error";
    //     }
    // }

    // @GetMapping("/login")
    // public ResponseEntity<String> userLogin(@RequestBody Member member, HttpServletRequest request) {

    //     Member storedUser = memberRepository.findByaccount(member.getAccount());
        
    //     HttpSession session = request.getSession();

    //     if (storedUser != null && storedUser.getPassword().equals(member.getPassword())) {
            
    //         session.setAttribute("user",storedUser);

    //         return ResponseEntity.ok("登录成功");
    //     } else {
    //         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("无效的凭据");
    //     }
    // }
    // public ResponseEntity<String> userLogin(@RequestBody HashMap<String,String> map){
    //     String email= map.get("email");
    //     String userAccount= map.get("userAccount");
    //     String userPassword= map.get("userPassword");
    //     String result="請輸入帳號密碼!";
    //     if(StringUtils.isBlank(email)||StringUtils.isBlank(userAccount)||StringUtils.isBlank(userPassword)){
    //         return ResponseEntity.ok(result);
    //     }else{
    //         result=userService.verifyUser(email, userAccount, userPassword);
    //         return ResponseEntity.ok(result);  
    //     }
    // } 
 
}
