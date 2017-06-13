package chessboard;

import java.awt.event.MouseListener;

import javax.swing.JPanel;

public abstract class ChessBoard extends JPanel implements MouseListener{
	
	public abstract void newGame();
	
}
