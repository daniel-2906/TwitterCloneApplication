package com.TwitterClone.TwitterApplication.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;


@Entity(name="Users")
public class User {
	
	
	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", fullName=" + fullName + ", password=" + password
				+ ", email=" + email + ", followers=" + followers + ", following=" + following + ", profileimage="
				+ profileimage + ", coverimage=" + coverimage + ", bio=" + bio + ", link=" + link + "]";
	}
	public User(String username, String fullName, @Size(min = 6) String password, String email) {
		super();
		this.username = username;
		this.fullName = fullName;
		this.password = password;
		this.email = email;
	}


	public User(Long id) {
		super();
		this.id = id;
	}


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Column(name="username",nullable=false,unique=true)
	private String username;
	@Column(name="fullName",nullable=false)
	private String fullName;
	@Column(name="password",nullable=false)
	@Size(min=6)
	private String password;
	@Column(name="email",nullable=false,unique=true)
	private String email;
	
	private LocalDateTime createdAt;
	
	 public LocalDateTime getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}
	

	@ManyToMany
	@JoinTable(
	    name = "user_followers",
	    joinColumns = @JoinColumn(name = "user_id"),
	    inverseJoinColumns = @JoinColumn(name = "follower_id")
	)
    private List<User> followers = new ArrayList<>();
	
	
	@ManyToMany(mappedBy = "followers")
	private List<User> following = new ArrayList<>();
    
   @Column(name="profileimage")
   private String profileimage;
   @Column(name="coverimage")
   private String coverimage;
   public User() {
	super();
}
@Column(name="bio")
   private String bio;
   @Column(name="link")
   private String link;
   
  
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
//public String getPassword() {
//	return password;
//}
public void setPassword(String password) {
	this.password = password;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public List<User> getFollowers() {
	return followers;
}
public void setFollowers(List<User> followers) {
	this.followers = followers;
}
public List<User> getFollowing() {
	return following;
}
public void setFollowing(List<User> following) {
	this.following = following;
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
public User(Long id, String username, String fullName, String email,List<User> followers, List<User> following,
		String profileimage, String coverimage, String bio, String link) {
	super();
	this.id = id;
	this.username = username;
	this.fullName = fullName;
	this.email = email;
	this.followers = followers;
	this.following = following;
	this.profileimage = profileimage;
	this.coverimage = coverimage;
	this.bio = bio;
	this.link = link;
}
public String getPassword() {
	// TODO Auto-generated method stub
	return password;
}
@PrePersist
protected void onCreate() {
    this.createdAt = LocalDateTime.now(); // Set the current date and time
}

@ManyToMany
@JoinTable(
    name = "user_liked_posts",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "post_id")
)
private List<Post> likedPosts = new ArrayList<>();



public List<Post> getLikedPosts() {
    return likedPosts;
}

public void setLikedPosts(List<Post> likedPosts) {
    this.likedPosts = likedPosts;
}

    
    


}
