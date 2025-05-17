package com.TwitterClone.TwitterApplication.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;



@Entity
@Table(name = "notifications")
public class Notification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long notificationId;
	
	@NotNull
    @ManyToOne
    @JoinColumn(name = "from_user_id", nullable = false)
    private User from;
	
	@NotNull
    @ManyToOne
    @JoinColumn(name = "to_user_id", nullable = false)
	private User to;
	
	
	@NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType type;
	
	private boolean read;

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public User getTo() {
		return to;
	}

	public void setTo(User to) {
		this.to = to;
	}

	public NotificationType getType() {
		return type;
	}

	public void setType(NotificationType type) {
		this.type = type;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public Notification(@NotNull User from, @NotNull User to, @NotNull NotificationType type) {
		super();
		this.from = from;
		this.to = to;
		this.type = type;
		
	}
	public Notification() {
		
	}

	public Long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Long notificationId) {
		this.notificationId = notificationId;
	}

}
