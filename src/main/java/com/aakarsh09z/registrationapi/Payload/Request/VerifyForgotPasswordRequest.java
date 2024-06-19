package com.aakarsh09z.registrationapi.Payload.Request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VerifyForgotPasswordRequest {
    @Pattern(regexp = "^[a-zA-Z0-9]{6,20}$", message = "Username must be alphanumeric and between 6 to 20 characters")
    private String username;
    @Pattern(regexp = "^[0-9]{6}$", message = "OTP should be a 6 digit number")
    private String otp;
}
