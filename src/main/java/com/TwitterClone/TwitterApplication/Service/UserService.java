package com.TwitterClone.TwitterApplication.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.TwitterClone.TwitterApplication.Model.User;
import com.TwitterClone.TwitterApplication.Repository.UserRepository;

@Service
public class UserService {
	
	
	@Autowired
	private UserRepository repo;
	
	public Optional<User> findExistingUser(String username) {
		return repo.findByUsername(username);
	}

	public Optional<User> findExistingEmail(String email) {
		return repo.findByEmail(email);
		
	}

	@Transactional
	public void saveuser(User newuser) {
		repo.save(newuser);
		
	}

	public String findPassword(String username) {
	
		return repo.findPassword(username);
		
	}

	public Optional<User> findById(Long id) {
		return repo.findById(id);
	}

	public List<User> getRandomUsers(Long currentuserId) {
		return repo.findRandomUsersExcluding(currentuserId);
	}

	public List<User> findall() {
		return repo.findAll();
	}

	public Optional<User> findByUsername(String username) {
		
		return repo.findByUsername(username);
	}

	public List<User> findUsersWhoLikedPost(Long id) {
		return repo.findUsersWhoLikedPost(id);
	}
	
	
}
