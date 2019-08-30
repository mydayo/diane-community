package community.board.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import community.board.entity.Comment;
import community.board.repository.JpaCommentRepository;
import community.vo.Board;

@Service
public class CommentManagerImpl implements CommentManager, BoardCommonManager {

	@Autowired
	JpaCommentRepository jpaCommentRepository;
	
	@Override
	public Comment addComment(Comment comment) {
		return jpaCommentRepository.save(comment);
	}

	@Override
	public Board updateBoard(Board board) throws Exception {
		Comment comment = jpaCommentRepository.findById(board.getId()).get();
		comment.setContents(board.getContents());
		Comment updatedComment = jpaCommentRepository.save(comment);
		board.setContents(updatedComment.getContents());
		return board;
	}

	@Override
	public void deleteBoard(String id) {
		jpaCommentRepository.deleteById(id);
	}

	@Override
	public String getType() {
		return "comment";
	}
}
