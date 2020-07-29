//package ru.senkot.validation;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.Errors;
//import org.springframework.validation.Validator;
//import ru.senkot.entities.User;
//import ru.senkot.servicies.UserService;
//
//@Component
//public class UserValidation implements Validator {
//
//    @Autowired
//    private UserService userService;
//
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return User.class.equals(clazz);
//    }
//
//    @Override
//    public void validate(Object o, Errors errors) {
//        User user = (User) o;
//        if (userService.findByUsername(user.getUsername()) != null) {
//            errors.rejectValue("username", "This username exists!");
//        }
//    }
//}
