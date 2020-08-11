package ru.senkot.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.senkot.DTO.PatientDTO;
import ru.senkot.entities.Patient;
import ru.senkot.servicies.PatientService;

import java.util.Calendar;
import java.util.GregorianCalendar;

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

        // validation existing Patient by insurance
        PatientDTO patientDTO = (PatientDTO) o;
        if (patientService.selectPatientByInsurance(patientDTO.getInsurance()) != null) {
            errors.rejectValue("insurance", "", "insurance error");
        }

        // validation age of Patient
        if (patientDTO.getDateOfBirth() != null) {
            Calendar birthDateDTO = new GregorianCalendar();
            birthDateDTO.setTime(patientDTO.getDateOfBirth());
            Calendar youngPermitBirthday = Calendar.getInstance();
            youngPermitBirthday.roll(Calendar.YEAR, -18);

            Calendar oldPermitBirthday = Calendar.getInstance();
            oldPermitBirthday.roll(Calendar.YEAR, -150);

            Calendar now = new GregorianCalendar();

            if (birthDateDTO.after(youngPermitBirthday) && birthDateDTO.before(now)) errors
                    .rejectValue("dateOfBirth", "", "date young");
            if (birthDateDTO.before(oldPermitBirthday)) errors.rejectValue("dateOfBirth", "", "date old");
            if (birthDateDTO.after(now)) errors.rejectValue("dateOfBirth", "", "date future");
        } else {
            errors.rejectValue("dateOfBirth", "", "dateOfBirth blank");
        }
    }
}
