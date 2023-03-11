package org.sde.login.model.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sde.login.LoginApplication;
import org.sde.login.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LoginApplication.class})
@ActiveProfiles("test")
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    User user;
    User user2;

    @Before
    public void init(){
        userRepository.deleteAll();

        user = new User();
        user.setUsername("testUsername");
        user.setPassword("testPassword");
        userRepository.save(user);

        user2 = new User();
        user2.setUsername("testUsername2");
        user2.setPassword("testPassword2");
        userRepository.save(user2);
    }

    @Test
    public void findByUsernameAndPasswordTest1(){
        Optional<User> userAfterSave = userRepository.findByUsernameAndPassword("giuseppe","pass");

        Assert.assertFalse("User found with bad credentials", userAfterSave.isPresent());
    }

    @Test
    public void findByUsernameAndPasswordTest2(){
        Optional<User> userAfterSave = userRepository.findByUsernameAndPassword("testUsername","pass");

        Assert.assertFalse("User found with bad credentials", userAfterSave.isPresent());
    }

    @Test
    public void findByUsernameAndPasswordTest3(){
        Optional<User> userAfterSave = userRepository.findByUsernameAndPassword("giuseppe","testPassword");

        Assert.assertFalse("User found with bad credentials", userAfterSave.isPresent());
    }

    @Test
    public void findByUsernameAndPasswordTest4(){
        Optional<User> userAfterSave = userRepository.findByUsernameAndPassword("testUsername","testPassword");

        Assert.assertTrue("User not found", userAfterSave.isPresent());

        Assert.assertEquals(user.getUsername(),userAfterSave.get().getUsername());
        Assert.assertEquals(user.getPassword(),userAfterSave.get().getPassword());
    }
}
