package ru.senkot.servicies;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.senkot.DAO.UserDAO;
import ru.senkot.entities.User;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    UserDAO userDAO;

    @Test
    public void findByUsernameTest() {
        String name = "test";
        User user = new User();
        user.setUsername(name);
        user.setPassword("password");
        user.setFullName("full_name");
        when(userDAO.findByUsername(name)).thenReturn(user);

        String userName = userService.findByUsername(name).getUsername();

        assertEquals(name, userName);
    }
}
