package com.zr.blog.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class ControllerException {


    @ExceptionHandler(ArithmeticException.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) {
        log.error("Request URL : {}, Exception : {}", request.getRequestURL(), e);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("url", request.getRequestURL());
        modelAndView.addObject("exception", e);
        modelAndView.setViewName("error/error");
        return modelAndView;
    }




}
