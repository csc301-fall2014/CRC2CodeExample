package csc301.examples.iteration1.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;


import csc301.examples.iteration1.model.Post;
import csc301.examples.iteration1.model.User;

public class DAOImplInMemory implements DAO {

	
	private Map<String, User> username2user = new HashMap<String, User>();
	private Map<String, Post> id2post = new HashMap<String, Post>();
	private Map<String, Collection<Post>> username2Posts = new HashMap<String, Collection<Post>>();
	
	
	@Override
	public User registerUser(String username, String password) {
		if(username2user.containsKey(username)){
			throw new IllegalArgumentException("Username '" + username + "' is taken.");
		}
		username2user.put(username, new User(username, password));
		return username2user.get(username);
	}

	
	@Override
	public User login(String username, String password) {
		if(! username2user.containsKey(username)){
			throw new IllegalArgumentException("No such user - " + username);
		}
		User user = username2user.get(username);
		if(! user.getPassword().equals(password)){
			throw new IllegalArgumentException("Invalid password");
		}
		return user;
	}

	
	@Override
	public Post createPost(String title, String imageUrl, User user) {
		String id = UUID.randomUUID().toString();
		if(id2post.containsKey(id)){
			throw new InternalError("Ooops");
		}
		
		Post post = new Post(id, title, imageUrl, user);
		id2post.put(id, post);
		
		if(!username2Posts.containsKey(user.getUsername())){
			username2Posts.put(user.getUsername(), new ArrayList<Post>());
		}
		username2Posts.get(user.getUsername()).add(post);
		
		return id2post.get(id);
	}
	
	
	@Override
	public Iterator<Post> getPostsByUsername(String username) {
		if(username2Posts.containsKey(username)){
			return username2Posts.get(username).iterator();
		} else {
			return Collections.emptyIterator(); 
		}
	}

	
	@Override
	public Post getPostById(String postId) {
		return id2post.get(postId);
	}
	

}
