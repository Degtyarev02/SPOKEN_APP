package com.example.twitterclone.controller;

import com.example.twitterclone.domain.Comment;
import com.example.twitterclone.domain.Message;
import com.example.twitterclone.domain.User;
import com.example.twitterclone.repos.CommentRepository;
import com.example.twitterclone.repos.MessageRepository;
import com.example.twitterclone.repos.UserRepo;
import com.example.twitterclone.service.ExceptionService;
import com.example.twitterclone.service.S3Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@Controller
public class MainController {

	@Autowired
	UserRepo userRepo;

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	S3Wrapper wrapperService;

	@Autowired
	ExceptionService exceptionService;

	@GetMapping("/")
	public String greeting() {
		return "greeting";
	}


	@GetMapping("main")
	public String main(@AuthenticationPrincipal User user, Model model) {

		//Получаем все подписки у текущего пользователя
		Set<User> subscribers = user.getSubscribers();
		ArrayList<Message> list = new ArrayList<>();
		//Добавляем в список все сообщения подписок
		for(User subUser : subscribers) {
			list.addAll(messageRepository.findByAuthor(subUser));
		}
		//Разворачиваем наш список, чтобы сперва отображились последние добавленные сообщения (от новых к старым)
		Collections.reverse(list);
		//Передаем список в модель, для отображения на странице
		model.addAttribute("messages", list);
		model.addAttribute("currentUser", user);
		return "main";
	}


	@PostMapping("main")
	public String addMessage(@AuthenticationPrincipal User user,
							 @Valid Message message,
							 BindingResult bindingResult,
							 Model model,
							 @RequestParam("file") MultipartFile file) throws IOException {

		if (bindingResult.hasErrors()) {
			model = exceptionService.getErrorsFromBindingResult(model, bindingResult);
			return main(user, model);
		} else {
			message.setAuthor(user);
			Calendar calendar = new GregorianCalendar();
			calendar.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
			message.setDate(calendar);
			//Получаем в форме файл и проверяем существует ли он
			if (file != null && !file.getOriginalFilename().isEmpty()) {
				//Обезопасим коллизию и создадим уникальное имя для файла
				String uuidFile = UUID.randomUUID().toString();
				String fileName = uuidFile + "." + file.getOriginalFilename();
				//Загружаем файл на амазоновское хранилище
				wrapperService.upload(file, fileName);
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

	//Фильтр ищет сообщения по указанному тегу
	//Сообщения отображаются все (согласно тегу), не только сообщения подписок
	@PostMapping("filter")
	public String filter(@RequestParam String filter, Model model, @AuthenticationPrincipal User currentUser) {
		List<Message> byTag = messageRepository.findByTag(filter);
		Collections.reverse(byTag);
		model.addAttribute("messages", byTag);
		model.addAttribute("currentUser", currentUser);
		return "main";
	}


	//Контроллер для удаления отдельной записи
	//Работает только для постов текущего пользователя
	@PostMapping("/main/{message}")
	public String deleteMessage(@PathVariable Message message) {
		//Проверяем что удаляемое сообщение существует
		if (message != null) {
			//Удаляем сообщения из БД
			message.getUsersWhoLiked().clear();
			message.getComments().clear();
			messageRepository.delete(message);
		}
		return "redirect:/main";
	}

	@PostMapping("/main/comment/{message}")
	public String addComment(
			@AuthenticationPrincipal User user,
			@PathVariable Message message,
			@RequestHeader(required = false) String referer,
			@Valid Comment comment,
			BindingResult bindingResult) {


		UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();
		if (bindingResult.hasErrors()) {
			FieldError error = bindingResult.getFieldError();
			//Добавляем в модель все ошибки
			assert error != null;
			System.out.println(error.getDefaultMessage());
			return "redirect:" + components.getPath();
		} else {
			//Получаем дату создания комментария
			Calendar calendar = new GregorianCalendar();
			calendar.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
			//Устанавливаем для комментария дату
			comment.setDate(calendar);
			//Связываем комментарий с сообщением и автором
			comment.setMessage(message);
			comment.setAuthor(user);
			//Добавляем комментарий в список комментариев текущего сообщения
			List<Comment> comments = message.getComments();
			comments.add(comment);
			message.setComments(comments);
			//Сохраняем в бд
			commentRepository.save(comment);

			return "redirect:" + components.getPath();
		}

	}

	@PostMapping("/main/like/{message}")
	public String likeMessage(@PathVariable Message message,
							  @RequestHeader(required = false) String referer,
							  @AuthenticationPrincipal User currentUser){

		UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();

		if(message != null && !currentUser.getLikedPosts().contains(message) && !message.getUsersWhoLiked().contains(currentUser)){
			currentUser.getLikedPosts().add(message);
			message.getUsersWhoLiked().add(currentUser);
			messageRepository.save(message);
		}

		return "redirect:" + components.getPath();
	}

	@PostMapping("/main/unlike/{message}")
	public String unLike(@PathVariable Message message,
							  @RequestHeader(required = false) String referer,
							  @AuthenticationPrincipal User currentUser){

		UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();

		if(message != null && message.getUsersWhoLiked().contains(currentUser)){
			currentUser.getLikedPosts().remove(message);
			message.getUsersWhoLiked().remove(currentUser);
			messageRepository.save(message);
		}

		return "redirect:" + components.getPath();
	}


}