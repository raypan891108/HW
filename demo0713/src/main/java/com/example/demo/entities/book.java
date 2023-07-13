package com.example.demo.entities;

import jakarta.persistence.*;
// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.Table;

@Entity
@Table(name="book")
public class book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @Column(name="bookName")
    private String bookName;

    @Column(name="author")
    private String author;
    
    @Column(name="means")
    private String means;
    
    @Column(name="price")
    private Integer price;
    
    @Column(name="cost")
    private Integer cost;

    public book(){};

    public Integer getID(){
        return ID;
    }
    public void setID(Integer ID){
        this.ID = ID;
    }

    public String getbookName(){
        return bookName;
    }
    public void setbookName(String bookName){
        this.bookName = bookName;
    }

    public String getauthor(){
        return author;
    }
    public void setauthor(String author){
        this.author = author;
    }

    public String getmeans(){
        return means;
    }
    public void setmeans(String means){
        this.means = means;
    }

    public Integer getprice(){
        return price;
    }
    public void setprice(Integer price){
        this.price = price;
    }

    public Integer getcost(){
        return cost;
    }
    public void setcost(Integer cost){
        this.cost = cost;
    }

    
    
}
