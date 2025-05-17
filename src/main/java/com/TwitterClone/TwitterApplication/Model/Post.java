package com.TwitterClone.TwitterApplication.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "posts")
public class Post {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long postId;
	

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    private String text;
    
    private String img;
    
    @ManyToMany
    @JoinTable(
        name = "post_likes",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> likes = new ArrayList<>();
    
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
    
    
    public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	private LocalDateTime createdAt;

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public List<User> getLikes() {
		return likes;
	}

	public void setLikes(List<User> likes) {
		this.likes = likes;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}



	public Post() {
		
	}
	
	 public void addComment(Comment comment) {
	        comments.add(comment);
	        comment.setPost(this); // Set the post for the comment
	    }
	 
	 public void removeLike(User user) {
	        likes.remove(user);
	    }
	 public void addLike(User user) {
		 likes.add(user);
	 }
	 @PrePersist
	    protected void onCreate() {
	        this.createdAt = LocalDateTime.now(); // Set the current date and time
	    }
	 public List<User> updateLikes(Long userId) {
		 
		 List<User> updatedLikes = new ArrayList<>();

	        // Add the user with the specified userId if they are in the likes
	        likes.stream()
	                .filter(user -> user.getId().equals(userId))
	                .findFirst()
	                .ifPresent(updatedLikes::add);

	        // Add all other likes except the user with the specified userId
	        updatedLikes.addAll(likes.stream()
	                .filter(user -> !user.getId().equals(userId))
	                .collect(Collectors.toList()));

	        return updatedLikes;
	 }


}
