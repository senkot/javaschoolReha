package ru.senkot.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.senkot.DTO.PatientDTO;
import ru.senkot.entities.Patient;
import ru.senkot.exception.IdNotFoundException;
import ru.senkot.servicies.PatientService;
import ru.senkot.servicies.UserService;
import ru.senkot.validation.PatientDTOValidator;

import javax.validation.Valid;
import java.util.Objects;

@Controller
public class PatientController {

    private static final Logger logger = Logger.getLogger(PatientController.class);

    @Autowired
    private PatientService patientService;

    @Autowired
    private UserService userService;

    @Autowired
    private PatientDTOValidator patientDTOValidator;


    @GetMapping(value = "/patient-list")
    public ModelAndView patientList() {
        logger.debug("patientList on mapping /patient-list is executed");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("patient-list");
        mav.addObject("patients", patientService.selectAllPatients());
        return mav;
    }

    @GetMapping(value = "/add")
    public ModelAndView patientForm() {
        logger.debug("patientForm on mapping /add is executed");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("patient-form");
        mav.addObject("user", userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        return mav;
    }

    @PostMapping(value = "/add")
    public ModelAndView addPatientForm(@Valid @ModelAttribute("patientDTO") PatientDTO patientDTO, BindingResult result) {
        logger.debug("addPatientForm on mapping /add is executed");
        ModelAndView mav = new ModelAndView();

        patientDTOValidator.validate(patientDTO, result);

        if (result.hasErrors()) {
            if (result.hasFieldErrors("insurance")) {
                if (result.getFieldError("insurance").toString().equals("insurance error"))
                    mav.addObject("existedPatientId", patientService
                            .selectPatientByInsurance(patientDTO.getInsurance()).getId());
            }
            mav.setViewName("patient-form");
            mav.addObject("user", userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
            mav.addObject("errors", result.getAllErrors());
            return mav;
        } else {
            mav.setViewName("patient-list");
            patientService.insertPatient(patientService.patientFromPatientDTOForInsert(patientDTO));
            mav.addObject("patients", patientService.selectAllPatients());
        }
        return mav;
    }

    @GetMapping(value = "/edit")
    public ModelAndView editPatient(@ModelAttribute("id") int id) throws IdNotFoundException {
        logger.debug("editPatient on mapping /edit is executed");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("patient-form");
        mav.addObject("patient", patientService.selectPatient(id));
        mav.addObject("user", userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        return mav;
    }

    @PostMapping(value = "/edit")
    public ModelAndView postEditPatient(@Valid @ModelAttribute("patientDTO") PatientDTO patientDTO,
                                        BindingResult result) {
        logger.debug("postEditPatient on mapping /edit is executed");
        ModelAndView mav = new ModelAndView();

        patientDTOValidator.validate(patientDTO, result);

        if (result.hasErrors()) {
            if (result.hasFieldErrors("insurance")) {
                if (result.getFieldError("insurance").toString().equals("insurance error"))
                    mav.addObject("existedPatientId", patientService
                            .selectPatientByInsurance(patientDTO.getInsurance()).getId());
            }
            mav.setViewName("patient-form");
            mav.addObject("patient", patientService.selectPatient(patientDTO.getPatientId()));
            mav.addObject("user", userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
            mav.addObject("errors", result.getAllErrors());
            return mav;
        } else {
            mav.setViewName("patient");
            patientService.updatePatient(patientService.patientFromPatientDTOForUpdate(patientDTO));
            mav.addObject("patient", patientService.selectPatient(patientDTO.getPatientId()));
        }
        return mav;
    }

    @GetMapping(value = "/patient")
    public ModelAndView getPatient(@ModelAttribute("id") int id) throws IdNotFoundException {
        logger.debug("getPatient on mapping /patient is executed");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("patient");
        Patient patient = patientService.selectPatient(id);
        if (patient != null) {
            mav.addObject("patient", patientService.selectPatient(id));
        } else {
            throw new IdNotFoundException(id);
        }
        return mav;
    }

    @PostMapping(value = "/patient")
    public ModelAndView editPatientState(@ModelAttribute("patientDTO") PatientDTO patientDTO) {
        logger.debug("editPatientState on mapping /patient is executed");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("patient");
        patientService.setPatientStateFromDTO(patientDTO);
        patientService.changeStatusesFromPatientDischarge(patientDTO);
        mav.addObject("patient", patientService.selectPatient(patientDTO.getPatientId()));
        return mav;
    }

}
