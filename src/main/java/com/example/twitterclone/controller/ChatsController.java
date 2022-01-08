package com.example.twitterclone.controller;


import com.example.twitterclone.domain.ChatMessage;
import com.example.twitterclone.domain.User;
import com.example.twitterclone.repos.ChatMessageRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@GetMapping("chat/{receiverUser}")
	public String chatPage(@PathVariable User receiverUser, @AuthenticationPrincipal User senderUser, Model model){
		List<ChatMessage> messages = chatMessageRepository.findAllBySenderUserIdAndReceiverUserId(senderUser.getId(), receiverUser.getId());
		model.addAttribute("messages", messages);
		model.addAttribute("receiverUser", receiverUser);
		model.addAttribute("senderUser", senderUser);
		return "chat_page";
	}


	/**
	 * Метод
	 * @param to пользователь, которому отправляется сообщение
	 * @param chatMessage само сообщение
	 */
	@MessageMapping("/{to}")
	public void greeting(@DestinationVariable String to, String chatMessage) throws Exception {

		//Сообщение приходит в виде JSON
		//Парсим JSON
		Object obj = new JSONParser().parse(chatMessage);
		//Преобразуем в объект JSON
		JSONObject jo = (JSONObject) obj;
		//Получаем сообщение
		String message = (String) jo.get("message");

		//Здесь настроить добавление в бд

		System.out.println(to);
		System.out.println(message);
		simpMessagingTemplate.convertAndSend("/topic/" + to, chatMessage);
	}
}
