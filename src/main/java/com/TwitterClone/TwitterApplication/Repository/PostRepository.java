package com.TwitterClone.TwitterApplication.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.TwitterClone.TwitterApplication.Model.Post;
import com.TwitterClone.TwitterApplication.Model.User;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

	List<Post> findAllByOrderByCreatedAtDesc();
	
	@Query("SELECT p FROM Post p WHERE p.user IN :users ORDER BY p.createdAt DESC")
	List<Post> findByUserInOrderByCreatedAtDesc(List<User> users);
	
	List<Post> findByUserOrderByCreatedAtDesc(User user);
	

	
	


}
