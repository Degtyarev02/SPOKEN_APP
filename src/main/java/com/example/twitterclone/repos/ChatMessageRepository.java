package com.example.twitterclone.repos;

import com.example.twitterclone.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
	ArrayList<ChatMessage> findAllBySenderUserIdAndReceiverUserId(Long senderUserId, Long receiverUserId);
}
