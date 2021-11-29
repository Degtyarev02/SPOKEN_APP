package com.example.twitterclone.controller;


import com.example.twitterclone.domain.Message;
import com.example.twitterclone.domain.User;
import com.example.twitterclone.repos.MessageRepository;
import com.example.twitterclone.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class UserController {


	@Value("${upload.path}")
	private String uploadPath;


	@Autowired
	UserRepo userRepo;

	@Autowired
	private MessageRepository messageRepository;


	//Контроллер, который выводит информацию об отдельном пользователе
	@GetMapping("/main/user/{user}")
	public String userProfilePage(@PathVariable User user, @AuthenticationPrincipal User currentUser, Model model) {
		model.addAttribute("currentuser", currentUser);
		model.addAttribute("user", user);
		List<Message> byUser = messageRepository.findByAuthor(user);
		Collections.reverse(byUser);
		model.addAttribute("messages", byUser);
		return "user_profile";
	}

	//Контроллер для редактирования пользователя
	@GetMapping("/main/edit/{user}")
	public String selfEdit(@PathVariable User user, Model model) {
		model.addAttribute("user", user);
		return "self_edit";
	}

	@PostMapping("/main/edit/{user}")
	public String saveEditUser(
			@PathVariable User user,
			//Получаем все данные из всех полей в форме
			@RequestParam Map<String, String> form,
			@RequestParam("file") MultipartFile file) throws IOException {

		//Получаем новое имя и статус пользователя
		String newUsername = form.get("username");
		String newStatus = form.get("status");

		//Проверка на существование пользователя
		User existUser = userRepo.findByUsername(newUsername);
		if (existUser != null && !newUsername.equals(user.getUsername())) {
			return "redirect:/main/edit/" + user.getId();
		}

		if (file != null && !file.getOriginalFilename().isEmpty()) {
			//Если у пользователя уже стоит аватарка, то удаляем старую
			if (user.getIconname() != null) {
				File deletable = new File(uploadPath + "/" + user.getIconname());
				if (deletable.delete()) {
					System.out.println("delete");
				}
			}

			//Создаем путь до папки, в которую будут сохраняться файлы
			File uploadDir = new File(uploadPath);
			//Если эта папка не существует, то создадим ее
			if (!uploadDir.exists()) {
				uploadDir.mkdir();
			}
			//Обезопасим коллизию и создадим уникальное имя для файла
			String uuidFile = UUID.randomUUID().toString();
			String fileName = uuidFile + "." + file.getOriginalFilename();
			//Перемещаем файл в папку
			file.transferTo(new File(uploadPath + "/" + fileName));
			//Устанавливаем имя файла для объекта message
			user.setIconname(fileName);
		}
		//Сохраняем отредактированного пользователя
		user.setUsername(newUsername);
		user.setStatus(newStatus);
		userRepo.save(user);
		return "redirect:/main/user/" + user.getId();
	}
}
