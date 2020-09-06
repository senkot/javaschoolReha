package ru.senkot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                    .antMatchers("/", "/about", "/test/**").permitAll()
                    .antMatchers("/patient-list/**", "/patient/**", "/patient-form/**"
                    , "/prescription-list/**", "/prescription/**", "/prescription-form/**", "/add", "/edit"
                    , "/add-prescription", "/edit-prescription").hasRole("DOCTOR")
                    .antMatchers("/event-list/**", "/event/**").hasAnyRole("NURSE", "DOCTOR")
                .and()
                .formLogin()
                    .loginPage("/login-page")
                    .loginProcessingUrl("/authenticateTheUser")
                    .permitAll()
                .and()
                    .logout().logoutSuccessUrl("/").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");

    }
}
