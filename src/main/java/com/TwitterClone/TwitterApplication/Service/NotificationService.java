package com.TwitterClone.TwitterApplication.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TwitterClone.TwitterApplication.Model.Notification;
import com.TwitterClone.TwitterApplication.Model.User;
import com.TwitterClone.TwitterApplication.Repository.NotificationRepository;
import com.TwitterClone.TwitterApplication.Repository.UserRepository;

@Service
public class NotificationService {

	@Autowired
	private NotificationRepository  repo;
	
	@Autowired
	private UserRepository userrepo;
	
	public void saveNotification(Notification newNotification) {
		repo.save(newNotification);
	}

	public List<Notification> findByTo(User user) {
		return repo.findByTo(user);
	}
	
	 public void markNotificationsAsRead(Long userId) {
		 Optional<User> user=userrepo.findById(userId);
	        List<Notification> notifications = repo.findByTo(user.get());
	        for (Notification notification : notifications) {
	            notification.setRead(true);
	        }
	       repo.saveAll(notifications);
	    }

	public void deleteNotificationsByUserId(Long currentuserId) {
		repo.deleteByToUserId(currentuserId);
		
	}

	public Optional<Notification> findById(Long id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	public void deleteNotificationsById(Long id) {
		repo.deleteById(id);
		
	}
}
