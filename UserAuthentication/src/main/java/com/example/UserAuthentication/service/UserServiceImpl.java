package com.example.UserAuthentication.service;

import com.example.UserAuthentication.domain.User;
import com.example.UserAuthentication.exception.UserAlreadyFoundException;
import com.example.UserAuthentication.exception.UserNotFoundException;
import com.example.UserAuthentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    @Override
    public User saveUser(User user) throws UserAlreadyFoundException {
        if(userRepository.findById(user.getUserId()).isPresent()){
            throw new UserAlreadyFoundException();
        }
        return userRepository.save(user);
    }

    @Override
    public User getAllByUserIdAndPassword(String userId, String password) throws UserNotFoundException {
        User user=userRepository.findByUserIdAndPassword(userId,password);
        if(user==null){
            throw new UserNotFoundException();
        }
        return user;
    }
}
