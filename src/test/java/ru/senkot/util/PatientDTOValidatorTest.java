package ru.senkot.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import ru.senkot.DAO.UserDAO;
import ru.senkot.entities.User;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PatientDTOValidatorTest {

    @Test
    public void findByUsernameTest() {
        String name = "test";
        UserDAO userDAO = mock(UserDAO.class);
        User user = new User();
        user.setUsername(name);
        user.setPassword("password");
        user.setFullName("full_name");
        when(userDAO.findByUsername(name)).thenReturn(user);
        String userName = userDAO.findByUsername(name).getUsername();

        assertEquals(name, userName);
    }
}
