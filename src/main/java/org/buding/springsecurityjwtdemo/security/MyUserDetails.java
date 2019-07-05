package org.buding.springsecurityjwtdemo.security;

import org.buding.springsecurityjwtdemo.model.User;
import org.buding.springsecurityjwtdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @program: spring-security-jwt-demo
 * @author: miaochen
 * @create: 2019-07-05 10:16
 * @description:
 **/
@Service
public class MyUserDetails implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        final User user=userRepository.findByUsername(s);
        if(user==null){
            throw new UsernameNotFoundException("User "+s+" not found ");
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(s)
                .password(user.getPassword())
                .authorities(user.getRoles())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
