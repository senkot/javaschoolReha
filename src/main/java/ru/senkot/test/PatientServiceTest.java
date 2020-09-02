package ru.senkot.test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.BaseMatcher.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ru.senkot.config.TestBeanConfig;
import ru.senkot.config.WebConfig;
import ru.senkot.servicies.PatientService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestBeanConfig.class, loader = AnnotationConfigContextLoader.class)
public class PatientServiceTest {

    @Autowired
    private PatientService patientService;

    @Test
    public void testPatientService() {
        assertEquals("class ru.senkot.servicies.PatientService",
                this.patientService.getClass().toString());
    }

    @Test
    public void patientFromPatientDTOForUpdate() {

    }

    @Test
    public void patientFromPatientDTOForInsert() {
    }

    @Test
    public void setPatientStateFromDTO() {
    }

    @Test
    public void changeStatusesFromPatientDischarge() {
    }
}