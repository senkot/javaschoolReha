package ru.senkot.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.senkot.DTO.PrescriptionDTO;

import java.util.Calendar;
import java.util.GregorianCalendar;

@Component
public class PrescriptionDTOValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return PrescriptionDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        PrescriptionDTO prescriptionDTO = (PrescriptionDTO) o;

        // validate dates
        if (prescriptionDTO.getDateOfStart() != null && prescriptionDTO.getDateOfEnd() != null) {

            Calendar dateOfStart = new GregorianCalendar();
            dateOfStart.setTime(prescriptionDTO.getDateOfStart());
            Calendar dateOfEnd = new GregorianCalendar();
            dateOfEnd.setTime(prescriptionDTO.getDateOfEnd());
            Calendar today = Calendar.getInstance();
            today.roll(Calendar.DATE, -1);

            if (dateOfStart.after(dateOfEnd)) errors.rejectValue("dateOfEnd", "", "dateOfEnd earlier");
//            if (dateOfStart.before(today)) errors.rejectValue("dateOfStart", "", "dateOfStart past");

        } else if (prescriptionDTO.getDateOfStart() == null && prescriptionDTO.getDateOfEnd() != null) {
            errors.rejectValue("dateOfStart", "", "dateOfStart blank");
        } else if (prescriptionDTO.getDateOfEnd() == null && prescriptionDTO.getDateOfStart() != null) {
            errors.rejectValue("dateOfEnd", "", "dateOfEnd blank");
        } else if (prescriptionDTO.getDateOfStart() == null && prescriptionDTO.getDateOfEnd() == null) {
            errors.rejectValue("dateOfStart", "", "dateOfStart blank");
            errors.rejectValue("dateOfEnd", "", "dateOfEnd blank");
        }

        if (prescriptionDTO.getRemedyType() != null && !prescriptionDTO.getRemedyType().equals("pill")
                && !prescriptionDTO.getRemedyType().equals("procedure")) {
            errors.rejectValue("remedyType", "", "remedyType error");
        } else if (prescriptionDTO.getRemedyType() == null) errors
                .rejectValue("remedyType", "", "remedyType blank");

        if (prescriptionDTO.getQuantity() < 1) errors.rejectValue("quantity", "", "quantity low");
        if (prescriptionDTO.getRemedyType().equals("procedure") && prescriptionDTO.getQuantity() != 1) errors
                .rejectValue("quantity", "", "quantity procedure");

        if (prescriptionDTO.getMonday() == null && prescriptionDTO.getTuesday() == null
                && prescriptionDTO.getWednesday() == null && prescriptionDTO.getThursday() == null
                && prescriptionDTO.getFriday() == null && prescriptionDTO.getSaturday() == null
                && prescriptionDTO.getSunday() == null) errors
                .rejectValue("sunday", "", "weekDays blank");

        if (prescriptionDTO.getMorning() == null && prescriptionDTO.getAfternoon() == null
                && prescriptionDTO.getEvening() == null && prescriptionDTO.getNight() == null) errors
                .rejectValue("night", "", "dayTime blank");

    }
}
