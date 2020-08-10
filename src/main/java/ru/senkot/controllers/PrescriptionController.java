package ru.senkot.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.senkot.DTO.PrescriptionDTO;
import ru.senkot.entities.Event;
import ru.senkot.entities.Prescription;
import ru.senkot.servicies.EventService;
import ru.senkot.servicies.PatientService;
import ru.senkot.servicies.PrescriptionService;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class PrescriptionController {

    private static final Logger logger = Logger.getLogger(PrescriptionController.class);

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private EventService eventService;

    @GetMapping(value = "/prescription-list")
    public ModelAndView prescriptionList(@ModelAttribute("id") int id) {
        logger.debug("prescriptionList on mapping /prescription-list is executed");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("prescription-list");
        mav.addObject("patient", patientService.selectPatient(id));
        mav.addObject("prescriptions", prescriptionService.selectAllPrescriptionsById(id));
        return mav;
    }

    @GetMapping(value = "prescription-check")
    public ModelAndView checkPrescriptions(@ModelAttribute("id") int id) {
        logger.debug("checkPrescriptions on mapping /prescription-check is executed");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("prescription-list");
        prescriptionService.checkPrescriptionsByPatientId(id);
        mav.addObject("patient", patientService.selectPatient(id));
        mav.addObject("prescriptions", prescriptionService.selectAllPrescriptionsById(id));
        return mav;
    }

    @GetMapping(value = "/add-prescription")
    public ModelAndView addPrescription(@ModelAttribute("id") int id) {
        logger.debug("addPrescription on mapping /add-prescription is executed");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("prescription-form");
        mav.addObject("patient", patientService.selectPatient(id));
        return mav;
    }

    @GetMapping(value = "/edit-prescription")
    public ModelAndView editPrescription(@ModelAttribute("id") int id) {
        logger.debug("editPrescription on mapping /edit-prescription is executed");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("prescription-form");
        Prescription prescription = prescriptionService.selectPrescription(id);
        mav.addObject("prescription", prescription);
        mav.addObject("patient", patientService.selectPatient(prescription.getPatient().getId()));
        return mav;
    }

    @PostMapping(value = "/edit-prescription")
    public ModelAndView editPrescriptionForm(@ModelAttribute("prescriptionDTO") PrescriptionDTO prescriptionDTO) {
        logger.debug("editPrescriptionForm on mapping /edit-prescription is executed");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("prescription-list");
        prescriptionService.updatePrescription(prescriptionService.getPrescriptionFromDTOForUpdate(prescriptionDTO));
        mav.addObject("patient", patientService.selectPatient(prescriptionDTO.getPatientId()));
        mav.addObject("prescriptions", prescriptionService.selectAllPrescriptionsById(prescriptionDTO.getPatientId()));
        return mav;
    }

    @PostMapping(value = "/add-prescription")
    public ModelAndView addPrescriptionForm(@ModelAttribute("prescriptionDTO") PrescriptionDTO prescriptionDTO) {
        logger.debug("addPrescriptionForm on mapping /add-prescription is executed");
        ModelAndView mav = new ModelAndView();
        Set<String> collisions = eventService.overlapEventsFromPrescriptionMap(prescriptionDTO);

        if (collisions == null || collisions.isEmpty()) {
            mav.setViewName("prescription-list");
            prescriptionService.insertPrescription(prescriptionService.getPrescriptionForInsert(prescriptionDTO));

            prescriptionDTO.setPrescriptionId(prescriptionService.getLastInsertedPrescriptionIdForPatient(prescriptionDTO.getPatientId()));
            eventService.generateAndInsertEvents(prescriptionDTO);
            mav.addObject("patient", patientService.selectPatient(prescriptionDTO.getPatientId()));
            mav.addObject("prescriptions", prescriptionService.selectAllPrescriptionsById(prescriptionDTO.getPatientId()));

        } else {
            mav.setViewName("prescription-form");
            mav.addObject("collisions", collisions);
            mav.addObject("patient", patientService.selectPatient(prescriptionDTO.getPatientId()));
        }
        return mav;
    }

    @GetMapping(value = "/prescription")
    public ModelAndView getPrescription(@ModelAttribute("id") int id) {
        logger.debug("getPrescription on mapping /prescription is executed");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("prescription");
        Prescription prescription = prescriptionService.selectPrescription(id);
        mav.addObject("prescription", prescription);
        mav.addObject("patient", patientService.selectPatient(prescription.getPatient().getId()));
        return mav;
    }

    @PostMapping(value = "/prescription")
    public ModelAndView editPrescriptionStatus(@ModelAttribute("prescriptionDTO") PrescriptionDTO prescriptionDTO) {
        logger.debug("editPrescriptionStatus on mapping /prescription is executed");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("prescription");
        prescriptionService.updatePrescriptionStatus(prescriptionDTO);
        Prescription prescription = prescriptionService.selectPrescription(prescriptionDTO.getPrescriptionId());
        prescriptionService.setStatusPrescriptionToEvent(prescriptionDTO);
        mav.addObject("prescription", prescription);
        mav.addObject("patient", prescription.getPatient());
        return mav;
    }

    @GetMapping(value = "/prescription-show")
    public ModelAndView getPrescriptionShow(@ModelAttribute("id") int id) {
        logger.debug("getPrescriptionShow on mapping /prescription-show is executed");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("prescription-show");
        Prescription prescription = prescriptionService.selectPrescription(id);
        mav.addObject("prescription", prescription);
        mav.addObject("patient", patientService.selectPatient(prescription.getPatient().getId()));
        mav.addObject("events", eventService.selectAllPlanedEventsByPrescriptionId(id));
        return mav;
    }

}
