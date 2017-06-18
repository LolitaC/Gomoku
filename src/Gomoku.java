import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import chessboard.Chess;
import chessboard.ChessBoard;
import chessboard.stdChessBoard;
import user.Computer;
import user.User;

public class Gomoku extends JFrame{

	final private int GOMOKU_WIDTH = 850;
	final private int GOMOKU_HEIGHT = 700;
	
	ChessBoard chessBoard;//����
	scoreBoard user1; //�û�һ�ļƷְ�
	scoreBoard user2; //�û����ļƷְ�
	Computer computer;
	
	final private int MARGIN = 1;//�ؼ�֮��ľ���
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Gomoku gomoku = new Gomoku();
	}
	
	public Gomoku(){
		init_menu();
		init();
		
	}
	
	private void init_menu(){
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu gameMenu = new JMenu("��Ϸ");
		JMenuItem newGameMenuItem = new JMenuItem("����Ϸ");
		JMenuItem exitGameMenuItem = new JMenuItem("�˳���Ϸ");
		gameMenu.add(newGameMenuItem);
		gameMenu.add(exitGameMenuItem);
		
		JMenu helpMenu = new JMenu("����");
		JMenuItem supportMenuItem = new JMenuItem("����BUG");
		JMenuItem aboutMenuItem = new JMenuItem("����");
		helpMenu.add(supportMenuItem);
		helpMenu.add(aboutMenuItem);
		
		menuBar.add(gameMenu);
		menuBar.add(helpMenu);
		
		this.setJMenuBar(menuBar);
		
		//�¼�����
		newGameMenuItem.addActionListener(new JMenuHander(1));
		exitGameMenuItem.addActionListener(new JMenuHander(2));
		supportMenuItem.addActionListener(new JMenuHander(3));
		aboutMenuItem.addActionListener(new JMenuHander(4));
		
		repaint();

	}
	

	private void init(){
		
		//���ڹر��¼�
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				 System.exit(0);
			}
		});
		
		
        
		
		BorderLayout border = new BorderLayout();
		this.setLayout(border);//�߽粼��
		
		JPanel userPart = new JPanel();//�û�ģ��
		user1 = new scoreBoard();//-------------------------�û�һ
		user2 = new scoreBoard();//-------------------------�û���
		userPart.setLayout(new GridLayout(2, 1));
		userPart.add(user1);
		userPart.add(user2);
		
		
		JPanel mainPart = new JPanel();//���̺� ������
		chessBoard = new stdChessBoard();//----------------------����
		computer = new Computer(chessBoard);
		mainPart.setLayout(new BorderLayout());
		mainPart.add("South", new JLabel("����������"));
		mainPart.add("Center", chessBoard);
		 
		this.add("East" , userPart);
		this.add("Center", mainPart);
		
		mainPart.setPreferredSize(new Dimension(500, 500));
		userPart.setPreferredSize(new Dimension(200, 200));
		
		this.setSize(GOMOKU_WIDTH,GOMOKU_HEIGHT);//�����С
		this.setLocationRelativeTo(null);//���ھ�����ʾ
		this.setVisible(true);
		
	}
	
	
	
	private void newGame(){
		chessBoard.newGame();
	}
	
    class JMenuHander implements ActionListener {
		
		int menuItemNum = 0;
		/*
		 * 1-----����Ϸ 
		 * 2-----�˳���Ϸ
		 * 3-----����bug
		 * 4-----����
		 */
		
		public JMenuHander(int select) {
			// TODO �Զ����ɵĹ��캯�����
			menuItemNum = select;
		}
		
		public void actionPerformed(ActionEvent e) {
			
			switch (menuItemNum) {
			case 1 : newGame(); break;
			case 2 : System.out.println(computer.Evaluate(chessBoard.getGrid(), Chess.BLACK));break;
			default : break;
			}
			
		}
	}

	


}
