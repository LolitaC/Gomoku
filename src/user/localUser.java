package user;

import chessboard.ChessBoard;

public class localUser extends User{

	public localUser(ChessBoard chessBoard)
	{
		super(chessBoard);
		init();
	}
	
	private void init(){
		userType = UserType.LOCAL_HUMAN; //用户类型为本地用户
	}
	
	public UserType getUserType(){
		return userType;
	}
}
