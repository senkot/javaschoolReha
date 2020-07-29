package ru.senkot.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.senkot.DAO.UserDAO;
import ru.senkot.entities.User;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public boolean deleteUser (User user) {
        User userFromDB = userDAO.selectUser(user.getId());
        if (userFromDB != null) {
            userDAO.deleteUser(user);
            return true;
        }
        return false;
    }

    @Transactional
    public List<User> selectAllUsers() {
        return userDAO.selectAllUsers();
    }

    @Transactional
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.insertUser(user);
    }


}
