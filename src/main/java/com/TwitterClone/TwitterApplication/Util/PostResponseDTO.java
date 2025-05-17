package com.TwitterClone.TwitterApplication.Util;

import java.util.ArrayList;
import java.util.List;
public class PostResponseDTO {
	
	private Long Id;
	private Long userId;
	private String text;
	private List<CommentResponseDTO> comments;
	private List<Long> likes = new ArrayList<>();
	
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
	public PostResponseDTO(Long id, Long userId, String text, List<CommentResponseDTO> comments, List<Long> likes) {
		super();
		Id = id;
		this.userId = userId;
		this.text = text;
		this.comments = comments;
		this.likes = likes;
	}
	public PostResponseDTO(List<CommentResponseDTO> comments) {
		this.comments = comments;
	}
	
	
	
	

}
