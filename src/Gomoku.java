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
	
	ChessBoard chessBoard;//棋盘
	scoreBoard user1; //用户一的计分板
	scoreBoard user2; //用户二的计分板
	Computer computer;
	
	final private int MARGIN = 1;//控件之间的距离
	
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
		
		JMenu gameMenu = new JMenu("游戏");
		JMenuItem newGameMenuItem = new JMenuItem("新游戏");
		JMenuItem exitGameMenuItem = new JMenuItem("退出游戏");
		gameMenu.add(newGameMenuItem);
		gameMenu.add(exitGameMenuItem);
		
		JMenu helpMenu = new JMenu("帮助");
		JMenuItem supportMenuItem = new JMenuItem("报告BUG");
		JMenuItem aboutMenuItem = new JMenuItem("关于");
		helpMenu.add(supportMenuItem);
		helpMenu.add(aboutMenuItem);
		
		menuBar.add(gameMenu);
		menuBar.add(helpMenu);
		
		this.setJMenuBar(menuBar);
		
		//事件监听
		newGameMenuItem.addActionListener(new JMenuHander(1));
		exitGameMenuItem.addActionListener(new JMenuHander(2));
		supportMenuItem.addActionListener(new JMenuHander(3));
		aboutMenuItem.addActionListener(new JMenuHander(4));
		
		repaint();

	}
	

	private void init(){
		
		//窗口关闭事件
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				 System.exit(0);
			}
		});
		
		
        
		
		BorderLayout border = new BorderLayout();
		this.setLayout(border);//边界布局
		
		JPanel userPart = new JPanel();//用户模块
		user1 = new scoreBoard();//-------------------------用户一
		user2 = new scoreBoard();//-------------------------用户二
		userPart.setLayout(new GridLayout(2, 1));
		userPart.add(user1);
		userPart.add(user2);
		
		
		JPanel mainPart = new JPanel();//棋盘和 功能区
		chessBoard = new stdChessBoard();//----------------------棋盘
		computer = new Computer(chessBoard);
		mainPart.setLayout(new BorderLayout());
		mainPart.add("South", new JLabel("功能区部分"));
		mainPart.add("Center", chessBoard);
		 
		this.add("East" , userPart);
		this.add("Center", mainPart);
		
		mainPart.setPreferredSize(new Dimension(500, 500));
		userPart.setPreferredSize(new Dimension(200, 200));
		
		this.setSize(GOMOKU_WIDTH,GOMOKU_HEIGHT);//界面大小
		this.setLocationRelativeTo(null);//窗口居中显示
		this.setVisible(true);
		
	}
	
	
	
	private void newGame(){
		chessBoard.newGame();
	}
	
    class JMenuHander implements ActionListener {
		
		int menuItemNum = 0;
		/*
		 * 1-----新游戏 
		 * 2-----退出游戏
		 * 3-----报告bug
		 * 4-----关于
		 */
		
		public JMenuHander(int select) {
			// TODO 自动生成的构造函数存根
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
