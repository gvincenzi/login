package org.sde.login.model.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.sde.login.LoginApplication;
import org.sde.login.model.entity.User;
import org.sde.login.model.repository.UserRepository;
import org.sde.login.service.ServiceException;
import org.sde.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Optional;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LoginApplication.class})
@ActiveProfiles("test")
public class UserServiceTest {
    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepositoryMock;

    static User user;

    private static Optional<User> getUserMock(){
        user = new User();
        user.setUsername("john.doe");
        user.setPassword("pass");
        user.setId(new Random().nextLong());
        user.setLastLogin(Instant.now());

        return Optional.of(user);
    }

    @Before
    public void init(){
        Mockito.when(userRepositoryMock.findByUsernameAndPassword("john.doe","pass")).thenReturn(getUserMock());
    }

    @Test
    public void getLastLoginTest() throws ServiceException {
        Instant lastLogin = userService.getLastLogin("john.doe", "pass");

        Assert.assertEquals(user.getLastLogin(),lastLogin);
    }

    @Test(expected = ServiceException.class)
    public void getLastLoginTest1() throws ServiceException {
        userService.getLastLogin(null, "pass");
    }

    @Test(expected = ServiceException.class)
    public void getLastLoginTest2() throws ServiceException {
        userService.getLastLogin(null, null);
    }

    @Test(expected = ServiceException.class)
    public void getLastLoginTest3() throws ServiceException {
        userService.getLastLogin("john.doe", null);
    }

    @Test(expected = ServiceException.class)
    public void getLastLoginTest4() throws ServiceException {
        userService.getLastLogin("john.doe2", "pass");
    }
}
