package ru.senkot.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import ru.senkot.DAO.UserDAO;
import ru.senkot.entities.User;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Transactional
    public User findByUsername(String name) {
        return userDAO.findByUsername(name);
    }

    /**
     * This method find the user by username, put user object in model if it exist.
     *
     * @return ModelAndView instance with the name of view page and instance of user (or without it)
     */
    public ModelAndView getMavForMain() {
        ModelAndView mav = new ModelAndView("index");

        User user = findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user != null) {
            mav.addObject("user", user);
        }

        return mav;
    }

}
