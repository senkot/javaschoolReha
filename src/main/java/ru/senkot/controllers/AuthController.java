//package ru.senkot.controllers;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import ru.senkot.entities.User;
//import ru.senkot.servicies.UserService;
//import ru.senkot.validation.UserValidation;
//
//import javax.validation.Valid;
//
//@Controller
//public class AuthController {
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private UserValidation userValidation;
//
//    @GetMapping(value = "/sign-up")
//    public String getSignUp(Model model) {
//        model.addAttribute("user", new User());
//        return "/auth/sign-up";
//    }
//
//    @PostMapping(value = "/sign-up")
//    public String signUp(@ModelAttribute @Valid User user, BindingResult bindingResult) {
//        userValidation.validate(user, bindingResult);
//        if (bindingResult.hasErrors()) return "/auth/sign-up";
//
//        userService.saveUser(user);
//        return "redirect:/";
//    }
//
//    @RequestMapping(value = "/login")
//    public String login(@RequestParam(name = "error", required = false) Boolean error, Model model) {
//        if (Boolean.TRUE.equals(error)) model.addAttribute("error", true);
//
//        return "/auth/sign-in";
//    }
//
//}
