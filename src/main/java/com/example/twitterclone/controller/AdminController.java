package com.example.twitterclone.controller;

import com.example.twitterclone.domain.User;
import com.example.twitterclone.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserRepo userRepo;

	@GetMapping("/")
	public String adminDash(@AuthenticationPrincipal User user, Model model){
		model.addAttribute("user", user);
		Iterable<User> usersList = userRepo.findAll();
		model.addAttribute("userlist", usersList);
		return "admin_dashboard";
	}

	@PostMapping("filter")
	public String searchByName(@AuthenticationPrincipal User user, @RequestParam String filter, Model model) {
		model.addAttribute("user", user);
		List<User> userList = userRepo.findAllByUsername(filter);
		model.addAttribute("userlist", userList);
		return "admin_dashboard";
	}
}
