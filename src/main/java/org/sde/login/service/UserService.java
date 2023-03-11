package org.sde.login.service;

import java.time.Instant;

public interface UserService {
    Instant getLastLogin(String username, String password) throws ServiceException;
}
