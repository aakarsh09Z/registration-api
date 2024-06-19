package com.aakarsh09z.registrationapi.Services;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface UserService {
    public ResponseEntity<?> getAllUsers(Pageable pageable);
    public ResponseEntity<?> getUserByUsername(String username, Pageable pageable);
}
