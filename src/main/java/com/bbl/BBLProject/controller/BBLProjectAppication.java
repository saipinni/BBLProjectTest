package com.bbl.BBLProject.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class BBLProjectAppication {
    @RequestMapping("/")
    private ModelAndView home(Model model) {
        ModelAndView  modelAndView = new ModelAndView("home");
        return modelAndView;
    }
}
