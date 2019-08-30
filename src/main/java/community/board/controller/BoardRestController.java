package community.board.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import community.board.entity.Comment;
import community.board.entity.Post;
import community.board.service.BoardService;
import community.vo.Board;
import io.swagger.annotations.Api;

@Api("BOARD SYSTEM")
@RestController
@RequestMapping("/community/board")
public class BoardRestController {
	
	@Autowired
	BoardService boardService;
	
	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public Post addPost(@RequestBody Post post) throws Exception {
		return boardService.addPost(post);
	}
	
	@RequestMapping(value = "/post/{id}", method = RequestMethod.GET)
	public Map<String, Object> getPost(@PathVariable String id) throws Exception {
		return boardService.getPost(id);
	}
	
	@RequestMapping(value = "/post", method = RequestMethod.PUT)
	public Board updatePost(@RequestBody Board board) throws Exception {
		return boardService.update(board);
	}
	
	@RequestMapping(value = "/post/{id}", method = RequestMethod.DELETE)
	public void deletePost(@PathVariable String id) throws Exception {
		boardService.delete(id, "post");
	}
	
	@RequestMapping(value = "/post/all", method = RequestMethod.GET)
	public List<Post> getAllPost() throws Exception {
		return boardService.getPostList();
	}
	
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public Comment addComment(Comment comment) throws Exception {
		return boardService.addComment(comment);
	}
	
	@RequestMapping(value = "/comment", method = RequestMethod.PUT)
	public Board updateComment(@RequestBody Board board) throws Exception {
		return boardService.update(board);
	}
	
	@RequestMapping(value = "/comment/{id}", method = RequestMethod.DELETE)
	public void deleteComment(@PathVariable String id) throws Exception {
		boardService.delete(id, "comment");
	}
}
