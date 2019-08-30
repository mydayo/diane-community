package community.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import community.board.entity.Comment;
import community.board.entity.Post;
import community.board.manager.BoardCommonManager;
import community.board.manager.CommentManager;
import community.board.manager.PostManager;
import community.exception.BadRequestParameterException;
import community.exception.RequestTargetIsNullException;
import community.vo.Board;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	PostManager postManager;
	
	@Autowired
	CommentManager commentManager;
	
	@Autowired
	List<BoardCommonManager> listBoardManager;

	private static final Map<String, BoardCommonManager> myServiceCache = new HashMap<>();
	private static final String type = "POST";


	@PostConstruct
	public void initMyServiceCache() {
		for (BoardCommonManager service : listBoardManager) {
			myServiceCache.put(service.getType(), service);
		}
	}
	
	@Override
	public Post addPost(Post post) throws Exception {
		Post savedPost = null;
		try {
			savedPost = postManager.addPost(post);
		} catch (BadRequestParameterException e) {
			e.printStackTrace();
		} catch (JpaSystemException e) {
			e.printStackTrace();
			throw new BadRequestParameterException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return savedPost;
	}

	@Override
	public Map<String, Object> getPost(String id) throws Exception {
		Map<String, Object> postWithComment;
		try {
			postWithComment = postManager.getPost(id);
		} catch (NullPointerException e) {
			throw new RequestTargetIsNullException(type);
		}
		return postWithComment;
	}

	@Override
	public List<Post> getPostList() throws Exception {
		return postManager.getPostList();
	}

	@Override
	public Comment addComment(Comment comment) {
		return commentManager.addComment(comment);
	}

	@Override
	public Board update(Board board) throws Exception {
		Board updatedBoard = null;
		try {
			updatedBoard = myServiceCache.get(board.getType()).updateBoard(board);
		} catch (NoSuchElementException e) {
			log.debug(e.getMessage());
			throw new BadRequestParameterException(e.getMessage());
		} catch (JpaSystemException e) {
			throw new BadRequestParameterException(e.getMessage());
		} 
		return updatedBoard;
	}

	@Override
	public void delete(String id, String type) throws Exception {
		myServiceCache.get(type).deleteBoard(id);
	}
}
