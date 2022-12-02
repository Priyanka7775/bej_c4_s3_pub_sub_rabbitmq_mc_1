package com.example.UserAuthentication.controller;

import com.example.UserAuthentication.domain.User;
import com.example.UserAuthentication.exception.UserAlreadyFoundException;
import com.example.UserAuthentication.exception.UserNotFoundException;
import com.example.UserAuthentication.service.SecurityTokenGenerator;
import com.example.UserAuthentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
@RequestMapping("user/v1")
public class UserController {
    private UserService userService;
    private SecurityTokenGenerator securityTokenGenerator;

    @Autowired
    public UserController(UserService userService, SecurityTokenGenerator securityTokenGenerator){
        this.userService=userService;
        this.securityTokenGenerator=securityTokenGenerator;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) throws UserNotFoundException {
        Map<String,String> map=null;
        try{
            User user1=userService.getAllByUserIdAndPassword(user.getUserId(), user.getPassword());
            if(user1.getUserId().equals(user.getUserId())){
                map=securityTokenGenerator.generateToken(user);
            }
            return new ResponseEntity<>(map, HttpStatus.OK);
        }catch (UserNotFoundException e){
            throw new RuntimeException(e);
        }catch (Exception e){
            return new ResponseEntity<>("Try after sometimes", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody User user)throws UserAlreadyFoundException {
        User userCreated=userService.saveUser(user);
        return new ResponseEntity<>("USER CREATED", HttpStatus.CREATED);
    }


}
