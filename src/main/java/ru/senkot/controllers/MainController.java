package ru.senkot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.senkot.servicies.PatientService;

@Controller
public class MainController {

    @Autowired
    private PatientService patientService;

    @GetMapping(value = "/patient-list")
    public ModelAndView patientList() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("patient-list");
        mav.addObject("patients", patientService.selectAllPatients());
        return mav;
    }

    @GetMapping(value = "/patient-form")
    public ModelAndView patientForm() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("patient-form");
        return mav;
    }

    @GetMapping(value = "/patient")
    public ModelAndView getPatient() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("patient");
        return mav;
    }

    @GetMapping(value = "/edit")
    public ModelAndView editPatient() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("patient-form");
        return mav;
    }

}
