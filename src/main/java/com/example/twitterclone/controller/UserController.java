package com.example.twitterclone.controller;

import com.example.twitterclone.domain.Message;
import com.example.twitterclone.domain.User;
import com.example.twitterclone.repos.MessageRepository;
import com.example.twitterclone.repos.UserRepo;
import com.example.twitterclone.service.ExceptionService;
import com.example.twitterclone.service.S3Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class UserController {

	@Autowired
	UserRepo userRepo;

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	S3Wrapper s3Wrapper;


	//Контроллер, который выводит информацию об отдельном пользователе
	@GetMapping("/main/user/{user}")
	public String userProfilePage(@PathVariable User user, @AuthenticationPrincipal User currentUser, Model model) {

		model.addAttribute("subscriptionsSize", user.getSubscriptions().size());
		model.addAttribute("subscribersSize", user.getSubscribers().size());
		model.addAttribute("currentUser", currentUser);
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
			//Файл - аватарка пользователя
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
				s3Wrapper.deleteFile("profile/" + user.getIconname());
			}

			//Обезопасим коллизию и создадим уникальное имя для файла
			String uuidFile = UUID.randomUUID().toString();
			String fileName = uuidFile + "." + file.getOriginalFilename();
			//Перемещаем файл в папку
			s3Wrapper.upload(file.getInputStream(), "profile/" + fileName);
			//Устанавливаем имя файла для объекта message
			user.setIconname(fileName);
		}
		//Сохраняем отредактированного пользователя
		user.setUsername(newUsername);
		user.setStatus(newStatus);
		userRepo.save(user);
		return "redirect:/main/user/" + user.getId();
	}


	//Метод для подписки на пользователя
	@PostMapping("/main/subscribe/{user}")
	public String subscribeToUser(@PathVariable User user, //Пользователь на которого подписываемся
								  @AuthenticationPrincipal User currentUser, //Пользователь, который подписывается
								  @RequestHeader(required = false) String referer //Ссылка с которой пришел запрос
	) {
		//Если пользовательские id не равны и у подписчиков пользователя на которого мы подписываемся нет пользователя, который подписывается
		if (!user.getId().equals(currentUser.getId())
				&& !user.getSubscribers().contains(currentUser)) {
			//То добавляемся в подписчики пользователя на которого подписываемся
			user.getSubscribers().add(currentUser);
			currentUser.getSubscriptions().add(user);
			userRepo.save(user);
			userRepo.save(currentUser);
		}
		//Получаем путь откуда мы пришли и возвращаемся туда
		UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();
		return "redirect:" + components.getPath();
	}

	//Метод для отписки от пользователя
	@PostMapping("/main/unsubscribe/{user}")
	public String unsubscribeFromUser(@PathVariable User user,
									  @AuthenticationPrincipal User currentUser,
									  @RequestHeader(required = false) String referer) {
		//Если пользователи не одинаковые
		//И если один подписан на другого
		if (!user.getId().equals(currentUser.getId())
				&& user.getSubscribers().contains(currentUser)) {
			//Убираем себя из подписчиков
			user.getSubscribers().remove(currentUser);
			currentUser.getSubscriptions().remove(user);
			userRepo.save(currentUser);
			userRepo.save(user);
		}
		UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();
		return "redirect:" + components.getPath();
	}


	/**
	 * Метод выводит всех пользователей на экран учитывая "фильтр" с именем пользователя
	 * По умолчанию фильтр - пустая строка - выводит всех существующих пользователей
	 *
	 * @param currentUser текущий пользователь
	 * @param findingName параметр, который нужен, чтобы искать пользователя по имени
	 */
	@GetMapping("/main/users")
	public String allUsers(@AuthenticationPrincipal User currentUser, Model model, @RequestParam(defaultValue = "") String findingName) {
		List<User> users;
		//Если фильтр по умолчанию - выводи всех
		if (findingName.equals("")) {
			users = userRepo.findAll();
			//Иначе найди пользователя по имени
		} else {
			users = userRepo.findAllByUsername(findingName);
		}
		model.addAttribute("users", users);
		model.addAttribute("currentUser", currentUser);
		return "users";
	}
}
