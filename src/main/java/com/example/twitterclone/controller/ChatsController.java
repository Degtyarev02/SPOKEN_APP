package com.example.twitterclone.controller;


import com.example.twitterclone.domain.ChatMessage;
import com.example.twitterclone.domain.User;
import com.example.twitterclone.repos.ChatMessageRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.List;

@Controller
public class ChatsController {

	@Autowired
	private ChatMessageRepository chatMessageRepository;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	/**
	 * Здесь создаем новый список из всех сообщений.
	 * <br>
	 * Ищем сообщения для обоих пользователей, объединяем списки и сортируем по id, с которым они записаны в БД.
	 * <br>
	 * Это необходимо, чтобы сообщения отображались в том порядке, в котором они отправлялись пользователями
	 */
	@GetMapping("chat/{receiverUser}")
	public String chatPage(@PathVariable User receiverUser, @AuthenticationPrincipal User senderUser, Model model) {

		List<ChatMessage> messages = chatMessageRepository.findAllBySenderUserIdAndReceiverUserId(senderUser.getId(), receiverUser.getId());
		messages.addAll(chatMessageRepository.findAllBySenderUserIdAndReceiverUserId(receiverUser.getId(), senderUser.getId()));

		//Сортируем. Модель реализует Comparable, метод compareTo сравнивает два id'шника.
		Collections.sort(messages);
		model.addAttribute("messages", messages);
		model.addAttribute("receiverUser", receiverUser);
		model.addAttribute("senderUser", senderUser);
		return "chat_page";
	}


	/**
	 * Метод для websocket, принимает отправленное сообщение и сохраняет его в БД
	 *
	 * @param to          пользователь, которому отправляется сообщение
	 * @param chatMessage само сообщение
	 */
	@MessageMapping("/{to}")
	public void greeting(@DestinationVariable Long to, String chatMessage) throws Exception {
		ChatMessage message = new ChatMessage();

		//Сообщение приходит в виде JSON
		//Парсим JSON
		Object obj = new JSONParser().parse(chatMessage);
		//Преобразуем в объект JSON
		JSONObject jo = (JSONObject) obj;
		//Получаем сообщение
		String messageText = (String) jo.get("message");
		String from = (String) jo.get("from");

		message.setText(messageText);
		message.setSenderUserId(Long.valueOf(from));
		message.setReceiverUserId(to);

		chatMessageRepository.save(message);

		simpMessagingTemplate.convertAndSend("/topic/" + to, chatMessage);
	}
}
