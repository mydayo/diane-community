package community.board.service;

import java.util.List;
import java.util.Map;

import community.board.entity.Comment;
import community.board.entity.Post;
import community.vo.Board;

public interface BoardService {

	public Post addPost(Post post) throws Exception;
	
	public Map<String, Object> getPost(String id) throws Exception;

	public List<Post> getPostList() throws Exception;

	public Comment addComment(Comment comment) throws Exception;
	
	public Board update(Board board) throws Exception;
	
	public void delete(String id, String type) throws Exception;
}
