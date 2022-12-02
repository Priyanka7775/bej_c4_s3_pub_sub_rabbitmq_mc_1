package com.example.UserTrackservices.service;

import com.example.UserTrackservices.domain.Track;
import com.example.UserTrackservices.domain.User;
import com.example.UserTrackservices.exception.TrackNotFoundException;
import com.example.UserTrackservices.exception.UserAlreadyFoundException;
import com.example.UserTrackservices.exception.UserNotFoundException;
import com.example.UserTrackservices.rabbitMq.CommonUser;

import java.util.List;

public interface UserService {
    public User addUser(User user) throws UserAlreadyFoundException;
    User addUser1(CommonUser commonUser);
    public User addTrackForUser(String userId, Track track) throws UserNotFoundException;
    public User deleteTrackFromUser(String userId,int trackId) throws UserNotFoundException, TrackNotFoundException;
    List<Track> getTrackForUser(String userId) throws UserNotFoundException;
    User updateTrackForUser(String userId,Track track) throws UserNotFoundException;
}
