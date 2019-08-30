package community.user.service;

import java.util.List;

import community.user.entity.User;

public interface UserService {

	public boolean emailDuplicationCheck(String email) throws Exception;

	public User addUser(User user) throws Exception;

	public User loginUser(User user) throws Exception;

	public User updateUser(String id, User user) throws Exception;

	public void deleteUser(String id) throws Exception;

	public List<User> getAllUser(String email) throws Exception;
}
