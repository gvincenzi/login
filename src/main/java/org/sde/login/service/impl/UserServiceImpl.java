package org.sde.login.service.impl;

import org.sde.login.model.entity.User;
import org.sde.login.model.repository.UserRepository;
import org.sde.login.service.ServiceException;
import org.sde.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Instant getLastLogin(String username, String password) throws ServiceException {
        Optional<User> optionalUser = userRepository.findByUsernameAndPassword(username, password);
        if(username == null || password == null){
            throw new ServiceException("Username and password is mandatory");
        }

        if(optionalUser.isEmpty()){
            throw new ServiceException("User not found");
        }

        return optionalUser.get().getLastLogin();
    }
}
