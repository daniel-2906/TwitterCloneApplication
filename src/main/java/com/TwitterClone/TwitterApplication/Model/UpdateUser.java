package com.TwitterClone.TwitterApplication.Model;

import jakarta.validation.constraints.Size;

public class UpdateUser {

	private String fullName;
	private String email;
	private String username;
	@Size(min=6)
	private String currentPassword;
	@Size(min=6)
	private String newPassword;
	private String bio;
	private String link;
	private String profileimage;
	private String coverimage;
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCurrentPassword() {
		return currentPassword;
	}
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getProfileimage() {
		return profileimage;
	}
	public void setProfileimage(String profileimage) {
		this.profileimage = profileimage;
	}
	public String getCoverimage() {
		return coverimage;
	}
	public void setCoverimage(String coverimage) {
		this.coverimage = coverimage;
	}
	@Override
	public String toString() {
		return "UpdateUser [fullName=" + fullName + ", email=" + email + ", username=" + username + ", currentPassword="
				+ currentPassword + ", newPassword=" + newPassword + ", bio=" + bio + ", link=" + link
				+ ", profileimage=" + profileimage + ", coverimage=" + coverimage + "]";
	}
	
}
