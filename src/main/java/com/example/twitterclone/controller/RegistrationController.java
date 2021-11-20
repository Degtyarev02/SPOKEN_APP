package com.example.twitterclone.controller;

import com.example.twitterclone.domain.Message;
import com.example.twitterclone.domain.Role;
import com.example.twitterclone.domain.User;
import com.example.twitterclone.repos.MessageRepository;
import com.example.twitterclone.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
public class RegistrationController {

	@Autowired
	private UserRepo userRepo;

	@GetMapping("/registration")
	public String registration() {
		return "registration";
	}

	@PostMapping("/registration")
	public String addUser(User user, Model model) {
		User userFromDB = userRepo.findByUsername(user.getUsername());
		if (userFromDB != null) {
			model.addAttribute("message", "user is already exist");
			return "registration";
		}
		user.setActive(true);
		user.setRoles(Collections.singleton(Role.USER));
		userRepo.save(user);

		return "redirect:/login";

	}


}