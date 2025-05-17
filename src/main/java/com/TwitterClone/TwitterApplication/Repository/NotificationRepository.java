package com.TwitterClone.TwitterApplication.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.TwitterClone.TwitterApplication.Model.Notification;
import com.TwitterClone.TwitterApplication.Model.User;
import jakarta.transaction.Transactional;

@Repository
public interface NotificationRepository  extends JpaRepository<Notification,Long> {
	
	
	List<Notification> findByTo(User user);
	
	    @Modifying
	    @Transactional
	    @Query("DELETE FROM Notification n WHERE n.to.id = ?1")
	    void deleteByToUserId(Long userId);

}
