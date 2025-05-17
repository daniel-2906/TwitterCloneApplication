package com.TwitterClone.TwitterApplication.Util;

public class NewCommentResponseDTO {
	private Long id; 
    private String text;
    private CommentUserResponseDTO user;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public CommentUserResponseDTO getUser() {
		return user;
	}
	public void setUser(CommentUserResponseDTO user) {
		this.user = user;
	}
	public NewCommentResponseDTO(Long id, String text, CommentUserResponseDTO user) {
		
		this.id = id;
		this.text = text;
		this.user = user;
	}
    

}
