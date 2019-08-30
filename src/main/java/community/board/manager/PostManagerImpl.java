package community.board.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import community.board.entity.Comment;
import community.board.entity.Post;
import community.board.repository.JpaCommentRepository;
import community.board.repository.JpaPostRepository;
import community.exception.BadRequestParameterException;
import community.vo.Board;

@Service
public class PostManagerImpl implements PostManager, BoardCommonManager {

	@Autowired
	JpaPostRepository jpaPostRepository;
	
	@Autowired
	JpaCommentRepository jpaCommentRepository;
	
	public static final String type = "post";
		
	@Override
	public Post addPost(Post post) {
		String title = post.getTitle();
		if (title == "" || title.length() > 30) {
			throw new BadRequestParameterException();
		}
		return jpaPostRepository.save(post);
	}

	@Override
	public Map<String, Object> getPost(String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		Optional<Post> post = jpaPostRepository.findById(id);
		if (post.isPresent()) {
			result.put("post", post.get());			
			List<Comment> commentList = jpaCommentRepository.findCommentsByPostId(id);
			if (jpaCommentRepository.findCommentsByPostId(id) != null) {
				result.put("comment", commentList);	
			}
		} else {
			throw new NullPointerException();
		}
		return result;
	}

	@Override
	public List<Post> getPostList() {
		return jpaPostRepository.findAll();
	}

	@Override
	public Board updateBoard(Board board) throws Exception {
		
		if (board.getTitle() == null || board.getTitle().length() > 30) {
			throw new BadRequestParameterException();
		}

		Optional<Post> post = jpaPostRepository.findById(board.getId());
		Post updatePost = post.get();
		updatePost.setTitle(board.getTitle());
		updatePost.setContents(board.getContents());
		updatePost = jpaPostRepository.save(updatePost);
		board.setContents(updatePost.getContents());
		board.setTitle(updatePost.getTitle());
		board.setId(updatePost.getId());
		
		return board;
	}

	@Override
	public void deleteBoard(String id) {
		jpaPostRepository.deleteById(id);
	}
	
	@Override
	public String getType() {
		return type;
	}
}
