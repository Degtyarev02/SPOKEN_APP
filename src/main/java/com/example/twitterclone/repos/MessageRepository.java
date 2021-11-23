package com.example.twitterclone.repos;

import com.example.twitterclone.domain.Message;
import com.example.twitterclone.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
	List<Message> findByTag(String tag);
	List<Message> findByAuthor(User user);

	@Transactional
	void deleteAllByAuthor(User user);
}
