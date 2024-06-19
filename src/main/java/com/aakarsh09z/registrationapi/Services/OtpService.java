package com.aakarsh09z.registrationapi.Services;

import com.aakarsh09z.registrationapi.Configuration.AppConstants;
import com.aakarsh09z.registrationapi.Exceptions.ResourceNotFoundException;
import com.aakarsh09z.registrationapi.Models.Otp;
import com.aakarsh09z.registrationapi.Models.User;
import com.aakarsh09z.registrationapi.Repository.OtpRepository;
import com.aakarsh09z.registrationapi.Repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpService {
    private static final String CHARACTERS = "123456789";
    private static final int OTP_LENGTH = 6;
    private final UserRepository userRepository;
    private final OtpRepository otpRepository;
    private final EmailService emailService;

    /*
        Get the OTP stored in the database
     */
    public String getOtpByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User","OTP",0));

        return user.getOtp().getValue();
    }
    /*
        Store the OTP in the database
     */
    public void sendOtp(String username) throws MessagingException {
        String OTP= this.generateOtp();
        LocalDateTime expirationTime=LocalDateTime.now().plusMinutes(AppConstants.OTP_EXPIRATION_MINUTE);
        User user=userRepository.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException("User","username"+username,0));
        Otp otp = new Otp();
        otp.setValue(OTP);
        otp.setExpirationTime(expirationTime);
        user.setOtp(otp);
        userRepository.save(user);
        emailService.sendOtpEmail(user.getEmail(),OTP);
    }
    /*
        Generate a 6 digit OTP
     */
    private String generateOtp() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            char randomChar = CHARACTERS.charAt(random.nextInt(CHARACTERS.length()));
            otp.append(randomChar);
        }
        return otp.toString();
    }
}