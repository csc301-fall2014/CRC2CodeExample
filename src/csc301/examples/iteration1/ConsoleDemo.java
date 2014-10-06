package csc301.examples.iteration1;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Scanner;

import csc301.examples.iteration1.model.Post;
import csc301.examples.iteration1.model.User;
import csc301.examples.iteration1.service.DAO;
import csc301.examples.iteration1.service.DAOImplInMemory;

public class ConsoleDemo {

	private Scanner inputScanner;
	private DAO dao;
	
	private User loggedInUser = null;
	
	
	public ConsoleDemo(InputStream input, DAO dao) {
		inputScanner = new Scanner(input);
		this.dao = dao;
	}
	
	
	
	public void run(){
		System.out.println("Welcome");
		System.out.println("Please type a command [register, login, post, find-post, search-posts, exit].");
		String s = inputScanner.nextLine().toLowerCase().trim();
		
		while(! s.equals("exit")){	
			try{
				if("register".equals(s)){
					register();
				} else if("login".equals(s)){
					login();
				} else if("post".equals(s)){
					post();
				} else if("find-post".equals(s)){
					findPost();
				} else if("search-posts".equals(s)){
					searchPosts();
				} else {
					System.out.println("Unrecognized command - " + s);		
				}
			} catch (Exception e){
				System.out.println("ERROR: " + e.getMessage());
			}
			
			System.out.println("Please type a command [register, login, post, find-post, search-posts, exit].");
			s = inputScanner.nextLine().toLowerCase().trim();
		}
		
		System.out.println("Goodbye");
	}
	


	private void register() {
		System.out.println("Please enter a username:");
		String username = inputScanner.nextLine().trim();
		System.out.println("Please enter a password:");
		String password = inputScanner.nextLine().trim();
		dao.registerUser(username, password);
	}
	
	
	private void login() {
		System.out.println("Please enter a username:");
		String username = inputScanner.nextLine().trim();
		System.out.println("Please enter a password:");
		String password = inputScanner.nextLine().trim();
		loggedInUser = dao.login(username, password);
	}
	
	
	private void post() {
		if(loggedInUser == null){
			throw new IllegalStateException("You must login in order to post something.");
		}
		System.out.println("Please enter a title");
		String title = inputScanner.nextLine().trim();
		System.out.println("Please enter an image URL");
		String imageUrl = inputScanner.nextLine().trim();
		System.out.println(dao.createPost(title, imageUrl, loggedInUser));
	}
	
	
	private void findPost() {
		System.out.println("Please enter a post id");
		String postId = inputScanner.nextLine().trim();
		System.out.println(dao.getPostById(postId));
	}
	
	private void searchPosts() {
		System.out.println("Please enter a username");
		String username = inputScanner.nextLine().trim();
		Iterator<Post> itr = dao.getPostsByUsername(username);
		int count = 0;
		while (itr.hasNext()) {
			System.out.println(itr.next() + "\n");
			count++;
		}
		System.out.println(count + " post(s) in total from " + username);
	}



	public static void main(String[] args) {
		new ConsoleDemo(System.in, new DAOImplInMemory()).run();
	}
}
