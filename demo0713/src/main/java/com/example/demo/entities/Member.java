package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Member")
public class Member {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    @Column(name="account")
    private String account;

    @Column(name="password")
    private String password;


    @Column(name="userName")
    private String userName;

    @Column(name="phone")
    private String phone;

    @Column(name="email")
    private String email;


    public Member(){}

    public Integer getID(){
        return ID;
    }

    public void setID(Integer ID){
        this.ID = ID;
    }

    public String getAccount(){
        return account;
    }

    public void setAccount(String account){
        this.account = account;
    }

    public String getPassword(){
        return password;
    }

    public void setpassword(String password){
        this.password = password;
    }
    public String getuserName(){
        return userName;
    }

    public void setuserName(String userName){
        this.userName = userName;
    }
    public String getphone(){
        return phone;
    }

    public void setphone(String phone){
        this.phone = phone;
    }
    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

   

    public boolean isPresent() {
        return false;
    }

  
   

    // public String findByUsername(String getuserName){
    //     return userName;
    // }

    



    
}
