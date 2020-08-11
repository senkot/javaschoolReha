package ru.senkot.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.senkot.DTO.PatientDTO;
import ru.senkot.entities.Patient;
import ru.senkot.servicies.PatientService;

@Component
public class PatientDTOValidator implements Validator {

    @Autowired
    private PatientService patientService;

    @Override
    public boolean supports(Class<?> aClass) {
        return PatientDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        PatientDTO patient = (PatientDTO) o;
        if (patientService.selectPatientByInsurance(patient.getInsurance()) != null) {
            errors.rejectValue("insurance", "", "PatientDTOValidator insurance error");
        }
    }
}
