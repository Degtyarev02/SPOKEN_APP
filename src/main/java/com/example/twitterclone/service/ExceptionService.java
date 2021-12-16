package com.example.twitterclone.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

@Service
public class ExceptionService {

	public Model getErrorsFromBindingResult(Model model, BindingResult result) {
		List<FieldError> errorList = result.getFieldErrors();
		for (FieldError error : errorList) {
			//В качестве названия элемента модели используем имя поля, где произошла ошибка и добавляем к ней Error,
			// в качестве сообщения передаем message ошибки определенный в domain
			model.addAttribute(error.getField() + "Error", error.getDefaultMessage());
		}
		return model;
	}
}
