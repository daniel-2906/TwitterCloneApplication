package com.TwitterClone.TwitterApplication.Controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.TwitterClone.TwitterApplication.Model.Notification;
import com.TwitterClone.TwitterApplication.Model.NotificationType;
import com.TwitterClone.TwitterApplication.Model.UpdateUser;
import com.TwitterClone.TwitterApplication.Model.User;
import com.TwitterClone.TwitterApplication.Service.ImageUploadService;
import com.TwitterClone.TwitterApplication.Service.NotificationService;
import com.TwitterClone.TwitterApplication.Service.UserService;
import com.TwitterClone.TwitterApplication.Util.AuthUtil;
import com.TwitterClone.TwitterApplication.Util.Response;
import com.TwitterClone.TwitterApplication.Util.UserResponseDTO;
import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/api/users")
public class UserControllers {

	@Autowired
	private UserService service;

	@Autowired
	private NotificationService notificationservice;
	
	@Autowired
   private ImageUploadService imageUploadService;
	
	

	@GetMapping("/profile/{username}")
	public ResponseEntity<?> getUserProfile(@PathVariable String username,HttpServletRequest request) {
		
		try {
			Optional<User> user = service.findExistingUser(username);
			if (!user.isPresent()) {
				return ResponseEntity.status(404).body(new Response("User not found"));
			}
			return ResponseEntity.status(200)
					.body(new UserResponseDTO(user.get().getId(), user.get().getUsername(), user.get().getFullName(),
							user.get().getEmail(), user.get().getFollowers(), user.get().getFollowing(),
							user.get().getProfileimage(), user.get().getCoverimage(), user.get().getBio(),
							user.get().getLink(),user.get().getCreatedAt()));
		} catch (Exception e) {
			return ResponseEntity.status(500).body(new Response("Error Occured"));
		}
	}

	
	  @GetMapping("/suggested") 
	  public ResponseEntity<?> getSuggestedProfile(HttpServletRequest request) {
		  try {
				Long currentuserId =  AuthUtil.extractUserId(request);
				List<User> userFollowedByMe=service.findById(currentuserId).get().getFollowing();
				List<Long> followedUserIds = userFollowedByMe.stream()
			            .map(User::getId)
			            .collect(Collectors.toList());
				List<User> randomUsers = service.getRandomUsers(currentuserId);
				List<User> filteredUsers=randomUsers.stream().filter(user -> !followedUserIds.contains(user.getId())).collect(Collectors.toList());
                List<User> suggestedUsers=filteredUsers.stream().limit(4).collect(Collectors.toList());
                List<UserResponseDTO> userDTOs = suggestedUsers.stream()
                        .map(newuser -> new UserResponseDTO(newuser.getId(),newuser.getUsername(),newuser.getFullName(),newuser.getEmail(),newuser.getFollowers(),newuser.getFollowing(),newuser.getProfileimage(),newuser.getCoverimage(),newuser.getBio(),newuser.getLink()))
                        .collect(Collectors.toList());
                return ResponseEntity.ok(userDTOs);
		  }
		  catch(Exception e) {
			  return ResponseEntity.status(404).body(new Response("Error Occured"));
			  
		  }
	  }
	 

	
	@PostMapping("/follow/{id}")
	public ResponseEntity<Response> followUnfollower(@PathVariable Long id, HttpServletRequest request) {
		try {
		Long currentuserId = AuthUtil.extractUserId(request);
		Optional<User> usertoModify = service.findById(id);
		Optional<User> currentUser = service.findById(currentuserId);
		if (id == currentuserId) {
			return ResponseEntity.status(400).body(new Response("error:You cant follow/unfollow yourself"));
		}
		boolean isFollowing = currentUser.get().getFollowing().contains(usertoModify.get());
		if (isFollowing) {
			usertoModify.get().getFollowers().remove(currentUser.get());
			service.saveuser(usertoModify.get());
			currentUser.get().getFollowing().remove(usertoModify.get());
			service.saveuser(currentUser.get());
			return ResponseEntity.status(200).body(new Response("User unfollowed succesfully"));
		} else {
			usertoModify.get().getFollowers().add(currentUser.get());
			service.saveuser(usertoModify.get());
			currentUser.get().getFollowing().add(usertoModify.get());
			service.saveuser(currentUser.get());
			NotificationType notificationType = NotificationType.FOLLOW;
			Notification newNotification = new Notification(currentUser.get(), usertoModify.get(), notificationType);
			notificationservice.saveNotification(newNotification);
			return ResponseEntity.status(200).body(new Response("User followed succesfully"));
		}
		}
		catch(Exception e) {
			return ResponseEntity.status(404).body(new Response("Error Occured"));
		}
	}

	
	  @PostMapping("/update") 
	  public ResponseEntity<?> UpdateUserProfile(@RequestBody UpdateUser formuserdata,HttpServletRequest request) {
			Long currentuserId =  AuthUtil.extractUserId(request);
			try {
				Optional<User> user=service.findById(currentuserId);
				if(user.get()==null) {
					return ResponseEntity.status(404).body(new Response("User Not Found"));
				}
				if((formuserdata.getNewPassword()==null && formuserdata.getCurrentPassword()!=null) || (formuserdata.getCurrentPassword()==null && formuserdata.getNewPassword()!=null)) {
					return ResponseEntity.status(400).body(new Response("error:Please provide both current password and new password"));
				}
				
			    if(formuserdata.getCurrentPassword()!=null && formuserdata.getNewPassword()!=null) {
			    	boolean isMatch=BCrypt.checkpw(formuserdata.getCurrentPassword(), user.get().getPassword());
			    	if(!isMatch) {
			    		return ResponseEntity.status(400).body(new Response("error:Current Password is incorrect"));
			    	}
			    	if(formuserdata.getNewPassword().length()<6) {
			    		return ResponseEntity.status(400).body(new Response("error:Password must be atleast 6 characters long"));
			    	}
			    	String salt = BCrypt.gensalt(10);
					user.get().setPassword(BCrypt.hashpw(formuserdata.getNewPassword(), salt));
			    }
			    if(formuserdata.getProfileimage()!=null) {
			    	if(user.get().getProfileimage()!=null) {
			    	imageUploadService.deleteImage(user.get().getProfileimage().split("/")[user.get().getProfileimage().split("/").length - 1].split("\\.")[0]);
			    		//cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
			    	}
			    	String profileImagePath=formuserdata.getProfileimage();
			    	
			    	String uniqueId = UUID.randomUUID().toString(); 
			        String defaultPublicId = "profileimage" + uniqueId; 
			        Map uploadResult = imageUploadService.uploadImageFromBase64(profileImagePath, defaultPublicId);
			        String profileimageurl = (String) uploadResult.get("url");
			        user.get().setProfileimage(profileimageurl);
			    }
			    if(formuserdata.getCoverimage()!=null) {
			    	if(user.get().getCoverimage()!=null) {
			    		
			    		imageUploadService.deleteImage(user.get().getCoverimage().split("/")[user.get().getCoverimage().split("/").length - 1].split("\\.")[0]);
			    	}
			    	String coverimagepath=formuserdata.getCoverimage();
			    	String uniqueId = UUID.randomUUID().toString(); 
			        String defaultPublicId = "coverimage" + uniqueId; 
			        Map uploadResult = imageUploadService.uploadImageFromBase64(coverimagepath, defaultPublicId);
			        String coverimageurl = (String) uploadResult.get("url");
			        user.get().setCoverimage(coverimageurl);
			    }
			    user.get().setFullName(formuserdata.getFullName()!=null?formuserdata.getFullName():user.get().getFullName());
			    user.get().setEmail(formuserdata.getEmail()!=null ?formuserdata.getEmail():user.get().getEmail());
			    user.get().setUsername(formuserdata.getUsername()!=null ?formuserdata.getUsername():user.get().getUsername());
			    user.get().setBio(formuserdata.getBio()!=null ?formuserdata.getBio():user.get().getBio());
			    user.get().setLink(formuserdata.getLink()!=null ?formuserdata.getLink():user.get().getLink());
			    service.saveuser(user.get());
			    return ResponseEntity.ok(new UserResponseDTO(user.get().getId(),user.get().getUsername(),user.get().getFullName(),user.get().getEmail(),user.get().getFollowers(),user.get().getFollowing(),user.get().getProfileimage(),user.get().getCoverimage(),user.get().getBio(),user.get().getLink()));
			}
			catch(Exception e) {
				return ResponseEntity.status(500).body(new Response("Error Occured"));
			}
		
	  }
	 

}
