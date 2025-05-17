package com.TwitterClone.TwitterApplication.Util;

import com.TwitterClone.TwitterApplication.Model.NotificationType;

public class NotificationResponseDTO {
    private Long notificationId;
    private NotificationUserResponseDTO from; // User who sent the notification
    private Long to; // ID of the user who received the notification
    private NotificationType type; // Assuming NotificationType is a String
    private boolean read;

    // Constructor
    public NotificationResponseDTO(Long notificationId, NotificationUserResponseDTO from, Long to, NotificationType type, boolean read) {
        this.notificationId = notificationId;
        this.from = from;
        this.to = to;
        this.type = type;
        this.read = read;
    }

    // Getters and Setters
    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public NotificationUserResponseDTO getFrom() {
        return from;
    }

    public void setFrom(NotificationUserResponseDTO from) {
        this.from = from;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
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
}

