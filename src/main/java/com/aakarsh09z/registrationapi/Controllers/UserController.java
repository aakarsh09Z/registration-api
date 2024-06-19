package com.aakarsh09z.registrationapi.Controllers;

import com.aakarsh09z.registrationapi.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    This class contains the endpoints for fetching the users and their details
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    @GetMapping("/fetch")
    public ResponseEntity<?> getAllUsers(@PageableDefault(page = 0, size = 10) Pageable pageable){
        return this.userService.getAllUsers(pageable);
    }
    @GetMapping("/fetch/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username, @PageableDefault(page = 0, size = 10) Pageable pageable){
        return this.userService.getUserByUsername(username, pageable);
    }
}
