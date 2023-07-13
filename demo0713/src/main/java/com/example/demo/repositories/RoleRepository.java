package com.example.demo.repositories;

import org.springframework.stereotype.Repository;

import com.example.demo.entities.Member;
import com.example.demo.entities.role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


import org.springframework.data.*;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.Member;
@Repository
public interface RoleRepository extends JpaRepository<role, Integer>{

    public role findByaccount(String account);

    //role getByaccount(String account);

    //role findByAccount(String account);
    
}
