package org.sde.login.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.sde.login.model.entity.User;
import org.sde.login.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LoginController.class)
@ActiveProfiles("test")
public class LoginControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    UserRepository userRepository;

    static User user;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Before
    public void init(){
        user = getUserMock();
        Mockito.when(userRepository.save(ArgumentMatchers.any(User.class))).then(AdditionalAnswers.returnsFirstArg());
    }

    @Test
    public void register() throws Exception {
        MvcResult mvcResult = mvc.perform(post("/user/register")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        User userResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);

        Assert.assertEquals(user.getUsername(),userResponse.getUsername());
        Assert.assertNotEquals(user.getPassword(),userResponse.getPassword());
    }

    @Test
    public void registerFail1() throws Exception {
        User userFail = getUserMock();
        userFail.setUsername(null);
        mvc.perform(post("/user/register")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userFail)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void registerFail2() throws Exception {
        User userFail = getUserMock();
        userFail.setPassword(null);
        mvc.perform(post("/user/register")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userFail)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    private User getUserMock(){
        User user = new User();
        user.setUsername("john.doe");
        user.setPassword("pass");
        user.setId(new Random().nextLong());

        return user;
    }
}
