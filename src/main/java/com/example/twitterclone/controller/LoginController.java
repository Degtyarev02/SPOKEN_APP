package com.example.twitterclone.controller;

import com.example.twitterclone.domain.User;
import com.example.twitterclone.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Valid;
import java.util.List;

@Controller
public class LoginController extends WebMvcConfigurerAdapter {

	@Autowired
	UserRepo userRepo;

	@GetMapping("/login")
	public String showForm() {
		return "login";
	}

	@PostMapping("/")
	public String validateLoginInfo(Model model, @Valid User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
			for (FieldError error : fieldErrorList) {
				model.addAttribute(error.getField()+"Error", error.getDefaultMessage());
			}
			return "login";
		}
		User ifExist = userRepo.findByUsername(user.getUsername());
		if(ifExist == null || !ifExist.getPassword().equals(user.getPassword())){
			return "login";
		}
		return "main";
	}
}
