package csc301.examples.iteration1.service;

import java.util.Iterator;

import csc301.examples.iteration1.model.Post;
import csc301.examples.iteration1.model.User;

public interface DAO {

	public User registerUser(String username, String password);
	public User login(String username, String password);
	public Post createPost(String title, String imageUrl, User user);
	public Iterator<Post> getPostsByUsername(String username);
	public Post getPostById(String postId);
	
}
