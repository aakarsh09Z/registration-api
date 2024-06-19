package com.aakarsh09z.registrationapi.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/*
    This class defines the Otp model.
 */

@Data
@Table(name = "OTP")
@Entity
public class Otp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String value;
    private LocalDateTime ExpirationTime;
    @OneToOne(mappedBy = "otp")
    @JsonIgnore
    private User user;
}