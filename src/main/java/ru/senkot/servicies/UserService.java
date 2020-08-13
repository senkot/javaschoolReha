package ru.senkot.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.senkot.DAO.UserDAO;
import ru.senkot.entities.User;

import javax.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Transactional
    public User findByUsername(String name) {
        return userDAO.findByUsername(name);
    }

    @Transactional
    public void insertUser(User user) {
        userDAO.insertUser(user);
    }

    @Transactional
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    @Transactional
    public void deleteUser(User user) {
        userDAO.deleteUser(user);
    }

}
