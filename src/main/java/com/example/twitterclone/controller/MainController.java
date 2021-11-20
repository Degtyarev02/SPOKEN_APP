package com.example.twitterclone.controller;

import com.example.twitterclone.domain.Message;
import com.example.twitterclone.domain.User;
import com.example.twitterclone.repos.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String main(Model model){
		Iterable<Message> messages = messageRepository.findAll();
		model.addAttribute("messages", messages);
		return "main";
	}

	@PostMapping("main")
	public String addMessage(@AuthenticationPrincipal User user, @RequestParam String text, @RequestParam String tag, Model model){
		Message message = new Message(text, tag, user);
		messageRepository.save(message);
		Iterable<Message> messages = messageRepository.findAll();
		model.addAttribute("messages", messages);
		return "main";
	}

	@PostMapping("filter")
	public String filter(@RequestParam String filter, Model model) {
		List<Message> byTag = messageRepository.findByTag(filter);
		model.addAttribute("messages", byTag);
		return "main";

	}

}