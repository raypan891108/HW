package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Member;



@Repository
public interface MemberRepository extends JpaRepository<Member, Integer>{

    // @Query("SELECT u FROM Member u WHERE u.userName = :username")
    // @Query("SELECT m FROM Member m WHERE m.userName = :username")
    static Member findByuserName(String userName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByuserName'");
    }
    Member findByaccount(String account);
    // @Query("SELECT u FROM MEMBER u WHERE u.userName = :getuserName")
    Member findByEmail(String email);
    Member getByEmail(String email);
    Member getByuserName(String userName);
    //Member findByUsername(String username);
    


}


