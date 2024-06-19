package com.aakarsh09z.registrationapi.Security;

import com.aakarsh09z.registrationapi.Exceptions.ResourceNotFoundException;
import com.aakarsh09z.registrationapi.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        return this.userRepository.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException("User","username:"+username,0L));
    }
}
