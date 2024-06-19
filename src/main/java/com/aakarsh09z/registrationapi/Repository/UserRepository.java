package com.aakarsh09z.registrationapi.Repository;

import com.aakarsh09z.registrationapi.Models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Page<User> findAll(Pageable pageable);
    Page<User> findByUsernameContaining(String username, Pageable pageable);
}
