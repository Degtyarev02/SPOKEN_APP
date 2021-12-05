package com.example.twitterclone.domain;


import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank(message = "Please fill the message")
	@Length(max = 512, message = "Message is long then 512ch.")
	private String text;

	@NotBlank(message = "Please fill the tag")
	private String tag;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User author;

	@OneToMany(fetch = FetchType.EAGER)
	private List<Comment> comments;


	private Calendar date;

	private String filename;

	public Message(String text, String tag, User author) {
		this.text = text;
		this.author = author;
		this.tag = tag;
	}

	public Message() {
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

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public User getAuthor() {
		return author;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}


	public void setAuthor(User author) {
		this.author = author;
	}


	public Calendar getDate() {
		return date;
	}

	public String returnReformatDate() {
		StringBuilder builder = new StringBuilder();

		builder
				.append(date.get(Calendar.HOUR_OF_DAY))
				.append(":")
				.append(date.get(Calendar.MINUTE));
		return builder.toString();

	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "Message{" +
				"id=" + id +
				", text='" + text + '\'' +
				", tag='" + tag + '\'' +
				'}';
	}
}
