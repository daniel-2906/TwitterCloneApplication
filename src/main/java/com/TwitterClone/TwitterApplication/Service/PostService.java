package com.TwitterClone.TwitterApplication.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TwitterClone.TwitterApplication.Model.Post;
import com.TwitterClone.TwitterApplication.Model.User;
import com.TwitterClone.TwitterApplication.Repository.PostRepository;

@Service
public class PostService {
	
	@Autowired
	private PostRepository repo;

	public void savepost(Post newPost) {
         repo.save(newPost);		
	}

	public Optional<Post> findbyId(Long currentuserId) {
		
		return repo.findById(currentuserId);
		
	}

	public void deletepost(Long id) {
		repo.deleteById(id);
		
	}

	public List<Post> findAllPosts() {
	return repo.findAllByOrderByCreatedAtDesc();
	}

	public List<Post> findAllById(List<Long> likedPostIds) {
		return repo.findAllById(likedPostIds);
	}

	public List<Post> findByUserIn(List<User> following) {
		// TODO Auto-generated method stub
		return repo.findByUserInOrderByCreatedAtDesc(following);
	}

	public List<Post> findByUserOrderByCreatedAtDesc(User user) {
		// TODO Auto-generated method stub
		return repo.findByUserOrderByCreatedAtDesc(user);
	}

	


	

	

}
