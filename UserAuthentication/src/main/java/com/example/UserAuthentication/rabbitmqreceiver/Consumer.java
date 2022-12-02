package com.example.UserAuthentication.rabbitmqreceiver;

import com.example.UserAuthentication.domain.User;
import com.example.UserAuthentication.exception.UserAlreadyFoundException;
import com.example.UserAuthentication.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    @Autowired
    private UserService userService;

    @RabbitListener(queues = "user_queue")
    public void getDtoFromQueueAndAddToDb(UserDTO userDTO) throws  UserAlreadyFoundException {
        User user=new User();
        user.setUserId(userDTO.getUserId());
        user.setPassword(userDTO.getPassword());

        User user1=userService.saveUser(user);
        System.out.println("result= " + user1);
    }
}
