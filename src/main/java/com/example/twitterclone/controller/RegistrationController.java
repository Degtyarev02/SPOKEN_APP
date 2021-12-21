package com.example.twitterclone.controller;

import com.example.twitterclone.domain.Role;
import com.example.twitterclone.domain.User;
import com.example.twitterclone.repos.UserRepo;
import com.example.twitterclone.service.ExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Controller
public class RegistrationController {


	//На AWS хранилище существует набор стоковых фотографий, которые устанавливаются по умолчанию
	private final String[] stockProfileImages = {"dog.png", "bird.png", "cat.png", "owl.png"};

	@Autowired
	private UserRepo userRepo;

	@Autowired
	@Lazy
	private PasswordEncoder passwordEncoder;

	@Autowired
	ExceptionService exceptionService;

	@GetMapping("/registration")
	public String registration() {
		return "registration";
	}


	/**
	 * Получаем пользователя после регистрации, если такой пользователь существует, то выводим соответствующее сообщение
	 * <br>
	 * Для проверки используем UserRepo, с собственным методом, который ищет пользователя в бд по имени
	 *
	 * @param user  С помощью сеттеров из переданной в html форме информации собирается пользователь
	 * @param model Стандартная модель, передаем туда сообщение если юзер существует
	 */
	@PostMapping("/registration")
	public String addUser(@Valid User user, BindingResult result, Model model) {

		//Если пароль у пользователя существует и не равен паролю для подтверждения,
		//то добавляем в модель ошибку
		if (user.getPassword() != null && !user.getPassword().equals(user.getPassword2())) {
			model.addAttribute("passwordEqualsError", "Passwords are not equals");
			return "registration";
		}
		//Если BindingResult вернулся с ошибками, то заполняем список ошибками и в цикле передаем все ошибки в модель
		if (result.hasErrors()) {
			model = exceptionService.getErrorsFromBindingResult(model, result);
			return "registration";
		}

		//Получаем пользователя из БД передавая имя нового пользователя
		User userFromDB = userRepo.findByUsername(user.getUsername());
		//Если в бд такой пользователь существует, то выдаем сообщение об ошибке
		if (userFromDB != null) {
			model.addAttribute("message", "User is already exist");
			return "registration";
		}
		Random random = new Random();
		//Стоковые фотографии выбираются рандомно
		user.setIconname(stockProfileImages[random.nextInt(stockProfileImages.length)]);
		user.setActive(true);
		user.setRoles(Collections.singleton(Role.USER));
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepo.save(user);

		return "redirect:/login";

	}


}