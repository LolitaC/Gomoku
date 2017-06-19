package chessboard;

import java.awt.Point;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.omg.CORBA.PUBLIC_MEMBER;

import scoreBoard.scoreBoard;
import user.User;

public abstract class ChessBoard extends JPanel implements MouseListener{
	
	scoreBoard userBlack;//�ڷ��û�
	scoreBoard userWhite;//�׷��û�

	public abstract void newGame();
	public abstract int getRowsCount();
	public abstract int getColumnCount();
	public abstract int[][] getGrid();
	public abstract void rollback();//���˲���
	public abstract void setChess(int x, int y);//�������ӣ�
	public abstract ArrayList<Point> getAvailable();//��ȡ���Է������ӵĿո�
	public abstract Boolean isWinner(int x, int y);//�Ƿ��ʤ
	
	public void setUserBlack(scoreBoard user)
	{
		userBlack = user;
	}
	public void setUserWhite(scoreBoard user)
	{
		userWhite = user;
	}

}
