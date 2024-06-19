package com.aakarsh09z.registrationapi.Repository;

import com.aakarsh09z.registrationapi.Models.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp,Long> {
}
