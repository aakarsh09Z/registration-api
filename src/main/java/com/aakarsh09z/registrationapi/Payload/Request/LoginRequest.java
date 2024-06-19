package com.aakarsh09z.registrationapi.Payload.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotEmpty(message = "Username field cannot be empty")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,20}$", message = "Username must be alphanumeric and between 6 to 20 characters")
    private String username;
    @NotEmpty(message="Password must be filled")
    private String password;
}
