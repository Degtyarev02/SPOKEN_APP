package com.example.twitterclone.controller;


import com.example.twitterclone.domain.ChatMessage;
import com.example.twitterclone.domain.User;
import com.example.twitterclone.repos.ChatMessageRepository;
import com.mysql.cj.xdevapi.JsonString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ChatsController {

	@Autowired
	private ChatMessageRepository chatMessageRepository;

	@GetMapping("chat/{receiverUser}")
	public String chatPage(@PathVariable User receiverUser, @AuthenticationPrincipal User senderUser, Model model){
		List<ChatMessage> messages = chatMessageRepository.findAllBySenderUserIdAndReceiverUserId(senderUser.getId(), receiverUser.getId());
		model.addAttribute("messages", messages);
		model.addAttribute("receiverUser", receiverUser);
		return "chat_page";
	}

	@MessageMapping("/{to}")
	@SendTo("/topic/greetings")
	public String greeting(@DestinationVariable String to, String chatMessage) throws Exception {
		System.out.println(to);
		System.out.println(chatMessage);
		return chatMessage;
	}
}
