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
public class RegisterRequest {
    private String firstname;
    private String lastname;
    @NotEmpty(message = "Username field cannot be empty")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,20}$", message = "Username must be alphanumeric and between 6 to 20 characters")
    private String username;
    @NotEmpty(message = "Email field cannot be empty")
    @Email(regexp="[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",flags = Pattern.Flag.CASE_INSENSITIVE,message = "Invalid email format")
    private String email;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must contain at least 8 characters, including one uppercase letter, one lowercase letter, one digit, and one special character.")
    private String password;
}
