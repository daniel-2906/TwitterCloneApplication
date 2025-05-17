package com.TwitterClone.TwitterApplication.Controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.TwitterClone.TwitterApplication.Model.Comment;
import com.TwitterClone.TwitterApplication.Model.Notification;
import com.TwitterClone.TwitterApplication.Model.NotificationType;
import com.TwitterClone.TwitterApplication.Model.Post;
import com.TwitterClone.TwitterApplication.Model.User;
import com.TwitterClone.TwitterApplication.Service.ImageUploadService;
import com.TwitterClone.TwitterApplication.Service.NotificationService;
import com.TwitterClone.TwitterApplication.Service.PostService;
import com.TwitterClone.TwitterApplication.Service.UserService;
import com.TwitterClone.TwitterApplication.Util.AllPostCommentResponse;
import com.TwitterClone.TwitterApplication.Util.AllPostResponseDTO;
import com.TwitterClone.TwitterApplication.Util.AuthUtil;
import com.TwitterClone.TwitterApplication.Util.CommentResponseDTO;
import com.TwitterClone.TwitterApplication.Util.CommentUserResponseDTO;
import com.TwitterClone.TwitterApplication.Util.NewCommentResponseDTO;
import com.TwitterClone.TwitterApplication.Util.PostResponseDTO;
import com.TwitterClone.TwitterApplication.Util.Response;
import com.TwitterClone.TwitterApplication.Util.UserResponseDTO;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api/posts")
public class PostController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
  private ImageUploadService imageUploadService;
    
    
    @Autowired
   private NotificationService notificationService;
	
	@PostMapping("/create")
	public ResponseEntity<?> createPost(@RequestBody Post post,HttpServletRequest request){
		Long currentuserId =  AuthUtil.extractUserId(request);
		try {
			String text=post.getText();
			String img=post.getImg();
		    Optional<User> user=userService.findById(currentuserId);
		    if(user.get()==null) {
		    	return ResponseEntity.status(404).body(new Response("User Not Found"));
		    }
		    
		    post.setUser(user.get());
		  
		    if(text ==null && img==null) {
		    	return ResponseEntity.status(400).body(new Response("Post must have text or image"));
		    }
		    if(img!=null) {
		    	String uniqueId = UUID.randomUUID().toString(); // Use current timestamp
		        String defaultPublicId = "image" + uniqueId; 
		        Map uploadResult = imageUploadService.uploadImageFromBase64(img, defaultPublicId);
		        String imageUrl = (String) uploadResult.get("url");
		    	post.setImg(imageUrl); 
		    	
		    }
		    if(text!=null) {
		    	post.setText(text);
		    }
		    postService.savepost(post);
		    List<Long> likes = post.getLikes().stream()
		            .map(User::getId)
		            .collect(Collectors.toList());
		    List<CommentResponseDTO> commentResponses = post.getComments().stream()
		            .map(comment -> {
		                CommentResponseDTO commentResponse = new CommentResponseDTO();
		                commentResponse.setCommentId(comment.getId());
		                commentResponse.setText(comment.getText());
		                commentResponse.setUserId(comment.getUser().getId());
		                return commentResponse;
		            })
		            .collect(Collectors.toList());
		    return ResponseEntity.ok(new PostResponseDTO(post.getPostId(),user.get().getId(),post.getText(),commentResponses,likes));
		}
		catch(Exception e) {
			return ResponseEntity.status(500).body(new Response("Error Occured"));
			
		}
		
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePost(HttpServletRequest request ,@PathVariable Long id){
		
		Long currentuserId = AuthUtil.extractUserId(request);
		try {
		Optional<Post> post=postService.findbyId(id);
		if(post.isEmpty()) {
			return ResponseEntity.status(404).body(new Response("Post Not Found"));
		}
		Optional<User> user=userService.findById(currentuserId);
		if(post.get().getUser()!=user.get()) {
			return ResponseEntity.status(401).body(new Response("You are not authorized to delete this post"));
		}
		if(post.get().getImg()!=null) {
			imageUploadService.deleteImage(post.get().getImg().split("/")[post.get().getImg().split("/").length - 1].split("\\.")[0]);
		}
		List<User> usersWhoLiked = userService.findUsersWhoLikedPost(id);
        for (User users : usersWhoLiked) {
            users.getLikedPosts().removeIf(posts -> posts.getPostId().equals(id));
        }
		postService.deletepost(id);
		
		return ResponseEntity.status(200).body(new Response("Post Deleted Successfully"));
		}
		catch(Exception e) {
			return ResponseEntity.status(500).body(new Response("Error Occured While Deleting Post"));
		}
	}
	
	
	@PostMapping("/like/{id}")
	public ResponseEntity<?> likeUnlikePost(@PathVariable Long id,HttpServletRequest request){
		Long currentuserId =  AuthUtil.extractUserId(request);
		try {
			Long postId=id;
			Post post = postService.findbyId(postId).orElse(null);
	        if (post == null) {
	            return ResponseEntity.status(404).body("Post Not found"); // Post not found
	        }

	        // Find the user
	        User user = userService.findById(currentuserId).orElse(null);
	        if (user == null) {
	            return ResponseEntity.status(404).body("User not found"); // User not found
	        }

	        // Check if the user liked the post
	        boolean userLikedPost = post.getLikes().contains(user);

	        if (userLikedPost) {
	            // Unlike post
	            post.getLikes().remove(user);
	            user.getLikedPosts().remove(post);
	           
	        } else {
	            // Like post
	            post.getLikes().add(user);
	            user.getLikedPosts().add(post);
	            NotificationType notificationType = NotificationType.LIKE;
				Notification newNotification = new Notification(user, post.getUser(), notificationType);
				notificationService.saveNotification(newNotification);

	            
	        }

	        // Save changes
	        postService.savepost(post);
	        userService.saveuser(user);

	        // Return the updated list of User objects who liked the post
	        List<Long> likedByUserIds = post.getLikes().stream()
	                .map(User::getId) // Extract only the user IDs
	                .collect(Collectors.toList());
	        //LikesResponse response = new LikesResponse(likedByUserIds);
	        return ResponseEntity.ok(likedByUserIds);
		}
		catch(Exception e) {
			return ResponseEntity.status(500).body(new Response("Error Occured While likeUnlike Post"));
		}
	}
	
	@PostMapping("/comment/{id}")
	public ResponseEntity<?> commentOnPost(@RequestBody Post post,HttpServletRequest request,@PathVariable Long id){
		Long currentuserId =  AuthUtil.extractUserId(request);
		try {
			String text=post.getText();
			Long postId=id;
			Long userId=currentuserId;
			if(text==null) {
				return ResponseEntity.status(400).body(new Response("error:Text field is required"));
			}
			Optional<Post> existingPost=postService.findbyId(postId);
			if(existingPost.get()==null) {
				return ResponseEntity.status(404).body(new Response("Post Not Found"));
			}
			Comment comments=new Comment();
		    comments.setText(text);
		    Optional<User> user=userService.findById(userId);
		    comments.setUser(user.get());
		    existingPost.get().addComment(comments);
		    postService.savepost(existingPost.get());
		    List<Long> likes = post.getLikes().stream()
		            .map(User::getId)
		            .collect(Collectors.toList());
		    List<CommentResponseDTO> commentResponses = existingPost.get().getComments().stream()
		            .map(comment -> {
		                CommentResponseDTO commentResponse = new CommentResponseDTO();
		                commentResponse.setCommentId(comment.getId());
		                commentResponse.setText(comment.getText());
		                commentResponse.setUserId(comment.getUser().getId());
		                return commentResponse;
		            })
		            .collect(Collectors.toList());
		   
		    return ResponseEntity.ok(new PostResponseDTO(existingPost.get().getPostId(),user.get().getId(),existingPost.get().getText(),commentResponses,likes));
		}
		catch(Exception e) {
			return ResponseEntity.status(500).body(new Response("Error Occured While Commenting Post"));
		}
	}
	
	@GetMapping("/all")
    public ResponseEntity<?> getAllPost(HttpServletRequest request){
		Long currentuserId =  AuthUtil.extractUserId(request);
		try {
			List<Post> posts=postService.findAllPosts();
			if(posts.size()==0) {
				return ResponseEntity.status(401).body(new Response("[]"));
			}
			
			List<AllPostCommentResponse> postResponseDTOs = posts.stream()
				    .map(post -> {
				        UserResponseDTO userResponseDTO = convertToUserResponseDTO(post.getUser());

				        // Collect likes for the current post
				        List<Long> postLikes = post.getLikes().stream()
				            .map(User::getId) // Assuming getLikes() returns List<User>
				            .distinct() // Ensure unique user IDs for this post
				            .collect(Collectors.toList());

				        // Collect comments for the current post
				        List<NewCommentResponseDTO> commentResponses = post.getComments().stream()
				            .map(comment -> new NewCommentResponseDTO(comment.getId(), comment.getText(),
				                new CommentUserResponseDTO(comment.getUser().getUsername(), comment.getUser().getProfileimage(), comment.getUser().getFullName())))
				            .collect(Collectors.toList());

				        return new AllPostCommentResponse(post.getPostId(), userResponseDTO, post.getText(), commentResponses, postLikes, post.getImg(),post.getCreatedAt());
				    })
				    .collect(Collectors.toList());

				return ResponseEntity.status(200).body(postResponseDTOs);
			
		}
		catch(Exception e) {
			return ResponseEntity.status(401).body(new Response("[]"));	
		}
    }
   
    private UserResponseDTO convertToUserResponseDTO(User user) {
    	UserResponseDTO userResponseDTO = new UserResponseDTO(user.getId(),user.getUsername(),user.getFullName(),user.getEmail(),user.getFollowers(),user.getFollowing(),user.getProfileimage(),user.getCoverimage(),user.getBio(),user.getLink());
        return userResponseDTO;
    }
    
    @GetMapping("/likes/{userId}")
    public ResponseEntity<?> getLikedPosts(@PathVariable Long userId,HttpServletRequest request){
    	Long currentuserId =  AuthUtil.extractUserId(request);
		try {
			Optional<User> user=userService.findById(userId);
			if(user.get()==null) {
				return ResponseEntity.status(404).body(new Response("User Not Found"));
			}
			  List<Long> likedPostIds = user.get().getLikedPosts().stream()
			            .map(Post::getPostId)
			            .collect(Collectors.toList());
			  
			  List<Post> likedPosts = postService.findAllById(likedPostIds);
			
			  List<AllPostResponseDTO> likedPostDTOs = likedPosts.stream()
			            .map(post -> {
			                UserResponseDTO userDTO = new UserResponseDTO(post.getUser().getId(),post.getUser().getUsername(),post.getUser().getFullName(),post.getUser().getEmail(),post.getUser().getFollowers(),post.getUser().getFollowing(),post.getUser().getProfileimage(),post.getUser().getCoverimage(),post.getUser().getBio(),post.getUser().getLink());
			  List<CommentResponseDTO> commentDTOs = post.getComments().stream()
			            .map(comment -> new CommentResponseDTO(
			                comment.getId(),
			                comment.getText(),
			                new UserResponseDTO(
			                    comment.getUser().getId(),
			                    comment.getUser().getUsername(),
			                    comment.getUser().getFullName(),
			                    comment.getUser().getEmail()
			                )
			            )).collect(Collectors.toList());
			  
			  return new AllPostResponseDTO(
			            post.getPostId(), // Assuming you have a method getPostId()
			            post.getText(), // Assuming you have a method getText()
			            userDTO,
			            commentDTOs // Use the list of CommentDTOs
			        );
			    }).collect(Collectors.toList());
			  return ResponseEntity.ok(likedPostDTOs);
			    
		}catch(Exception e) {
			return ResponseEntity.status(500).body(new Response("Internal Server"));
		}
    	
    }
        

    @GetMapping("/following")
    public ResponseEntity<?> getFollowingPosts(HttpServletRequest request) {
		Long currentuserId =  AuthUtil.extractUserId(request);
		try {
			Optional<User> user=userService.findById(currentuserId);
			if(user.get()==null) {
				return ResponseEntity.status(404).body(new Response("User Not Found"));
			}
			List<User> following=user.get().getFollowing();
			List<Post> feedPosts = postService.findByUserIn(following);
			List<AllPostResponseDTO> feedPostDTOs = feedPosts.stream()
		            .map(post -> {
		                UserResponseDTO userDTO = new UserResponseDTO(post.getUser().getId(),post.getUser().getUsername(),post.getUser().getFullName(),post.getUser().getEmail(),post.getUser().getFollowers(),post.getUser().getFollowing(),post.getUser().getProfileimage(),post.getUser().getCoverimage(),post.getUser().getBio(),post.getUser().getLink());
		  List<CommentResponseDTO> commentDTOs = post.getComments().stream()
		            .map(comment -> new CommentResponseDTO(
		                comment.getId(),
		                comment.getText(),
		                new UserResponseDTO(
		                    comment.getUser().getId(),
		                    comment.getUser().getUsername(),
		                    comment.getUser().getFullName(),
		                    comment.getUser().getEmail()
		                )
		            )).collect(Collectors.toList());
		  
		  return new AllPostResponseDTO(
		            post.getPostId(), // Assuming you have a method getPostId()
		            post.getText(), // Assuming you have a method getText()
		            userDTO,
		            commentDTOs // Use the list of CommentDTOs
		        );
		    }).collect(Collectors.toList());
			
			return ResponseEntity.ok(feedPostDTOs);
			
		}catch(Exception e) {
			return ResponseEntity.status(500).body(new Response("Internal Server"));
		}
    	
    }

    
    @GetMapping("/user/{username}")
    public ResponseEntity<?>getUserPosts(HttpServletRequest request,@PathVariable String username){
		Long currentuserId = AuthUtil.extractUserId(request);
		try {
			Optional<User> user=userService.findByUsername(username);
			if(user.isEmpty()) {
				return ResponseEntity.status(404).body(new Response("User Not Found"));
			}
			List<Post> posts = postService.findByUserOrderByCreatedAtDesc(user.get());
			List<AllPostResponseDTO> postsDTOs = posts.stream()
		            .map(post -> {
		                UserResponseDTO userDTO = new UserResponseDTO(post.getUser().getId(),post.getUser().getUsername(),post.getUser().getFullName(),post.getUser().getEmail(),post.getUser().getFollowers(),post.getUser().getFollowing(),post.getUser().getProfileimage(),post.getUser().getCoverimage(),post.getUser().getBio(),post.getUser().getLink());
		  List<CommentResponseDTO> commentDTOs = post.getComments().stream()
		            .map(comment -> new CommentResponseDTO(
		                comment.getId(),
		                comment.getText(),
		                new UserResponseDTO(
		                    comment.getUser().getId(),
		                    comment.getUser().getUsername(),
		                    comment.getUser().getFullName(),
		                    comment.getUser().getEmail()
		                )
		            )).collect(Collectors.toList());
		  
		  return new AllPostResponseDTO(
		            post.getPostId(), // Assuming you have a method getPostId()
		            post.getText(), // Assuming you have a method getText()
		            userDTO,
		            commentDTOs ,post.getImg(),post.getCreatedAt()// Use the list of CommentDTOs
		        );
		    }).collect(Collectors.toList());
			
			return ResponseEntity.ok(postsDTOs);
		}catch(Exception e) {
			return ResponseEntity.status(500).body(new Response("Internal Server"));
		}
    	
    }
}
