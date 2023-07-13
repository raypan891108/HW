package com.example.demo.controller;

 

import java.security.Principal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.example.demo.Service.UserService;
//import com.example.demo.entities.LoginAttempt;
import com.example.demo.entities.Member;
import com.example.demo.entities.Register;
import com.example.demo.entities.book;
//import com.example.demo.repositories.LoginAttemptReposity;
import com.example.demo.repositories.MemberRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class loginContorller {
    private final MemberRepository memberRepository;
    //private final LoginAttemptReposity loginAttemptReposity;

    @Autowired
    public loginContorller(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
        //this.loginAttemptReposity=loginAttemptReposity;
    }


    
    // @GetMapping("/login")
    // public ResponseEntity<String> userLogin(@RequestBody Member member) {

    //     Member storedUser = memberRepository.findByaccount(member.getAccount());
        
    //     if (storedUser != null && storedUser.getPassword().equals(member.getPassword())) {
    //         LoginAttempt login = loginAttemptReposity.findByaccount(member.getAccount());
    //         login.setLoginTime(LocalDateTime.now());
    //         login.setSuccess(true);
    //         return ResponseEntity.ok("登录成功");
    //     } else {
    //         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("无效的凭据");
    //     }
    // }
    @GetMapping("/login")
    public ResponseEntity<String> userLogin(@RequestBody Member member) {

        Member storedUser = memberRepository.findByaccount(member.getAccount());
        String result="請輸入帳號密碼!";
        //HttpSession session = request.getSession();

        if (storedUser != null && storedUser.getPassword().equals(member.getPassword())) {
            result=verifyUser(storedUser.getEmail(), storedUser.getAccount(),storedUser.getPassword());
            //return ResponseEntity.ok(result);  
            //session.setAttribute("user",storedUser);
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("accout or password error!!!");
        }
    }

    public String verifyUser(String email,String userAcct , String userPasswd){
        /*
        0000 login success
        0001 wrong email
        0002 wrong useracct
        0003 wrong passwd
        0004 acct locked
        */
        Member user=memberRepository.findByEmail(email);
        String result="9999";
        if(user.getEmail().equals(email)){
            
            if(user.getAccount().equals(userAcct)){

                if(user.getPassword().equals(userPasswd)){
            //新增了以下這段：

                    Date expireDate = 
                    //設定30min過期
                    new Date(System.currentTimeMillis()+ 3 * 60 * 1000);
                    String jwtToken = Jwts.builder()
                    .setSubject(userAcct) //我以account當subject
                    .setExpiration(expireDate)
                    //MySecret是自訂的私鑰，HS512是自選的演算法，可以任意改變
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
