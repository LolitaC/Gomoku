package user;

import chessboard.ChessBoard;

public abstract class User {
	
    UserType userType = UserType.LOCAL_HUMAN;
    ChessBoard chessBoard;
    
    public User(ChessBoard chessBoard) {
		// TODO Auto-generated constructor stub
    	this.chessBoard = chessBoard;
	}
}

