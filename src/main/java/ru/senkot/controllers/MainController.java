package ru.senkot.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.senkot.servicies.UserService;

@Controller
public class MainController {

    private static final Logger logger = Logger.getLogger(MainController.class);

    @Autowired
    private UserService userService;

    @GetMapping(value = "/about")
    public ModelAndView getAboutPage() {
        logger.debug("getAboutPage on mapping /about is executed");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("about");
        return mav;
    }

    @GetMapping(value = "/")
    public ModelAndView getIndexPage() {
        logger.debug("getIndexPAge on mapping / is executed");

        return userService.getMavForMain();
    }

}
