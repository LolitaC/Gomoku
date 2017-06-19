package chessboard;

import java.awt.Point;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.omg.CORBA.PUBLIC_MEMBER;

import scoreBoard.scoreBoard;
import user.User;

public abstract class ChessBoard extends JPanel implements MouseListener{
	
	scoreBoard userBlack;//黑方用户
	scoreBoard userWhite;//白方用户

	public abstract void newGame();
	public abstract int getRowsCount();
	public abstract int getColumnCount();
	public abstract int[][] getGrid();
	public abstract void rollback();//回退操作
	public abstract void setChess(int x, int y);//放置棋子，
	public abstract ArrayList<Point> getAvailable();//获取可以放置棋子的空格
	public abstract Boolean isWinner(int x, int y);//是否获胜
	
	public void setUserBlack(scoreBoard user)
	{
		userBlack = user;
	}
	public void setUserWhite(scoreBoard user)
	{
		userWhite = user;
	}

}
