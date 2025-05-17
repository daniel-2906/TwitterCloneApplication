package com.TwitterClone.TwitterApplication.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.TwitterClone.TwitterApplication.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
	Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);
	
	@Query(value = "SELECT password FROM USERS WHERE username =:username", nativeQuery = true)
	String findPassword(@Param("username") String username);
	
	@Query(value = "SELECT * FROM users WHERE id <> :userId ORDER BY RANDOM() LIMIT 10", nativeQuery = true)
	 List<User> findRandomUsersExcluding(@Param("userId") Long userId);
	
	@Query(value="SELECT u.* FROM users u JOIN user_liked_posts p ON u.id = p.user_id WHERE p.post_id = :postId", nativeQuery = true)
	List<User> findUsersWhoLikedPost(@Param("postId") Long postId);

}
