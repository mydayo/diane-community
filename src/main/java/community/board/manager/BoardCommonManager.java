package community.board.manager;

import community.vo.Board;

public interface BoardCommonManager {
	
	public String getType();
	
	public Board updateBoard(Board board) throws Exception;

	public void deleteBoard(String id) throws Exception;
	
}
