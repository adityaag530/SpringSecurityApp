package com.aditya.SecurityApp.SecurityApplication.repositories;


/*
 * @author adityagupta
 * @date 17/06/25
 */

import com.aditya.SecurityApp.SecurityApplication.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
