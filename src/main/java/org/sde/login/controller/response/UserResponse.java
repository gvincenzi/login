package org.sde.login.controller.response;

import org.sde.login.model.entity.User;

import java.time.Instant;

public class UserResponse {
    private Long id;
    private String username;
    private Instant lastLogin;

    public UserResponse(User user){
        this.setId(user.getId());
        this.setUsername(user.getUsername());
        this.setLastLogin(user.getLastLogin());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Instant getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Instant lastLogin) {
        this.lastLogin = lastLogin;
    }
}
