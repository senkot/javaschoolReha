package ru.senkot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.senkot.DTO.PrescriptionDTO;
import ru.senkot.entities.Prescription;
import ru.senkot.servicies.PatientService;
import ru.senkot.servicies.PrescriptionService;

@Controller
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private PatientService patientService;

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
        Prescription prescription = new Prescription(patientService.selectPatient(prescriptionDTO.getPatientId()),
                prescriptionDTO.getRemedyName(),
                prescriptionDTO.getRemedyType(),
                prescriptionDTO.getDateOfStart(),
                prescriptionDTO.getDateOfEnd(),
                prescriptionDTO.getRepeat(),
                prescriptionDTO.getQuantity());
        prescriptionService.updatePrescription(prescription);
        mav.addObject("patient", patientService.selectPatient(prescription.getPatient().getId()));
        mav.addObject("prescriptions", prescriptionService.selectAllPrescriptionsById(prescription.getPatient().getId()));
        return mav;
    }

    @PostMapping(value = "/add-prescription")
    public ModelAndView addPrescriptionForm(@ModelAttribute("prescriptionDTO") PrescriptionDTO prescriptionDTO) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("prescription-list");
        Prescription prescription = new Prescription(patientService.selectPatient(prescriptionDTO.getPatientId()),
                prescriptionDTO.getRemedyName(),
                prescriptionDTO.getRemedyType(),
                prescriptionDTO.getDateOfStart(),
                prescriptionDTO.getDateOfEnd(),
                prescriptionDTO.getRepeat(),
                prescriptionDTO.getQuantity());
        prescriptionService.insertPrescription(prescription);
        mav.addObject("id", prescription.getPatient().getId());
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
