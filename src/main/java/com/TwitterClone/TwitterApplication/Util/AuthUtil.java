package com.TwitterClone.TwitterApplication.Util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;

public class AuthUtil {

    private static final String SECRET_KEY = AppConfig.getSecretKey(); 
    private static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds

    public static void generateTokenAndSetCookie(Long newUserId, HttpServletResponse response) {
        // Convert Long user ID to String
        String userIdString = newUserId.toString();

        
        String jwt = JWT.create()
                .withSubject(userIdString) // Use the String representation of the user ID
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET_KEY));

        // Create a cookie
        Cookie cookie = new Cookie("jwt", jwt);
        cookie.setHttpOnly(true); // Prevents JavaScript access to the cookie
         // Use true if your application is served over HTTPS
         // Set the path for the cookie
        cookie.setPath("/");
        cookie.setMaxAge((int) EXPIRATION_TIME / 1000); 
        // Set the cookie expiration time

        // Add the cookie to the response
        
        response.addCookie(cookie);
    
    }
    public static Long extractUserId(HttpServletRequest request) {
    	String token=null;
    	Cookie[] cookies=request.getCookies();   
        if(cookies!=null) {
        	for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
       
       if(token==null) {
    	  // return ResponseEntity.status(401).body(new Response("Unauthorized:No Token provided"));
       }
       Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
       JWTVerifier verifier = JWT.require(algorithm).build();
       DecodedJWT decoded = verifier.verify(token);
       
       if(decoded==null) {
    	  // return ResponseEntity.status(401).body(new Response("Unauthorized:Invalid Token"));
       }
        return Long.valueOf( JWT.decode(token).getSubject());
    }
    
    
}
