package com.TwitterClone.TwitterApplication.Util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AllPostCommentResponse {

	private Long Id;
	private String text;
	private List<NewCommentResponseDTO> comments;
	private List<Long> likes = new ArrayList<>();
	private UserResponseDTO user;
	private String img;
	private LocalDateTime createdAt;
	
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public UserResponseDTO getUser() {
		return user;
	}
	public void setUser(UserResponseDTO user) {
		this.user = user;
	}
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<NewCommentResponseDTO> getComments() {
		return comments;
	}
	public void setComments(List<NewCommentResponseDTO> comments) {
		this.comments = comments;
	}
	public List<Long> getLikes() {
		return likes;
	}
	public void setLikes(List<Long> likes) {
		this.likes = likes;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public AllPostCommentResponse(Long id, UserResponseDTO user,String text, List<NewCommentResponseDTO> comments, List<Long> likes,String img,
			LocalDateTime createdAt) {
		super();
		Id = id;
		this.text = text;
		this.comments = comments;
		this.likes = likes;
		this.user = user;
		this.img=img;	
		this.createdAt=createdAt;
		}
	
	
}
