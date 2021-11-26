package com.example.twitterclone.controller;

import com.example.twitterclone.domain.Message;
import com.example.twitterclone.domain.User;
import com.example.twitterclone.repos.MessageRepository;
import com.mysql.cj.xdevapi.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Controller
public class MainController {

	@Autowired
	private MessageRepository messageRepository;

	@GetMapping("/")
	public String greeting() {
		return "greeting";
	}

	@Value("${upload.path}")
	private String uploadPath;

	@GetMapping("main")
	public String main(@AuthenticationPrincipal User user, Model model) {
		//Создаем список и передаем туда все сообщения найденные с соответствующей таблицы
		//!(ВАЖНО) Элементы в messages забираются из БД в порядке от старых к новым!!!!!! Дальше это исправим
		Iterable<Message> messages = messageRepository.findAll();

		//Создаем простой список и добавляем туда все элементы из messages
		ArrayList<Message> list = new ArrayList();
		//добавляем все из messages
		messages.forEach(list::add);
		//Разворачиваем наш список, чтобы сперва отображились последние добавленные сообщения (от новых с старым)
		Collections.reverse(list);
		//Передаем список в модель, для отображения на странице
		model.addAttribute("messages", list);
		model.addAttribute("user", user);
		return "main";
	}


	@PostMapping("main")
	public String addMessage(@AuthenticationPrincipal User user,
							 @Valid Message message,
							 BindingResult bindingResult,
							 Model model,
							 @RequestParam("file") MultipartFile file) throws IOException {

		if (bindingResult.hasErrors()) {
			List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
			for (FieldError error : fieldErrorList) {
				System.out.println(error.getField() + "Error" + " " + error.getDefaultMessage());
				model.addAttribute(error.getField() + "Error", error.getDefaultMessage());
				return main(user, model);
			}
		} else {
			message.setAuthor(user);

			//Получаем в форме файл и проверяем существует ли он
			if (file != null && !file.getOriginalFilename().isEmpty()) {
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
				message.setFilename(fileName);
			}
			model.addAttribute("message", null);
			messageRepository.save(message);
		}
		Iterable<Message> messages = messageRepository.findAll();
		model.addAttribute("messages", messages);
		return "redirect:/main";

	}

	@PostMapping("filter")
	public String filter(@RequestParam String filter, Model model) {
		List<Message> byTag = messageRepository.findByTag(filter);
		model.addAttribute("messages", byTag);
		return "main";
	}

	@PostMapping("/main/{message}")
	public String deleteMessage(@PathVariable Message message) {
		if (message != null) {
			File file = new File(uploadPath + "/" + message.getFilename());
			if(file.delete()) {
				System.out.println("delete");
			}
			messageRepository.delete(message);
		}
		return "redirect:/main";
	}

	@GetMapping("/main/user/{user}")
	public String userProfilePage(@PathVariable User user, @AuthenticationPrincipal User currentUser, Model model) {
		model.addAttribute("currentuser", currentUser);
		model.addAttribute("user", user);
		List<Message> byUser = messageRepository.findByAuthor(user);
		model.addAttribute("messages", byUser);
		return "user_profile";
	}


}