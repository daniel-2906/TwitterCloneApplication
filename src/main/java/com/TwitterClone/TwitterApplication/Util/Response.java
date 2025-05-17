package com.TwitterClone.TwitterApplication.Util;

import com.TwitterClone.TwitterApplication.Model.User;

public class Response {
	private Object data;
    
    public Response(String data) {
        this.data = data;
    }
    public Response(User data) {
    	this.data=data;
    }
   
    public Object getdata() {
    	return data;
    }
}
