package com.aakarsh09z.registrationapi.Services;

import com.aakarsh09z.registrationapi.Exceptions.ResourceNotFoundException;
import com.aakarsh09z.registrationapi.Models.User;
import com.aakarsh09z.registrationapi.Payload.Response.ApiResponse;
import com.aakarsh09z.registrationapi.Payload.Response.JwtTokenResponse;
import com.aakarsh09z.registrationapi.Repository.UserRepository;
import com.aakarsh09z.registrationapi.Security.JwtHelper;
import com.aakarsh09z.registrationapi.Payload.Response.JwtAccessTokenResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class JwtTokenGenerator {
    private final AuthenticationManager authenticationManager;
    private final JwtHelper jwtHelper;
    private final UserDetailsService userDetailsService;
    private  final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public JwtTokenResponse generateToken(String username){
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        String myAccessToken = this.jwtHelper.generateAccessToken(userDetails);
        String myRefreshToken = this.jwtHelper.generateRefreshToken(userDetails);
        JwtTokenResponse response = new JwtTokenResponse();
        response.setAccessToken(myAccessToken);
        response.setRefreshToken(myRefreshToken);
        return response;
    }
    public ResponseEntity<?> generateRefreshToken(String token){
        if(token != null){
            try {
                String username = this.jwtHelper.getUsernameFromToken(token);
                if(username.startsWith("refresh_")) {
                    String newUsername = username.substring(8);
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(newUsername);
                    User user = userRepository.findByUsername(newUsername).orElseThrow(() -> new ResourceNotFoundException("User","username",0));
                    if (this.jwtHelper.validateRefreshToken(token, userDetails)) {
                        String accessToken = this.jwtHelper.generateAccessToken(userDetails);
                        return new ResponseEntity<>(new JwtAccessTokenResponse(accessToken, user.getFirstname(),user.getLastname(), newUsername,true),OK);
                    }
                    else {
                        return new ResponseEntity<>(new ApiResponse("Refresh Token Expired!!", false), HttpStatus.REQUEST_TIMEOUT);
                    }
                }
                else{
                    return new ResponseEntity<>(new ApiResponse("Not a Refresh Token", false), BAD_REQUEST);
                }
            }
            catch(IllegalArgumentException e){
                return new ResponseEntity<>(new ApiResponse("Unable to get the JWT token!!", false), BAD_REQUEST);
            }
            catch(ExpiredJwtException e){
                return new ResponseEntity<>(new ApiResponse("Refresh Token Expired!!", false), HttpStatus.REQUEST_TIMEOUT);
            }
            catch(MalformedJwtException e){
                return new ResponseEntity<>(new ApiResponse("Invalid jwt token", false), BAD_REQUEST);
            }
        }
        else {
            return new ResponseEntity<>(new ApiResponse("Invalid jwt token", false), BAD_REQUEST);
        }
    }
}
