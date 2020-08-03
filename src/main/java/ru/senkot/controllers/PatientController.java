package ru.senkot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.senkot.DTO.PatientDTO;
import ru.senkot.entities.Patient;
import ru.senkot.servicies.PatientService;
import ru.senkot.servicies.UserService;

@Controller
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private UserService userService;


    @GetMapping(value = "/patient-list")
    public ModelAndView patientList() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("patient-list");
        mav.addObject("patients", patientService.selectAllPatients());
        return mav;
    }

    @GetMapping(value = "/add")
    public ModelAndView patientForm() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("patient-form");
        mav.addObject("user", userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        return mav;
    }

    @PostMapping(value = "/add")
    public ModelAndView addPatientForm(@ModelAttribute("patientDTO") PatientDTO patientDTO) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("patient-list");
        patientService.insertPatient(patientService.patientFromPatientDTOForInsert(patientDTO));
        mav.addObject("patients", patientService.selectAllPatients());
        return mav;
    }

    @GetMapping(value = "/edit")
    public ModelAndView editPatient(@ModelAttribute("id") int id) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("patient-form");
        mav.addObject("patient", patientService.selectPatient(id));
        mav.addObject("user", userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        return mav;
    }

    @PostMapping(value = "/edit")
    public ModelAndView postEditPatient(@ModelAttribute("patientDTO") PatientDTO patientDTO) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("patient");
        patientService.updatePatient(patientService.patientFromPatientDTOForUpdate(patientDTO));
        mav.addObject("patient", patientService.selectPatient(patientDTO.getPatientId()));
        return mav;
    }

    @GetMapping(value = "/patient")
    public ModelAndView getPatient(@ModelAttribute("id") int id) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("patient");
        mav.addObject("patient", patientService.selectPatient(id));
        return mav;
    }

}
