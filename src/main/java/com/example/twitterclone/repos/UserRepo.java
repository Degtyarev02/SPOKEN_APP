package com.example.twitterclone.repos;

import com.example.twitterclone.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
	User findByUsername(String username);
	List<User> findAllByUsername(String username);
}
