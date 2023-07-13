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
import com.example.demo.entities.book;
import com.example.demo.entities.role;
import com.example.demo.repositories.MemberRepository;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.bookRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class bookController {
    
    
    private final bookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public bookController(bookRepository bookRepository, MemberRepository memberRepository,
                            RoleRepository roleRepository){
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/book")
    public ResponseEntity books(HttpServletRequest request){
        Member member = memberRepository.findByaccount(getTokenBody(request));   
        System.out.println("JWT payload:"+ member);
        role role = roleRepository.findByaccount(member.getAccount());

        if(role.getRole() == 1){
            return ResponseEntity.ok(bookRepository.findAll());
        }else{
            return ResponseEntity.ok("no !!!");
        }
        
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity deleteBookById(@PathVariable("id") Integer id, HttpServletRequest request) {
        Member member = memberRepository.findByaccount(getTokenBody(request));   
        System.out.println("JWT payload:"+ member);
        role role = roleRepository.findByaccount(member.getAccount());

        if(role.getRole() == 1){
            bookRepository.deleteById(id);
            return ResponseEntity.ok(bookRepository.findAll());
        }else{
            return ResponseEntity.ok("no success!!!");
        }
        
    }

    @Transactional //保证延迟加载的属性能够被正确初始化
    @PutMapping("/book/{id}")
    public ResponseEntity updateEmployee(@PathVariable Integer id,@RequestBody book updateBook, HttpServletRequest request) {

        Member member = memberRepository.findByaccount(getTokenBody(request));   
        System.out.println("JWT payload:"+ member);
        role role = roleRepository.findByaccount(member.getAccount());

        if(role.getRole() == 1){
            book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not exist with id: " + id));

            existingBook.setbookName(updateBook.getbookName());
            existingBook.setauthor(updateBook.getauthor());
            existingBook.setmeans(updateBook.getmeans());
            existingBook.setprice(updateBook.getprice());
            existingBook.setcost(updateBook.getcost());

            bookRepository.save(existingBook);


            return ResponseEntity.ok(existingBook);
        }else{
            return ResponseEntity.ok("no success!!!");
        }

    }


    // public book updateBook(@PathVariable("id") Integer id, @RequestBody book updateBook) {
    //     // book existingBook = bookRepository.getById(id);
    //     // if (existingBook != null) {
    //     //     existingBook.setbookName(updateBook.getbookName());
    //     //     existingBook.setauthor(updateBook.getauthor());
    //     //     existingBook.setmeans(updateBook.getmeans());
    //     //     existingBook.setprice(updateBook.getprice());
    //     //     existingBook.setcost(updateBook.getcost());
    //     //     //bookRepository.save(updateBook);
    //     //     return bookRepository.saveAndFlush(updateBook);
    //     // }
    //     // return null;// 或者抛出异常等适当的处理
      
    // }
    

    @PostMapping("/book")
    public book createBook(@RequestBody book book) {
        return bookRepository.save(book);
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

    

}
