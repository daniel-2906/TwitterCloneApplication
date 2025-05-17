package com.TwitterClone.TwitterApplication.Util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

	
	 @Value("${cloudinary.cloud_name}")
	    private String cloudName;

	    @Value("${cloudinary.api_key}")
	    private String apiKey;

	    @Value("${cloudinary.api_secret}")
	    private String apiSecret;

	    public String getCloudName() {
			return cloudName;
		}

		public void setCloudName(String cloudName) {
			this.cloudName = cloudName;
		}

		public String getApiKey() {
			return apiKey;
		}

		public void setApiKey(String apiKey) {
			this.apiKey = apiKey;
		}

		public String getApiSecret() {
			return apiSecret;
		}

		public void setApiSecret(String apiSecret) {
			this.apiSecret = apiSecret;
		}

		@Bean
	    public Cloudinary cloudinary() {
	        return new Cloudinary(ObjectUtils.asMap(
	                "cloud_name", cloudName,
	                "api_key", apiKey,
	                "api_secret", apiSecret));
	    }
		
}
