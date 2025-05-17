package com.TwitterClone.TwitterApplication.Controllers;

import java.util.Optional;
import java.util.regex.Pattern;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.TwitterClone.TwitterApplication.Component.UserContext;
import com.TwitterClone.TwitterApplication.Model.User;
import com.TwitterClone.TwitterApplication.Service.UserService;
import com.TwitterClone.TwitterApplication.Util.AuthUtil;
import com.TwitterClone.TwitterApplication.Util.Response;
import com.TwitterClone.TwitterApplication.Util.UserResponseDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
	
	@Autowired
	private UserService service;
	
	@PostMapping("/signup")
	public ResponseEntity<?> SignUp(@RequestBody User user, HttpServletResponse response) {
		String fullName=user.getFullName();
		String username=user.getUsername();
		String email=user.getEmail();
		String password=user.getPassword();
		if (!isValidEmail(email)) {
            return ResponseEntity.status(400).body(new Response("Invalid email format"));
        }
		 Optional<User> existinguser=service.findExistingUser(username);
		 if(existinguser.isPresent()) {
			 return ResponseEntity.status(400).body(new Response("Username is already taken"));
		 }
		 Optional<User> existingemail=service.findExistingEmail(email);
		 if(existingemail.isPresent()) {
			 return ResponseEntity.status(400).body(new Response("error:Email is already taken"));
		 }
		 String salt = BCrypt.gensalt(10);
		 String hashedPassword=BCrypt.hashpw(password, salt);
		 User newuser=new User(username,fullName,hashedPassword,email);
		if(newuser!=null) {
			 service.saveuser(newuser);
			 AuthUtil.generateTokenAndSetCookie(newuser.getId(), response);
		}
		return ResponseEntity.status(200).body(new UserResponseDTO(newuser.getId(),newuser.getUsername(),newuser.getFullName(),newuser.getEmail(),newuser.getFollowers(),newuser.getFollowing(),newuser.getProfileimage(),newuser.getCoverimage(),newuser.getBio(),newuser.getLink()));
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> Login(@RequestBody User user , HttpServletResponse response) {
		String username=user.getUsername();
		String password=user.getPassword();
		Optional<User> existingUser=service.findExistingUser(username);
		
		boolean isPasswordCorrect=BCrypt.checkpw(password, service.findPassword(username));
		if(existingUser.isEmpty()==true || isPasswordCorrect==false) {
			return ResponseEntity.status(400).body(new Response("Invalid username or password"));
		}
		AuthUtil.generateTokenAndSetCookie(existingUser.get().getId(), response);
		return ResponseEntity.status(200).body(new UserResponseDTO(existingUser.get().getId(),existingUser.get().getUsername(),existingUser.get().getFullName(),existingUser.get().getEmail(),existingUser.get().getFollowers(),existingUser.get().getFollowing(),existingUser.get().getProfileimage(),existingUser.get().getCoverimage(),existingUser.get().getBio(),existingUser.get().getLink()));
	}
	@PostMapping("/logout")
	public ResponseEntity<Response> Logout(HttpServletRequest request,HttpServletResponse response) {
		
		   try {
			Cookie cookie=new Cookie("jwt","");
			cookie.setMaxAge(0);
			cookie.setPath("/");
			response.addCookie(cookie);
			return ResponseEntity.status(200).body(new Response("Logged out Successfully"));
			}
		catch(Exception e) {
			return ResponseEntity.status(500).body(new Response("Internal Server Error"));
		}
		
	}
	
	@GetMapping("/me")
	public ResponseEntity<?> GetMe(HttpServletRequest request,HttpServletResponse response) {
	try {
       Long userId = AuthUtil.extractUserId(request);
	       Optional<User> users=service.findById(userId);
	       if(users.isPresent()==false) {
	    	   return ResponseEntity.status(404).body(new Response("User Not Found"));
	       }
	       users.ifPresent(UserContext::setUser);

	       return ResponseEntity.status(200).body(new UserResponseDTO(users.get().getId(),users.get().getUsername(),users.get().getFullName(),users.get().getEmail(),users.get().getFollowers(),users.get().getFollowing(),users.get().getProfileimage(),users.get().getCoverimage(),users.get().getBio(),users.get().getLink()));
	}
	catch(Exception e) {
			return ResponseEntity.status(500).body(new Response("Internald Server Error"));
		}
		
	}
	

	String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
    
    
    

	


}
