package com.example.demo.entities;
import jakarta.persistence.*;


@Entity
@Table(name="role")
public class role {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    @Column(name="account")
    private String account;

    @Column(name="password")
    private String password;

    @Column(name="email")
    private String email;

    @Column(name="role")
    private Integer role;

    public role(){}

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

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }
    
     public Integer getRole(){
        return role;
    }

    public void setRole(Integer role){
        this.role = role;
    }
}
