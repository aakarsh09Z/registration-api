package com.aakarsh09z.registrationapi.Services.Implementation;

import com.aakarsh09z.registrationapi.Models.User;
import com.aakarsh09z.registrationapi.Payload.Response.ApiResponse;
import com.aakarsh09z.registrationapi.Repository.UserRepository;
import com.aakarsh09z.registrationapi.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    /*
        The getAllUsers method is designed to retrieve a paginated
        list of users from the database and return an appropriate HTTP response.
     */
    @Override
    public ResponseEntity<?> getAllUsers(Pageable pageable){
        Page<User> users = userRepository.findAll(pageable);
        if(users.getNumberOfElements()==0){
            return new ResponseEntity<>(new ApiResponse("No users registered",false), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    /*
        The getUserByUsername method is designed to retrieve a paginated
        list of users whose usernames contain a specified string.
     */
    @Override
    public ResponseEntity<?> getUserByUsername(String username, Pageable pageable){
        Page<User> users = userRepository.findByUsernameContaining(username,pageable);
        if(users.getNumberOfElements()==0){
            return new ResponseEntity<>(new ApiResponse("No users containing "+username,false), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
