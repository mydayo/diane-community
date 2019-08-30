package community.board.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import community.board.entity.Post;

@Repository
public interface JpaPostRepository extends CrudRepository<Post, String> {
	List<Post> findAllByOrderByIdDesc();

	@Override
	public List<Post> findAll();

}