package com.example.UserAuthentication.service;

import com.example.UserAuthentication.domain.User;
import com.example.UserAuthentication.exception.UserAlreadyFoundException;
import com.example.UserAuthentication.exception.UserNotFoundException;

public interface UserService {
    public User saveUser(User user) throws UserAlreadyFoundException;
    public User getAllByUserIdAndPassword (String userId, String password) throws UserNotFoundException;
}
