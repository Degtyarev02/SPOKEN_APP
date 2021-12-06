package com.example.twitterclone.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Calendar;

@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	@NotEmpty
	@Length(max = 50, message = "Comment is too long")
	private String text;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User author;

	private Calendar date;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "message_id")
	private Message message;

	public Comment() {
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

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public String returnReformatDate() {
		StringBuilder builder = new StringBuilder();

		//Если часов меньше 10, добавляем 0 в начале для двузначного числа
		if (date.get(Calendar.HOUR_OF_DAY) < 10) {
			builder.append("0");
		}

		builder.append(date.get(Calendar.HOUR_OF_DAY)).append(":");

		//То же самое что и с часами
		if (date.get(Calendar.MINUTE) < 10) {
			builder.append("0");
		}

		builder.append(date.get(Calendar.MINUTE));
		return builder.toString();

	}
}
