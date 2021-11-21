package com.example.twitterclone.controller;

import com.example.twitterclone.domain.User;
import com.example.twitterclone.repos.MessageRepository;
import com.example.twitterclone.repos.UserRepo;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private MessageRepository messageRepo;


	/**
	 * В WebSecurityConfig прописано, что доступ по этому URl разрешен только пользователям ADMIN.
	 * Здесь мы получаем текущего пользователя, получаем список из всех пользователей и добавляем этот список в model для отображения на экране
	 */
	@GetMapping("/")
	public String adminDash(@AuthenticationPrincipal User user, Model model) {
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

	@GetMapping("/edit/{user}")
	public String editUser(@PathVariable User user, Model model) {
		model.addAttribute("user", user);
		return "edit_user";
	}

	@PostMapping("/{user}")
	public String editUser(@PathVariable User user){
		if(user != null){
			messageRepo.deleteAllByAuthor(user);
			userRepo.deleteById(user.getId());
		}
		return "redirect:/admin/";
	}
}
