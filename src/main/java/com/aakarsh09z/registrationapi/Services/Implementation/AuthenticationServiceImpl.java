package com.aakarsh09z.registrationapi.Services.Implementation;

import com.aakarsh09z.registrationapi.Exceptions.ResourceNotFoundException;
import com.aakarsh09z.registrationapi.Models.Otp;
import com.aakarsh09z.registrationapi.Models.User;
import com.aakarsh09z.registrationapi.Payload.Request.*;
import com.aakarsh09z.registrationapi.Payload.Response.ApiResponse;
import com.aakarsh09z.registrationapi.Payload.Response.JwtTokenResponse;
import com.aakarsh09z.registrationapi.Repository.UserRepository;
import com.aakarsh09z.registrationapi.Services.AuthenticationService;
import com.aakarsh09z.registrationapi.Services.JwtTokenGenerator;
import com.aakarsh09z.registrationapi.Services.OtpService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

/*
    This is the implementation class of AuthenticationService.
 */

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;
    private final JwtTokenGenerator jwtTokenGenerator;

    /*
        RegisterRequest:
            firstname: First Name of the User
            lastname: Last Name of the User
            username: Username of the User
            email: Email Address of the User
            password: Password for registration
     */
    @Override
    public ResponseEntity<?> register(RegisterRequest request) {
        User user=new User();

        // Checking if the username is not present in the database.
        if(userRepository.findByUsername(request.getUsername()).isEmpty()) {
            //  Assigning the user instance
            user.setFirstname(request.getFirstname().trim());
            user.setLastname(request.getLastname().trim());
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setIsVerified(false);
        }
        // If the username is present in the database
        else {
            user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new ResourceNotFoundException("User", "username:" + request.getUsername(), 0));
            // If the user is verified then return Already Registered
            if (user.getIsVerified()) {
                return new ResponseEntity<>(new ApiResponse("Username already Registered", false), HttpStatus.CONFLICT);
            }
            // If the user was not verified then the username can be used by other users.
            user.setFirstname(request.getFirstname().trim());
            user.setLastname(request.getLastname().trim());
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setIsVerified(false);
        }
        // Adding the user to database
        userRepository.save(user);

        // Sending OTP to the email of the user for verification
        try {
            otpService.sendOtp(request.getEmail());
        }
        catch(MessagingException e){
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("Failed to send OTP",false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new ApiResponse("Check your email for OTP", true), HttpStatus.CREATED);
    }

    /*
        VerifyToRegisterRequest:
            email: Email of the User
            otp: OTP received by the User
     */
    @Override
    public ResponseEntity<?> verifyToRegister(VerifyToRegisterRequest request){
        String email=request.getEmail().trim().toLowerCase();
        // If the email is not present in the database then the user never registered
        if(userRepository.findByEmail(email).isEmpty()){
            return new ResponseEntity<>(new ApiResponse("No OTP generated",false),HttpStatus.NOT_FOUND);
        }
        // Fetch the OTP stored in the database
        String OTP = otpService.getOtpByEmail(request.getEmail());
        // Checking if the OTP received by the user matches with the one stored in the database
        if(!(request.getOtp().equals(OTP))){
            return new ResponseEntity<>(new ApiResponse("Incorrect OTP",false),HttpStatus.UNAUTHORIZED);
        }
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(()->new ResourceNotFoundException("User","Email",0));
        Otp otp=user.getOtp();
        // If the OTP generated has been in the database for 10 minutes or more it is expired
        if(LocalDateTime.now().isAfter(otp.getExpirationTime())){
            return new ResponseEntity<>(new ApiResponse("OTP expired",false),HttpStatus.CONFLICT);
        }
        /*
            JwtTokenResponse:
                accessToken: Access Token of the User
                refreshToken: Refresh Token to generate another AccessToken
                username: Username of the User
                email: Email of the User
                success: Boolean response whether the API ran successfully or not
         */
        JwtTokenResponse response = this.jwtTokenGenerator.generateToken(request.getEmail());
        // User is verified
        user.setIsVerified(true);
        userRepository.save(user);
        response.setUsername(user.getUsername());
        response.setEmail(request.getEmail());
        response.setSuccess(true);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /*
        LoginRequest:
            username: Username of the User
            password: Password for login
     */
    @Override
    public ResponseEntity<?> login(LoginRequest request){
        // Check if the user is present
        if(userRepository.findByUsername(request.getUsername()).isEmpty()){
            return new ResponseEntity<>(new ApiResponse("User not registered",false),HttpStatus.BAD_REQUEST);
        }
        User user=userRepository.findByUsername(request.getUsername()).orElseThrow(()->new ResourceNotFoundException("User", "username:" + request.getUsername(), 0));
        // Check if user is verified
        if(!user.getIsVerified()){
            return new ResponseEntity<>(new ApiResponse("User not verified",false),HttpStatus.UNAUTHORIZED);
        }
        // Check if the password is correct
        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            return new ResponseEntity<>(new ApiResponse("Invalid Credentials",false),HttpStatus.UNAUTHORIZED);
        }
        /*
            JwtTokenResponse:
                accessToken: Access Token of the User
                refreshToken: Refresh Token to generate another AccessToken
                username: Username of the User
                email: Email of the User
                success: Boolean response whether the API ran successfully or not
         */
        JwtTokenResponse response = this.jwtTokenGenerator.generateToken(request.getUsername());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setSuccess(true);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> forgotPassword(String username){
        // Check if the user is registered
        if(userRepository.findByUsername(username).isEmpty()){
            return new ResponseEntity<>(new ApiResponse("User not registered",false),HttpStatus.BAD_REQUEST);
        }
        User user=userRepository.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException("User", "username:" + username, 0));
        // Check if the user is verified
        if(!user.getIsVerified()){
            return new ResponseEntity<>(new ApiResponse("User not verified",false),HttpStatus.UNAUTHORIZED);
        }
        // Send OTP to the email of the user to verify the user
        try {
            otpService.sendOtp(user.getEmail());
        }
        catch(MessagingException e){
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("Failed to send OTP",false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new ApiResponse("Check your registered email for OTP",true),HttpStatus.OK);
    }
    /*
        VerifyForgotPasswordRequest:
            username: Username of the User
            otp: OTP received by User through email
     */
    @Override
    public ResponseEntity<?> verifyForgotPassword(VerifyForgotPasswordRequest request){
        // Check if the user is in the database
        if(userRepository.findByUsername(request.getUsername()).isEmpty()){
            return new ResponseEntity<>(new ApiResponse("User not found in database",false),HttpStatus.BAD_REQUEST);
        }
        User user=userRepository.findByUsername(request.getUsername()).orElseThrow(()->new ResourceNotFoundException("User", "username:" + request.getUsername(), 0));
        // Check if the OTP is expired
        if(LocalDateTime.now().isAfter(user.getOtp().getExpirationTime())){
            return new ResponseEntity<>(new ApiResponse("OTP expired",false),HttpStatus.CONFLICT);
        }
        // Check if the OTP is correct
        if(request.getOtp().equals(user.getOtp().getValue())) {
            return new ResponseEntity<>(new ApiResponse("OTP is successfully verified",true),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new ApiResponse("Incorrect OTP",false),HttpStatus.UNAUTHORIZED);
        }
    }
    /*
        ResetPasswordRequest:
            username: Username of the User
            password: New Password for resetting the password
     */
    @Override
    public ResponseEntity<?> resetPassword(ResetPasswordRequest request){
        // Check if the user is in the database
        if(userRepository.findByUsername(request.getUsername()).isEmpty()){
            return new ResponseEntity<>(new ApiResponse("User not found in the database",false),HttpStatus.BAD_REQUEST);
        }
        User user=userRepository.findByUsername(request.getUsername()).orElseThrow(()->new ResourceNotFoundException("User", "username:" + request.getUsername(), 0));
        // Check if the user is verified
        if(!user.getIsVerified()){
            return new ResponseEntity<>(new ApiResponse("User not verified",false),HttpStatus.BAD_REQUEST);
        }
        // Set new password
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return new ResponseEntity<>(new ApiResponse("Reset password successful",true),HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> resendOtp(String username){
        // Check id the user is in the database
        if(userRepository.findByUsername(username).isEmpty()){
            return new ResponseEntity<>(new ApiResponse("User not registered",false),HttpStatus.BAD_REQUEST);
        }
        User user=userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("User", "email:" + username, 0));
        // Ensure that the OTP resending cooldown is 1 minute
        if(LocalDateTime.now().isBefore(user.getOtp().getExpirationTime().minusMinutes(9))){
            return new ResponseEntity<>(new ApiResponse("Please wait 1 min before sending another OTP",false),HttpStatus.TOO_EARLY);
        }
        // Send the OTP to the email of the user
        try {
            otpService.sendOtp(user.getEmail());
        }
        catch (MessagingException e){
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("Failed to send OTP",false),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new ApiResponse("OTP sent successfully",true),HttpStatus.OK);
    }
}
