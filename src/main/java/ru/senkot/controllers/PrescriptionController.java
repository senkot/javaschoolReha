package ru.senkot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.senkot.DTO.PrescriptionDTO;
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

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private EventService eventService;

    @GetMapping(value = "/prescription-list")
    public ModelAndView prescriptionList(@ModelAttribute("id") int id) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("prescription-list");
        mav.addObject("patient", patientService.selectPatient(id));
        mav.addObject("prescriptions", prescriptionService.selectAllPrescriptionsById(id));
        return mav;
    }

    @GetMapping(value = "/add-prescription")
    public ModelAndView addPrescription(@ModelAttribute("id") int id) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("prescription-form");
        mav.addObject("patient", patientService.selectPatient(id));
        return mav;
    }

    @GetMapping(value = "/edit-prescription")
    public ModelAndView editPrescription(@ModelAttribute("id") int id) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("prescription-form");
        Prescription prescription = prescriptionService.selectPrescription(id);
        mav.addObject("prescription", prescription);
        mav.addObject("patient", patientService.selectPatient(prescription.getPatient().getId()));
        return mav;
    }

    @PostMapping(value = "/edit-prescription")
    public ModelAndView editPrescriptionForm(@ModelAttribute("prescriptionDTO") PrescriptionDTO prescriptionDTO) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("prescription-list");
        prescriptionService.updatePrescription(prescriptionService.getPrescriptionFromDTOForUpdate(prescriptionDTO));
        mav.addObject("patient", patientService.selectPatient(prescriptionDTO.getPatientId()));
        mav.addObject("prescriptions", prescriptionService.selectAllPrescriptionsById(prescriptionDTO.getPatientId()));
        return mav;
    }

    @PostMapping(value = "/add-prescription")
    public ModelAndView addPrescriptionForm(@ModelAttribute("prescriptionDTO") PrescriptionDTO prescriptionDTO) {
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
        ModelAndView mav = new ModelAndView();
        mav.setViewName("prescription");
        Prescription prescription = prescriptionService.selectPrescription(id);
        mav.addObject("prescription", prescription);
        mav.addObject("patient", patientService.selectPatient(prescription.getPatient().getId()));
        return mav;
    }

}
