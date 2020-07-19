package ru.senkot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.senkot.entities.Patient;
import ru.senkot.servicies.PatientService;

@Controller
public class PatientController {

    @Autowired
    private PatientService patientService;


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
        return mav;
    }

    @PostMapping(value = "/add")
    public ModelAndView addPatientForm(@ModelAttribute("patient") Patient patient) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("patient-list");
        patientService.insertPatient(patient);
        mav.addObject("patients", patientService.selectAllPatients());
        return mav;
    }

    @GetMapping(value = "/edit")
    public ModelAndView editPatient(@ModelAttribute("id") int id) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("patient-form");
        mav.addObject("patient", patientService.selectPatient(id));
        return mav;
    }

    @PostMapping(value = "/edit")
    public ModelAndView postEditPatient(@ModelAttribute("patient") Patient patient) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("patient");
        patientService.updatePatient(patient);
        mav.addObject("patient", patientService.selectPatient(patient.getId()));
        return mav;
    }

    @GetMapping(value = "/patient")
    public ModelAndView getPatient(@ModelAttribute("id") int id) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("patient");
        mav.addObject("patient", patientService.selectPatient(id));
        return mav;
    }

    @GetMapping(value = "/event-list")
    public ModelAndView eventList() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("event-list");
        return mav;
    }

}
