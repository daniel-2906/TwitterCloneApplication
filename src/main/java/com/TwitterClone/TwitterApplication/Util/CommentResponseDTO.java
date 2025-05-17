package com.TwitterClone.TwitterApplication.Util;

public class CommentResponseDTO {
	private Long commentId; 
    private String text;
    private Long userId;
    private UserResponseDTO user;
	
	
	public UserResponseDTO getUser() {
		return user;
	}
	public void setUser(UserResponseDTO user) {
		this.user = user;
	}
	public Long getCommentId() {
		return commentId;
	}
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public CommentResponseDTO() {
	}
	
	public CommentResponseDTO(Long commentId, String text, Long userId) {
	
		this.commentId = commentId;
		this.text = text;
		this.userId = userId;
	}
	public CommentResponseDTO(Long commentId, String text,UserResponseDTO user)
	{
		this.commentId = commentId;
		this.text = text;
		this.user=user;
		
	}
	 
   
}
