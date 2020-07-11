package ru.senkot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @GetMapping(value = "/patient-list")
    public ModelAndView patientList() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("patient-list");
        return mav;
    }

}
