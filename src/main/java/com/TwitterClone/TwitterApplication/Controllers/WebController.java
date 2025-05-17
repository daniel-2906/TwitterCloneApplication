package com.TwitterClone.TwitterApplication.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class WebController {
	 @GetMapping(value={ "/", "/login", "/signup", "/notifications", "/profile/**"})
	    public ModelAndView login() {
	        return new ModelAndView("forward:/index.html"); 
	 }
	 @RequestMapping(value = "/{path:[^\\.]*}")
	 public String redirect() {
	        return "forward:/index.html";
	    }

}
