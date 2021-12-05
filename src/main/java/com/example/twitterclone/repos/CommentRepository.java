package com.example.twitterclone.repos;

import com.example.twitterclone.domain.Comment;
import com.example.twitterclone.domain.Message;
import com.example.twitterclone.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findAllByAuthor(User user);
	List<Comment> findAllByMessage(Message message);
}
