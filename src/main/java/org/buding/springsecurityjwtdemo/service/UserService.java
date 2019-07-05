package org.buding.springsecurityjwtdemo.service;

import org.buding.springsecurityjwtdemo.exception.CustomException;
import org.buding.springsecurityjwtdemo.model.User;
import org.buding.springsecurityjwtdemo.repository.UserRepository;
import org.buding.springsecurityjwtdemo.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: spring-security-jwt-demo
 * @author: miaochen
 * @create: 2019-07-05 10:06
 * @description:
 **/
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;


    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    public String signin(String username,String password){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
            return jwtTokenProvider.createToken(username,userRepository.findByUsername(username).getRoles());
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * 注册
     * @param user
     * @return
     */
    public String signup(User user){
        if(!userRepository.existsByUsername(user.getUsername())){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return jwtTokenProvider.createToken(user.getUsername(),user.getRoles());
        }else {
            throw new CustomException("Username is already in use",HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public void delete(String username){
        userRepository.deleteByUsername(username);
    }

    /**
     * 搜索
     * @param username
     * @return
     */
    public User search(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        return user;
    }

    /**
     *
     * @param req
     * @return
     */
    public User whoami(HttpServletRequest req) {
        return userRepository.findByUsername(jwtTokenProvider.parseToken(jwtTokenProvider.resolveToken(req)).getSubject());
    }

    /**
     * 重新获取token
     * @param username
     * @return
     */
    public String refresh(String username) {
        return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
    }

}
