package ru.senkot.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.senkot.DTO.PrescriptionDTO;
import ru.senkot.entities.Prescription;
import ru.senkot.messaging.MessageSender;
import ru.senkot.servicies.EventService;
import ru.senkot.servicies.PatientService;
import ru.senkot.servicies.PrescriptionService;
import ru.senkot.validation.PrescriptionDTOValidator;

import javax.validation.Valid;
import java.util.Set;

@Controller
public class PrescriptionController {

    private static final Logger logger = Logger.getLogger(PrescriptionController.class);

    @Autowired
    private PrescriptionDTOValidator prescriptionDTOValidator;

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private EventService eventService;

    @Autowired
    MessageSender messageSender;

    @GetMapping(value = "/prescription-list")
    public ModelAndView prescriptionList(@ModelAttribute("id") int id) {
        logger.debug("prescriptionList on mapping /prescription-list is executed");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("prescription-list");
        mav.addObject("patient", patientService.findPatientById(id));
        mav.addObject("prescriptions", prescriptionService.findAllPrescriptionsByPatientId(id));
        return mav;
    }

    @GetMapping(value = "prescription-check")
    public ModelAndView checkPrescriptions(@ModelAttribute("id") int id) {
        logger.debug("checkPrescriptions on mapping /prescription-check is executed");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("prescription-list");
        prescriptionService.checkPrescriptionsByPatientId(id);
        mav.addObject("patient", patientService.findPatientById(id));
        mav.addObject("prescriptions", prescriptionService.findAllPrescriptionsByPatientId(id));
        return mav;
    }

    @GetMapping(value = "/add-prescription")
    public ModelAndView addPrescription(@ModelAttribute("id") int id) {
        logger.debug("addPrescription on mapping /add-prescription is executed");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("prescription-form");
        mav.addObject("patient", patientService.findPatientById(id));
        return mav;
    }

    @GetMapping(value = "/edit-prescription")
    public ModelAndView editPrescription(@ModelAttribute("id") int id) {
        logger.debug("editPrescription on mapping /edit-prescription is executed");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("prescription-form");
        Prescription prescription = prescriptionService.findPrescriptionById(id);
        mav.addObject("prescription", prescription);
        mav.addObject("patient", patientService.findPatientById(prescription.getPatient().getId()));
        return mav;
    }

    @PostMapping(value = "/edit-prescription")
    public ModelAndView editPrescriptionForm(@Valid @ModelAttribute("prescriptionDTO") PrescriptionDTO prescriptionDTO,
                                             BindingResult result) {
        logger.debug("editPrescriptionForm on mapping /edit-prescription is executed");
        ModelAndView mav = new ModelAndView();
        prescriptionDTOValidator.validateEdit(prescriptionDTO, result);

        if (result.hasErrors()) {
            mav.setViewName("prescription-form");
            mav.addObject("errors", result.getAllErrors());
            mav.addObject("prescription", prescriptionService.findPrescriptionById(prescriptionDTO.getPrescriptionId()));
            mav.addObject("patient", patientService.findPatientById(prescriptionDTO.getPatientId()));
            mav.addObject("prescriptionDTO", prescriptionDTO);
        } else {
            Set<String> collisions = eventService.overlapEventsFromPrescriptionMapForEdit(prescriptionDTO);
            if (collisions == null || collisions.isEmpty()) {
                mav.setViewName("prescription-list");

                prescriptionService.updatePrescription(prescriptionService.getPrescriptionFromDTOForUpdate(prescriptionDTO));
                eventService.changeEventStatusForPrescriptionIdByPrescriptionId(prescriptionDTO.getPrescriptionId());
                eventService.generateAndInsertEvents(prescriptionDTO);

                mav.addObject("patient", patientService
                        .findPatientById(prescriptionDTO.getPatientId()));
                mav.addObject("prescriptions", prescriptionService
                        .findAllPrescriptionsByPatientId(prescriptionDTO.getPatientId()));
                messageSender.sendMessage("DB updated. Prescription has been changed");

            } else {
                mav.setViewName("prescription-form");
                mav.addObject("collisions", collisions);
                mav.addObject("prescription", prescriptionService.findPrescriptionById(prescriptionDTO.getPatientId()));
                mav.addObject("patient", patientService.findPatientById(prescriptionDTO.getPatientId()));
                mav.addObject("prescriptionDTO", prescriptionDTO);
            }
        }
        return mav;
    }

    @PostMapping(value = "/add-prescription")
    public ModelAndView addPrescriptionForm(@Valid @ModelAttribute("prescriptionDTO") PrescriptionDTO prescriptionDTO,
                                            BindingResult result) {
        logger.debug("addPrescriptionForm on mapping /add-prescription is executed");
        ModelAndView mav = new ModelAndView();

        prescriptionDTOValidator.validate(prescriptionDTO, result);

        if (result.hasErrors()) {
            mav.setViewName("prescription-form");
            mav.addObject("errors", result.getAllErrors());
            mav.addObject("patient", patientService.findPatientById(prescriptionDTO.getPatientId()));
            mav.addObject("prescriptionDTO", prescriptionDTO);
        } else {
            Set<String> collisions = eventService.overlapEventsFromPrescriptionMap(prescriptionDTO);
            if (collisions == null || collisions.isEmpty()) {
                mav.setViewName("prescription-list");
                prescriptionService.savePrescription(prescriptionService.getPrescriptionForInsert(prescriptionDTO));

                prescriptionDTO.setPrescriptionId(prescriptionService
                        .getLastInsertedPrescriptionIdForPatient(prescriptionDTO.getPatientId()));
                eventService.generateAndInsertEvents(prescriptionDTO);
                mav.addObject("patient", patientService
                        .findPatientById(prescriptionDTO.getPatientId()));
                mav.addObject("prescriptions", prescriptionService
                        .findAllPrescriptionsByPatientId(prescriptionDTO.getPatientId()));
                messageSender.sendMessage("DB updated. New prescription has been added");

            } else {
                mav.setViewName("prescription-form");
                mav.addObject("collisions", collisions);
                mav.addObject("patient", patientService.findPatientById(prescriptionDTO.getPatientId()));
                mav.addObject("prescriptionDTO", prescriptionDTO);
            }
        }
        return mav;
    }

    @GetMapping(value = "/prescription")
    public ModelAndView getPrescription(@ModelAttribute("id") int id) {
        logger.debug("getPrescription on mapping /prescription is executed");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("prescription");
        Prescription prescription = prescriptionService.findPrescriptionById(id);
        mav.addObject("prescription", prescription);
        mav.addObject("patient", patientService.findPatientById(prescription.getPatient().getId()));
        return mav;
    }

    @PostMapping(value = "/prescription")
    public ModelAndView editPrescriptionStatus(@ModelAttribute("prescriptionDTO") PrescriptionDTO prescriptionDTO) {
        logger.debug("editPrescriptionStatus on mapping /prescription is executed");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("prescription");
        prescriptionService.updatePrescriptionStatus(prescriptionDTO);
        Prescription prescription = prescriptionService.findPrescriptionById(prescriptionDTO.getPrescriptionId());
        prescriptionService.setStatusPrescriptionToEvent(prescriptionDTO);
        mav.addObject("prescription", prescription);
        mav.addObject("patient", prescription.getPatient());
        messageSender.sendMessage("DB updated. Prescription's status has been changed");
        return mav;
    }

    @GetMapping(value = "/prescription-show")
    public ModelAndView getPrescriptionShow(@ModelAttribute("id") int id) {
        logger.debug("getPrescriptionShow on mapping /prescription-show is executed");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("prescription-show");
        Prescription prescription = prescriptionService.findPrescriptionById(id);
        mav.addObject("prescription", prescription);
        mav.addObject("patient", patientService.findPatientById(prescription.getPatient().getId()));
        mav.addObject("events", eventService.findAllEventsByPrescriptionId(id));
        return mav;
    }

}
