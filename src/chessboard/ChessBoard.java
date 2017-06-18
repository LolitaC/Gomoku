package chessboard;

import java.awt.event.MouseListener;

import javax.swing.JPanel;

public abstract class ChessBoard extends JPanel implements MouseListener{
	
	public abstract void newGame();
	public abstract int getRowsCount();
	public abstract int getColumnCount();
	public abstract int[][] getGrid();
}
