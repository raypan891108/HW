package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="register")
public class Register {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;
    
    @Column(name="account")
    private String account;

    @Column(name="password")
    private String password;


    @Column(name="Againpassword")
    private String Againpassword;

    @Column(name="userName")
    private String userName;

    @Column(name="phone")
    private String phone;

    @Column(name="email")
    private String email;


    public Register(){}

    // public Integer getID(){
    //     return ID;
    // }

    // public void setID(Integer ID){
    //     this.ID = ID;
    // }

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

    public String getAgainpassword(){
        return Againpassword;
    }

    public void setAgainpassword(String Againpassword){
        this.Againpassword = Againpassword;
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

   

    // public String findByUsername(String getuserName){
    //     return userName;
    // }

    



    
}

