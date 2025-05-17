package com.TwitterClone.TwitterApplication.Util;

public class CommentUserResponseDTO {
	private String username;
	private String profileimage;
	private String fullName;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getProfileimage() {
		return profileimage;
	}
	public void setProfileimage(String profileimage) {
		this.profileimage = profileimage;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public CommentUserResponseDTO(String username, String profileimage, String fullName) {
		super();
		this.username = username;
		this.profileimage = profileimage;
		this.fullName = fullName;
	}
	
	

}
