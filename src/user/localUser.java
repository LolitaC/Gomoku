package user;

import chessboard.ChessBoard;

public class localUser extends User{

	public localUser(ChessBoard chessBoard)
	{
		super(chessBoard);
		init();
	}
	
	private void init(){
		userType = UserType.LOCAL_HUMAN; //�û�����Ϊ�����û�
	}
	
	public UserType getUserType(){
		return userType;
	}
}
