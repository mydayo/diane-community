package community.board.manager;

import java.util.List;
import java.util.Map;

import community.board.entity.Post;

public interface PostManager {

	public Post addPost(Post post) throws Exception ;
	
	public Map<String, Object> getPost(String id)  throws Exception;
	
	public List<Post> getPostList()  throws Exception;
}
