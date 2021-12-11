package com.example.twitterclone.controller;

import com.example.twitterclone.domain.Role;
import com.example.twitterclone.domain.User;
import com.example.twitterclone.repos.CommentRepository;
import com.example.twitterclone.repos.MessageRepository;
import com.example.twitterclone.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private MessageRepository messageRepo;

	@Autowired
	private CommentRepository commentRepository;


	/**
	 * В WebSecurityConfig прописано, что доступ по этому URl разрешен только пользователям ADMIN.
	 * Здесь мы получаем текущего пользователя, получаем список из всех пользователей и добавляем этот список в model для отображения на экране
	 */
	@GetMapping("/")
	public String adminDash(@AuthenticationPrincipal User user, Model model) {
		model.addAttribute("currentUser", user);
		Iterable<User> usersList = userRepo.findAll();
		model.addAttribute("userlist", usersList);
		return "admin_dashboard";
	}

	//Данный фильтр ищет пользователя по имени
	@PostMapping("filter")
	public String searchByName(@AuthenticationPrincipal User user, @RequestParam String filter, Model model) {
		model.addAttribute("currentUser", user);
		List<User> userList = userRepo.findAllByUsername(filter);
		model.addAttribute("userlist", userList);
		return "admin_dashboard";
	}

	@GetMapping("/edit/{user}")
	public String editUser(@PathVariable User user, Model model) {
		model.addAttribute("user", user);
		model.addAttribute("roles", Role.values());
		return "edit_user";
	}


	@PostMapping("/edit/{user}")
	public String editUser(//Получаем все данные из формы
						   @RequestParam Map<String, String> form,
						   @PathVariable User user) {


		//Проверяем, что такой пользователь не существует, получая пользователя из userRepo
		User existUser = userRepo.findByUsername(form.get("username"));
		if (existUser != null && !form.get("username").equals(user.getUsername())) {
			return "redirect:/admin/edit/" + user.getId();
		}

		//Получаем все значения ролей из enum
		Set<String> roles = Arrays.stream(Role.values())
				.map(Role::name)
				.collect(Collectors.toSet());
		//Очищаем все роли у пользователя
		user.getRoles().clear();

		//Устанавливаем выбранные роли
		for (String key : form.keySet()) {
			if (roles.contains(key)) {
				user.getRoles().add(Role.valueOf(key));
			}
		}
		user.setUsername(form.get("username"));
		userRepo.save(user);
		return "redirect:/admin/";
	}

	/**
	 * Post метод, который удаляет пользователя
	 * <br>
	 * Доступ только у админа
	 * <br>
	 * В пути передается id, по этому id удаляются сначала все сообщения пользователя,
	 * потом и сам пользователь
	 * В результате происходит редирект на панель админа
	 */
	@PostMapping("/{user}")
	public String deleteUser(@PathVariable User user) {
		if (user != null) {
			user.getSubscribers().clear();
			user.getSubscriptions().clear();
			userRepo.save(user);
			commentRepository.deleteAllByAuthor(user);
			messageRepo.deleteAllByAuthor(user);
			userRepo.deleteById(user.getId());
		}
		return "redirect:/admin/";
	}
}
