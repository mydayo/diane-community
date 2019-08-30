package community.board.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import community.board.entity.Comment;

@Repository
public interface JpaCommentRepository extends CrudRepository<Comment, String>{
	List<Comment> findAllByOrderByIdDesc();
	
	List<Comment> findCommentsByPostId(String postId);

}