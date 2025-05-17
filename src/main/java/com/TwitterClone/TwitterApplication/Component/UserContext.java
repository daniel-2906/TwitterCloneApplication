package com.TwitterClone.TwitterApplication.Component;

import org.springframework.stereotype.Component;

import com.TwitterClone.TwitterApplication.Model.User;

@Component
public class UserContext {
    private static final ThreadLocal<User> userHolder = new ThreadLocal<>();

    public static void setUser(User user) {
        userHolder.set(user);
    }

    public static User getUser() {
        return userHolder.get();
    }

    public static void clear() {
        userHolder.remove();
    }
}
