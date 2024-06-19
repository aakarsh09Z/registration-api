package com.aakarsh09z.registrationapi.Controllers;

import com.aakarsh09z.registrationapi.Payload.Request.*;
import com.aakarsh09z.registrationapi.Payload.Response.ApiResponse;
import com.aakarsh09z.registrationapi.Services.AuthenticationService;
import com.aakarsh09z.registrationapi.Services.JwtTokenGenerator;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
    The AuthenticationController class handles authentication-related HTTP requests.
*/

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtTokenGenerator jwtTokenGenerator;
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            return this.authenticationService.register(request);
        }
        catch(MessagingException e){
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("Failed to send OTP",false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/register/verify")
    public ResponseEntity<?> verify(@Valid @RequestBody VerifyToRegisterRequest request) {
        return this.authenticationService.verifyToRegister(request);
    }
    @GetMapping("/regenerateToken")
    public ResponseEntity<?> refreshToken(@RequestParam String token) {
        return this.jwtTokenGenerator.generateRefreshToken(token);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request){
        return this.authenticationService.login(request);
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassowrd(@RequestParam String username){
        return this.authenticationService.forgotPassword(username);
    }
    @PostMapping("/forgot-password/verify")
    public ResponseEntity<?> verifyForgotPassword(@Valid @RequestBody VerifyForgotPasswordRequest request) {
        return this.authenticationService.verifyForgotPassword(request);
    }
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        return this.authenticationService.resetPassword(request);
    }
    @PostMapping("/resend-otp")
    public ResponseEntity<?> resendOtp(@RequestParam String username){
        return this.authenticationService.resendOtp(username);
    }
}
