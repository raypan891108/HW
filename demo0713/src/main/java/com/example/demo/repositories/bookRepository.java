package com.example.demo.repositories;


import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.stereotype.Repository;

import com.example.demo.entities.book;

@Repository
public interface bookRepository extends JpaRepository<book, Integer>{

    

    
    
}
