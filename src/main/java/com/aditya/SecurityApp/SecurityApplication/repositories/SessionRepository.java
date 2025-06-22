package com.aditya.SecurityApp.SecurityApplication.repositories;


/*
 * @author adityagupta
 * @date 21/06/25
 */

import com.aditya.SecurityApp.SecurityApplication.entities.Session;
import com.aditya.SecurityApp.SecurityApplication.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByUser(User user);

    Optional<Session> findByRefreshToken(String refreshToken);
}
