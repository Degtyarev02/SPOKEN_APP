package com.example.twitterclone.controller;

import com.example.twitterclone.domain.Message;
import com.example.twitterclone.domain.User;
import com.example.twitterclone.repos.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
public class MainController {

	@Autowired
	private MessageRepository messageRepository;

	@GetMapping("/")
	public String greeting() {
		return "greeting";
	}

	@GetMapping("main")
	public String main(@AuthenticationPrincipal User user, Model model) {
		//Создаем список и передаем туда все сообщения найденные с соответствующей таблицы
		Iterable<Message> messages = messageRepository.findAll();
		//Передаем список в модель, для отображения на странице
		model.addAttribute("messages", messages);
		model.addAttribute("user", user);
		return "main";
	}


	@PostMapping("main")
	public String addMessage(@AuthenticationPrincipal User user,
							 @Valid Message message,
							 BindingResult bindingResult,
							 Model model) {

		if (bindingResult.hasErrors()) {
			List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
			for (FieldError error : fieldErrorList) {
				System.out.println(error.getField()+"Error" + " " + error.getDefaultMessage());
				model.addAttribute(error.getField()+"Error", error.getDefaultMessage());
				return main(user, model);
			}
		} else {
			message.setAuthor(user);
			messageRepository.save(message);
		}
			Iterable<Message> messages = messageRepository.findAll();
			model.addAttribute("messages", messages);
			return "redirect:/main";

	}

		@PostMapping("filter")
		public String filter (@RequestParam String filter, Model model){
			List<Message> byTag = messageRepository.findByTag(filter);
			model.addAttribute("messages", byTag);
			return "main";
		}
	}