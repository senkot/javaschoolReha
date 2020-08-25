package ru.senkot.exception;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;

@ControllerAdvice
public class ExceptionInterceptor {
    private static final Logger logger = Logger.getLogger(ExceptionInterceptor.class);

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handleError404(WebRequest request, Exception e) {
        logger.error("NO PAGE FOUND on request : " +  request.getContextPath() + " with Exception :" + e.getMessage());
        return new ModelAndView("errors/404");
    }

    @GetMapping(value = {"/404"})
    public String NotFoundPage() {
        return "errors/404";
    }

    @ExceptionHandler(SQLException.class)
    public ModelAndView handleSQLException() {
        logger.error("SQL Exception!");
        return new ModelAndView("errors/database-error");
    }

    @ExceptionHandler(NullPointerException.class)
    public ModelAndView handleNullPointerException() {
        logger.error("Null-pointer Exception!");
        return new ModelAndView("errors/database-error");
    }

    @ExceptionHandler(IdNotFoundException.class)
    public ModelAndView handleIdNotFoundException(HttpServletRequest request, Exception e) {
        ModelAndView mav = new ModelAndView("errors/error");
        logger.error("Id not found. URL :" + request.getRequestURL() + "?id=" + e.getMessage());
        mav.addObject("exception", e.getMessage());
        mav.addObject("url", request.getRequestURL());
        return mav;
    }

//    @ExceptionHandler(Exception.class)
//    public ModelAndView handleAnyOtherException() {
//        logger.error("Unknown exception. Check the log!");
//        return new ModelAndView("errors/unknown-error");
//    }

}
