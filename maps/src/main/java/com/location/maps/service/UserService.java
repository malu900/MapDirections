package com.location.maps.service;

import com.location.maps.api.payload.DirectionRequest;
import com.location.maps.exception.AppException;
import com.location.maps.model.Direction;
import com.location.maps.model.User;
import com.location.maps.model.Waypoint;
import com.location.maps.respository.DirectionRepository;
import com.location.maps.respository.UserRepository;
import com.location.maps.security.CurrentUser;
import com.location.maps.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class UserService {
    final
    DirectionRepository directionRepository;

    final
    UserRepository userRepository;
    public UserService(DirectionRepository directionRepository, UserRepository userRepository) {
        this.directionRepository = directionRepository;
        this.userRepository = userRepository;
    }

    public User createFavoriteDirections(UserPrincipal userPrincipal, String username, Long directionId) {
        Optional<Direction> direction = directionRepository.findById(directionId);
        if(direction == null){
            throw new AppException("Username not found");
        }
        Optional<User> user = userRepository.findByUsername(userPrincipal.getUsername());
        if(user == null){
            throw new AppException("Username not found");
        }

        User usermodel = user.get();
        Direction directionmodel = direction.get();

        usermodel.getDirections().add(directionmodel);
        return userRepository.save(usermodel);
    }
}
