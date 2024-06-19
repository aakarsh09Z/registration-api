package com.aakarsh09z.registrationapi.Payload.Request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VerifyToRegisterRequest {
    @NotEmpty(message = "Username field cannot be empty")
    private String username;
    @Min(value=1000, message="OTP should be 6 digit number")
    @Digits(message="OTP should be 6 digit number", fraction = 0, integer = 6)
    private String otp;
}
