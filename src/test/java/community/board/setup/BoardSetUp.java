package community.board.setup;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import community.board.entity.Comment;
import community.board.entity.Post;
import community.user.entity.User;

public class BoardSetUp {

	private static final String testEmail = "test@test.co.kr";
	private static final String testPassword = "test";
	private static final String testId = "testID";
	
	private static final String testPostContent = "testContents";
	private static final String testPostTitle = "testTitle";
	
	private static final String testCommentContent = "testContents";
	
	private static User userSet = null;
	private static Post postSet = null;
	private static List<Comment> commentSet = null; 
	
	public static User userSetUp() {
		userSet = new User();
		userSet.setEmail(testEmail);
		userSet.setPassword(testPassword);
		userSet.setId(testId);
		
		return userSet;
	}
	
	public static Post postSetUp() {
		postSet = new Post();
		postSet.setId(UUID.randomUUID().toString());
		postSet.setContents(testPostContent);
		postSet.setTitle(testPostTitle);
		postSet.setUser(userSet);
		
		return postSet;
	}
	
	public static List<Comment> commentSetUp() {
		Comment comment = new Comment();
		comment.setId(UUID.randomUUID().toString());
		comment.setContents(testCommentContent);
		comment.setPost(postSet);
		comment.setUser(userSet);
		
		Comment comment2 = new Comment();
		comment.setId(UUID.randomUUID().toString());
		comment.setContents(testCommentContent);
		comment.setPost(postSet);
		comment.setUser(userSet);
		
		commentSet = new ArrayList<Comment>();
		commentSet.add(comment);
		commentSet.add(comment2);
		
		return commentSet;
	}
}
