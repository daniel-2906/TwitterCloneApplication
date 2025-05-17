package com.TwitterClone.TwitterApplication.Util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AllPostResponseDTO {

	private Long Id;
	private String text;
	private List<CommentResponseDTO> comments;
	private List<Long> likes = new ArrayList<>();
	private UserResponseDTO user;
	private String img;
	private LocalDateTime createdAt;
	
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
	public List<CommentResponseDTO> getComments() {
		return comments;
	}
	public void setComments(List<CommentResponseDTO> comments) {
		this.comments = comments;
	}
	public List<Long> getLikes() {
		return likes;
	}
	public void setLikes(List<Long> likes) {
		this.likes = likes;
	}
	
	
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public AllPostResponseDTO(Long id,UserResponseDTO user,String text, List<CommentResponseDTO> comments, List<Long> likes) {
		Id=id;
		this.user=user;
		this.text = text;
		this.comments = comments;
		this.likes = likes;
	}
	public AllPostResponseDTO(Long id, String text, UserResponseDTO userDTO, List<CommentResponseDTO> commentDTOs,String img,LocalDateTime createdAt) {
		this.Id=id;
		this.text=text;
		this.user=userDTO;
		this.comments=commentDTOs;
		this.img=img;
		this.createdAt=createdAt;
			
		}
	public AllPostResponseDTO(Long id, String text, UserResponseDTO userDTO, List<CommentResponseDTO> commentDTOs) {
		this.Id=id;
		this.text=text;
		this.user=userDTO;
		this.comments=commentDTOs;
	}
}
