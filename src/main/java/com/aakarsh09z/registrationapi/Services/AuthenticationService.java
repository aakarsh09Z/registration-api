package com.aakarsh09z.registrationapi.Services;

import com.aakarsh09z.registrationapi.Payload.Request.*;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    public ResponseEntity<?> register(RegisterRequest request) throws MessagingException;
    public ResponseEntity<?> verifyToRegister(VerifyToRegisterRequest request);
    public ResponseEntity<?> login(LoginRequest request);
    public ResponseEntity<?> forgotPassword(String username);
    public ResponseEntity<?> verifyForgotPassword(VerifyForgotPasswordRequest request);
    public ResponseEntity<?> resetPassword(ResetPasswordRequest request);
    public ResponseEntity<?> resendOtp(String username);
}
