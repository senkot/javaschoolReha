package ru.senkot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

//        User.UserBuilder users = User.withDefaultPasswordEncoder();
//
//        auth.inMemoryAuthentication()
//                .withUser(users.username("john").password("test123").roles("DOCTOR"))
//                .withUser(users.username("mary").password("test123").roles("NURSE"))
//                .withUser(users.username("admin").password("admin").roles("DOCTOR", "NURSE", "ADMIN"));

        auth.jdbcAuthentication().dataSource(dataSource);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                    .antMatchers("/", "/about").permitAll()
                    .antMatchers("/patient-list/**", "/patient/**", "/patient-form/**"
                    , "/prescription-list/**", "/prescription/**", "/prescription-form/**").hasRole("DOCTOR")
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
