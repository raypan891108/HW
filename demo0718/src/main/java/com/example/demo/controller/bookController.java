package com.example.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Member;
import com.example.demo.entities.blackList;
import com.example.demo.entities.book;
import com.example.demo.entities.role;
import com.example.demo.repositories.MemberRepository;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.bookRepository;
import com.example.demo.repositories.blackListResposity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class bookController {
    
    
    private final bookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final blackListResposity blackListResposity;

    @Autowired
    public bookController(bookRepository bookRepository, MemberRepository memberRepository,
                            RoleRepository roleRepository, blackListResposity blackListResposity){
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.roleRepository = roleRepository;
        this.blackListResposity = blackListResposity;
    }

    @GetMapping("/book")
    public ResponseEntity books(HttpServletRequest request){
    

        if(!islogin(request)){
            return ResponseEntity.ok("please login!!!");
        }else if(getRole(request) == 1){
            return ResponseEntity.ok(bookRepository.findAll());
        }else{
            return ResponseEntity.ok("no !!!");
        }
        
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity deleteBookById(@PathVariable("id") Integer id, HttpServletRequest request) {
        
        if(!islogin(request)){
            return ResponseEntity.ok("please login!!!");
        }else if(getRole(request) == 1){
            bookRepository.deleteById(id);
            return ResponseEntity.ok(bookRepository.findAll());
        }else{
            return ResponseEntity.ok("no success!!!");
        }
        
    }

    @Transactional //保证延迟加载的属性能够被正确初始化
    @PutMapping("/book/{id}")
    public ResponseEntity updateEmployee(@PathVariable Integer id,@RequestBody book updateBook, HttpServletRequest request) {

        if(!islogin(request)){
            return ResponseEntity.ok("please login!!!");
        }else if(getRole(request) == 1){
            book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not exist with id: " + id));

            existingBook.setBookName(updateBook.getBookName());
            existingBook.setAuthor(updateBook.getAuthor());
            existingBook.setMeans(updateBook.getMeans());
            existingBook.setPrice(updateBook.getPrice());
            existingBook.setCost(updateBook.getCost());

            bookRepository.save(existingBook);


            return ResponseEntity.ok(existingBook);
        }else{
            return ResponseEntity.ok("no success!!!");
        }

    }

    @GetMapping("/book/{bookName}")
    public ResponseEntity SearchBook(@PathVariable("bookName") String bookName, HttpServletRequest request) {
        
        if(!islogin(request)){
            return ResponseEntity.ok("please login!!!");
        }else if(getRole(request) == 1){
              book book = bookRepository.findBybookName(bookName);
              if(book != null)
                return ResponseEntity.ok(book);
            else
                return ResponseEntity.ok("NO DATA!!!!");
            
        }else{
            return ResponseEntity.ok("permission denied !!!");
        }
    }



    @PostMapping("/book")
    public ResponseEntity createBook(@RequestBody book book, HttpServletRequest request) {

        if(!islogin(request)){
            return ResponseEntity.ok("please login!!!");
        }else if(getRole(request) == 1){
            bookRepository.save(book);
            return ResponseEntity.ok("新增成功");
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
