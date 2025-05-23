package com.TwitterClone.TwitterApplication.Util;

public class NotificationUserResponseDTO {
	 private Long id;
	    private String username;
	    private String profileImage;

	    // Constructor
	    public NotificationUserResponseDTO(Long id, String username, String profileImage) {
	        this.id = id;
	        this.username = username;
	        this.profileImage = profileImage;
	    }
	    public NotificationUserResponseDTO(Long id) {
	    	this.id=id;
	    }

	    // Getters and Setters
	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getUsername() {
	        return username;
	    }

	    public void setUsername(String username) {
	        this.username = username;
	    }

	    public String getProfileImage() {
	        return profileImage;
	    }

	    public void setProfileImage(String profileImage) {
	        this.profileImage = profileImage;
	    }
}
