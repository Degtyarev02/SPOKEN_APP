package com.example.twitterclone.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Calendar;

@Entity
public class ChatMessage implements Comparable<ChatMessage> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String text;
	private Long senderUserId;
	private Long receiverUserId;
	private Calendar date;

	public ChatMessage() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Long getSenderUserId() {
		return senderUserId;
	}

	public void setSenderUserId(Long senderUserId) {
		this.senderUserId = senderUserId;
	}

	public Long getReceiverUserId() {
		return receiverUserId;
	}

	public void setReceiverUserId(Long receiverUserId) {
		this.receiverUserId = receiverUserId;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public String returnReformatDate() {
		StringBuilder builder = new StringBuilder();

		//Если часов меньше 10, добавляем 0 в начале для двузначаного числа
		if (date.get(Calendar.HOUR_OF_DAY) < 10) {
			builder.append("0");
		}

		builder.append(date.get(Calendar.HOUR_OF_DAY)).append(":");

		//Тоже самое что и с часами
		if (date.get(Calendar.MINUTE) < 10) {
			builder.append("0");
		}

		builder.append(date.get(Calendar.MINUTE));
		return builder.toString();

	}

	@Override
	public int compareTo(ChatMessage o) {
		return id.compareTo(o.getId());
	}
}
