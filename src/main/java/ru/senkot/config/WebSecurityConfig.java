//package ru.senkot.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import ru.senkot.servicies.UserService;
//import sun.plugin2.applet.context.NoopExecutionContext;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableWebSecurity
//@ComponentScan("ru.senkot.security")
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
////    @Autowired
////    private AuthProvider authProvider;
//
//    @Autowired
//    private DataSource dataSource;
//
////    @Bean
////    public BCryptPasswordEncoder bCryptPasswordEncoder() {
////        return new BCryptPasswordEncoder();
////    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                    .antMatchers("/", "/about", "/registration").permitAll()
//                    .antMatchers().authenticated()
//                .and()
//                    .formLogin().loginPage("/login")
//                    .permitAll()
//                .and().logout().permitAll();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication().dataSource(dataSource)
//                .passwordEncoder(NoOpPasswordEncoder.getInstance())
//                .usersByUsernameQuery("select username, password from User where username=?")
//                .authoritiesByUsernameQuery("select u.username, ur.roles from User u inner join user_role ur on u.id = ur.user_id where u.username=?");
//    }
//}
