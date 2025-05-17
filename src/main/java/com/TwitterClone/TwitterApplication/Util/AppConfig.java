package com.TwitterClone.TwitterApplication.Util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    private static String secretKey;

    @Value("${secret.key}")
    public void setSecretKey(String secretKey) {
        AppConfig.secretKey = secretKey;
    }

    public static String getSecretKey() {
        return secretKey;
    }
}

