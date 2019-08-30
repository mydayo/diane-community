package community.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import community.user.entity.User;

@Repository
public interface JpaUserRepository extends CrudRepository<User, String> {
	List<User> findAllByOrderByIdDesc();

	public User findByEmail(String email);

	public User findByEmailAndPassword(String email, String password);

	Optional<User> findById(@Param("id") String id);
	
	@Override
	public List<User> findAll();
}