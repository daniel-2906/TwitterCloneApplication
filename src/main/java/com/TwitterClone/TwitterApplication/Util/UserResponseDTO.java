package com.TwitterClone.TwitterApplication.Util;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.TwitterClone.TwitterApplication.Model.User;

public class UserResponseDTO {
	private Long id;

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

	public List<Long> getFollowers() {
		return followers;
	}

	public void setFollowers(List<Long> followers) {
		this.followers = followers;
	}

	public List<Long> getFollowing() {
		return following;
	}

	public void setFollowing(List<Long> following) {
		this.following = following;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public String getCoverImage() {
		return coverImage;
	}

	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
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

	private String username;
	private String fullName;
	private String email;
	private List<Long> followers; 
	private List<Long> following; 
	private String profileImage;
	private String coverImage;
	private String bio;
	private String link;
	private LocalDateTime createdAt;

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	// Constructor
	public UserResponseDTO(Long id, String username, String fullName, String email, List<User> followers,
			List<User> following, String profileImage, String coverImage, String bio, String link) {
		this.id = id;
		this.username = username;
		this.fullName = fullName;
		this.email = email;
		this.followers = followers.stream().map(User::getId).collect(Collectors.toList());
		this.following = following.stream().map(User::getId).collect(Collectors.toList());
		this.profileImage = profileImage;
		this.coverImage = coverImage;
		this.bio = bio;
		this.link = link;

	}
	public UserResponseDTO(Long id, String username, String fullName, String email, List<User> followers,
			List<User> following, String profileImage, String coverImage, String bio, String link,LocalDateTime createdAt) {
		this.id = id;
		this.username = username;
		this.fullName = fullName;
		this.email = email;
		this.followers = followers.stream().map(User::getId).collect(Collectors.toList());
		this.following = following.stream().map(User::getId).collect(Collectors.toList());
		this.profileImage = profileImage;
		this.coverImage = coverImage;
		this.bio = bio;
		this.link = link;
		this.createdAt=createdAt;

	}

	

	

	public UserResponseDTO() {
		// TODO Auto-generated constructor stub
	}

	

	public UserResponseDTO(Long id, String username, String fullName, String email) {
		this.id = id;
		this.username = username;
		this.fullName = fullName;
		this.email = email;
	}


	

}
