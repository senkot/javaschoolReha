package ru.senkot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.senkot.servicies.PatientService;

@Configuration
@ComponentScan(basePackages = "ru.senkot")
public class TestBeanConfig {

    @Bean
    public PatientService getPatientService() {
        return new PatientService();
    }
}
