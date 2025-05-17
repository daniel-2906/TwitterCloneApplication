package com.TwitterClone.TwitterApplication.Controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.TwitterClone.TwitterApplication.Model.Notification;
import com.TwitterClone.TwitterApplication.Model.User;
import com.TwitterClone.TwitterApplication.Service.NotificationService;
import com.TwitterClone.TwitterApplication.Service.UserService;
import com.TwitterClone.TwitterApplication.Util.AuthUtil;
import com.TwitterClone.TwitterApplication.Util.NotificationResponseDTO;
import com.TwitterClone.TwitterApplication.Util.NotificationUserResponseDTO;
import com.TwitterClone.TwitterApplication.Util.Response;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api")
public class NotificationController {
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/notification")
	public ResponseEntity<?> getNotifications(HttpServletRequest request){
		Long currentuserId = AuthUtil.extractUserId(request);
		try {
			Optional<User> user=userService.findById(currentuserId);
		List<Notification> notifications=notificationService.findByTo(user.get());
		notificationService.markNotificationsAsRead(currentuserId);
	
		
		 List<NotificationResponseDTO> notificationResponseDTOs = notifications.stream().map(notification -> {
		        // Get the 'from' user and create a DTO
		        User fromUser = notification.getFrom();
		        NotificationUserResponseDTO fromUserDTO = new NotificationUserResponseDTO(
		                fromUser.getId(),
		                fromUser.getUsername(),
		                fromUser.getProfileimage() // Assuming this is the correct field name
		        );

		       
		        return new NotificationResponseDTO(
		                notification.getNotificationId(),
		                fromUserDTO, 
		                notification.getTo().getId(), 
		                notification.getType(), // Assuming NotificationType is an enum
		                notification.isRead()
		        );
		    }).collect(Collectors.toList());
		 return ResponseEntity.ok(notificationResponseDTOs);
		}
		catch(Exception e) {
			return ResponseEntity.status(500).body(new Response("Internal Server Error"));
		}
	}
    
	@DeleteMapping("/notification")
	public ResponseEntity<?> deleteNotifications(HttpServletRequest request){
		Long currentuserId = AuthUtil.extractUserId(request);
		try {
			notificationService.deleteNotificationsByUserId(currentuserId);
			return ResponseEntity.status(200).body(new Response("Notifications deleted successfully"));
		}
		catch(Exception e) {
			return ResponseEntity.status(500).body(new Response("Internal Server Error"));
		}
	}
	
	
	@DeleteMapping("/notification/{id}")
	public ResponseEntity<?> deleteNotification(HttpServletRequest request,@PathVariable Long id){
		Long currentuserId =  AuthUtil.extractUserId(request);
		try {
			Optional<Notification> notification=notificationService.findById(id);
			if(notification.isEmpty()) {
				return ResponseEntity.status(404).body(new Response("Notification Not Found"));
			}
			if(notification.get().getTo().getId()!=currentuserId) {
				return ResponseEntity.status(403).body(new Response("error;You are not allowed to delete this notification"));
			}
			notificationService.deleteNotificationsById(id);
			return ResponseEntity.status(200).body(new Response("message:Notification deleted successfully"));
		}
		catch(Exception e) {
			return ResponseEntity.status(500).body(new Response("Internal Server Error"));
		}
	}
}
