package com.example.UserTrackservices.service;

import com.example.UserTrackservices.domain.Track;
import com.example.UserTrackservices.domain.User;
import com.example.UserTrackservices.exception.TrackNotFoundException;
import com.example.UserTrackservices.exception.UserAlreadyFoundException;
import com.example.UserTrackservices.exception.UserNotFoundException;
import com.example.UserTrackservices.rabbitMq.CommonUser;
import com.example.UserTrackservices.rabbitMq.Producer;
import com.example.UserTrackservices.rabbitMq.UserDTO;
import com.example.UserTrackservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Producer producer;
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    @Override
    public User addUser(User user) throws UserAlreadyFoundException {
        if(userRepository.findById(user.getUserId()).isPresent()) {
            throw new UserAlreadyFoundException();
        }
        return userRepository.insert(user);
    }

    @Override
    public User addUser1(CommonUser commonUser) {
        UserDTO userDTO=new UserDTO(commonUser.getUserId(),commonUser.getPassword());
        producer.sendDtoToQueue(userDTO);
        User user=new User(commonUser.getUserId(), commonUser.getUserName(), commonUser.getAddress(), new ArrayList<>());
        return userRepository.insert(user);
    }

    @Override
    public User addTrackForUser(String userId, Track track) throws UserNotFoundException {
        if(userRepository.findById(userId).isEmpty()){
            throw new UserNotFoundException();
        }
        User user=userRepository.findByUserId(userId);
        if(user.getTrackList()==null){
            user.setTrackList(Arrays.asList(track));
        }else {
            List<Track> tracks=user.getTrackList();
            tracks.add(track);
            user.setTrackList(tracks);
        }
        return userRepository.save(user);
    }

    @Override
    public User deleteTrackFromUser(String userId, int trackId) throws UserNotFoundException, TrackNotFoundException {
        boolean result=false;
        if(userRepository.findById(userId).isEmpty()){
            throw new UserNotFoundException();
        }
        User user=userRepository.findById(userId).get();
        List<Track> tracks=user.getTrackList();
        result=tracks.removeIf(x->x.getTrackId()==trackId);
        if(!result){
            throw new TrackNotFoundException();
        }
        user.setTrackList(tracks);
        return userRepository.save(user);
    }

    @Override
    public List<Track> getTrackForUser(String userId) throws UserNotFoundException {
        if(userRepository.findById(userId).isEmpty()){
            throw new UserNotFoundException();
        }
        return userRepository.findById(userId).get().getTrackList();
    }

    @Override
    public User updateTrackForUser(String userId, Track track) throws UserNotFoundException {
        if(userRepository.findById(userId).isEmpty())
        {
            throw new UserNotFoundException();
        }
        User user = userRepository.findById(userId).get();
        List<Track> tracks = user.getTrackList();
        Iterator<Track> iterator = tracks.iterator();
        while (iterator.hasNext()){
            Track track1 = iterator.next();
            if(track1.getTrackId() == track.getTrackId()){
                track1.setTrackName(track.getTrackName());
                track1.setArtistName(track.getArtistName());
            }
        }
        user.setTrackList(tracks);
        return userRepository.save(user);
    }
}
