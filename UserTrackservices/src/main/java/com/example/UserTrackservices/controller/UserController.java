package com.example.UserTrackservices.controller;

import com.example.UserTrackservices.domain.Track;
import com.example.UserTrackservices.domain.User;
import com.example.UserTrackservices.exception.TrackNotFoundException;
import com.example.UserTrackservices.exception.UserAlreadyFoundException;
import com.example.UserTrackservices.exception.UserNotFoundException;
import com.example.UserTrackservices.rabbitMq.CommonUser;
import com.example.UserTrackservices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;

@RestController
@RequestMapping("/userTrackapp/user")
public class UserController {
    @Autowired
    private UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestBody User user) throws UserAlreadyFoundException {
        ResponseEntity responseEntity=null;
        try{
            user.setTrackList(new ArrayList<>());
            responseEntity=new ResponseEntity<>(userService.addUser(user), HttpStatus.CREATED);
        }catch (UserAlreadyFoundException e){
            throw new UserAlreadyFoundException();
        }catch (Exception e){
            responseEntity=new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PostMapping("/common")
    public ResponseEntity<?> addUserDetails(@RequestBody CommonUser commonUser){
        return new ResponseEntity<>(userService.addUser1(commonUser),HttpStatus.OK);
    }

    @PutMapping("/track/{userId}")
    public ResponseEntity<?> addTrackFromUser(@PathVariable String userId, @RequestBody Track track)throws UserNotFoundException {
        ResponseEntity responseEntity=null;
        try{
            responseEntity=new ResponseEntity<>(userService.addTrackForUser(userId,track),HttpStatus.OK);
        }catch (UserNotFoundException e){
            responseEntity=new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @DeleteMapping("/track/delete/{trackId}/{userId}")
    public ResponseEntity<?> deleteTrackFromUser(@PathVariable(value = "trackId") int trackId,@PathVariable(value = "userId") String userId)throws TrackNotFoundException,UserNotFoundException{
        ResponseEntity responseEntity=null;
        try{
            responseEntity=new ResponseEntity<>(userService.deleteTrackFromUser(userId,trackId),HttpStatus.OK);
        }catch (TrackNotFoundException e){
            throw new TrackNotFoundException();
        }catch (UserNotFoundException e){
            throw new UserNotFoundException();
        }catch (Exception e){
            responseEntity=new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }


    @GetMapping("/track/tracks")
    public ResponseEntity<?>getProductForUser(@RequestBody User user)throws UserNotFoundException{
        ResponseEntity responseEntity=null;
        try{
            responseEntity=new ResponseEntity<>(userService.getTrackForUser(user.getUserId()),HttpStatus.OK);
        }catch (UserNotFoundException e){
            throw new UserNotFoundException();
        }catch (Exception e){
            responseEntity=new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }


    @PutMapping("/track/updateTrack/{userId}")
    public ResponseEntity<?> updateTrackForUser(@PathVariable String userId,@RequestBody Track track) throws UserNotFoundException {
        ResponseEntity responseEntity = null;
        try{
            responseEntity = new ResponseEntity<>(userService.updateTrackForUser(userId,track), HttpStatus.CREATED);
        }catch (UserNotFoundException e){
            throw new UserNotFoundException();
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }



}
