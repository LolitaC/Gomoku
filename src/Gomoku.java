import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.GridLayout;

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
import scoreBoard.scoreBoard;
import user.Computer;
import user.User;
import user.localUser;


public class Gomoku extends JFrame{

	final private int GOMOKU_WIDTH = 850;
	final private int GOMOKU_HEIGHT = 700;
	
	
	ChessBoard chessBoard;//棋盘
	scoreBoard user1; //用户一的计分板
	scoreBoard user2; //用户二的计分板
	User userBlack;//黑方用户
	User userWhite;//白方用户
	
	final private int MARGIN = 1;//控件之间的距离
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Gomoku gomoku = new Gomoku();
		gomoku.setVisible(true);
	}
	
	public Gomoku(){
		init_menu();
		init();
		setResizable(false); //窗口固定大小
		
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
		
		JMenu testMenu = new JMenu("新功能测试");
		JMenuItem blackEvaluate = new JMenuItem("黑棋估值");
		JMenuItem whiteEvaluate = new JMenuItem("白棋估值");
		testMenu.add(blackEvaluate);
		testMenu.add(whiteEvaluate);
		
		menuBar.add(gameMenu);
		menuBar.add(helpMenu);
		menuBar.add(testMenu);
		
		this.setJMenuBar(menuBar);
		
		//事件监听
		newGameMenuItem.addActionListener(new JMenuHander(1));
		exitGameMenuItem.addActionListener(new JMenuHander(2));
		supportMenuItem.addActionListener(new JMenuHander(3));
		aboutMenuItem.addActionListener(new JMenuHander(4));
		
		blackEvaluate.addActionListener(new JMenuHander(8));
		whiteEvaluate.addActionListener(new JMenuHander(9));
		
		repaint();

	}
	

	private void init(){
		
		//窗口关闭事件
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				 System.exit(0);
			}
		});
		
		chessBoard = new stdChessBoard();//----------------------棋盘
		//初始化用户
		userBlack = new localUser(chessBoard);
		userWhite = new Computer(chessBoard);
		
		
		
		setBounds(100, 100, 769, 625);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		
		
		
		chessBoard.setBounds(10, 10, 500, 500);
		getContentPane().add(chessBoard);
		
		user1 = new scoreBoard("resource/robot.jpg", Chess.WHITE, userWhite);//-------------------------用户一
		user1.setBounds(542, 10, 200, 226);
		getContentPane().add(user1);
		
		user2 = new scoreBoard("resource/user.png", Chess.BLACK, userBlack);//-------------------------用户二
		user2.setBounds(542, 284, 200, 226);
		getContentPane().add(user2);
		
		//将用户加入棋盘
		chessBoard.setUserBlack(user2);
		chessBoard.setUserWhite(user1);
				
		JButton btnNewButton = new JButton("新游戏");
		btnNewButton.setBounds(20, 533, 93, 30);
		getContentPane().add(btnNewButton);
		
		JButton button = new JButton("悔棋");
		button.setBounds(150, 533, 93, 30);
		getContentPane().add(button);
		
		JButton button_1 = new JButton("认输");
		button_1.setBounds(280, 533, 93, 30);
		getContentPane().add(button_1);
		
		JButton button_2 = new JButton("求和");
		button_2.setBounds(410, 533, 93, 30);
		getContentPane().add(button_2);
		
		//事件监听
		btnNewButton.addActionListener(new JMenuHander(1));
		button.addActionListener(new JMenuHander(5));
		button_1.addActionListener(new JMenuHander(6));
		button_2.addActionListener(new JMenuHander(7));
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
		 * 
		 * 5-----悔棋
		 * 6-----认输
		 * 7-----求和
		 * 
		 * 8-----黑棋估值
		 * 9-----白棋估值
		 */
		
		public JMenuHander(int select) {
			// TODO 自动生成的构造函数存根
			menuItemNum = select;
		}
		
		public void actionPerformed(ActionEvent e) {
			
			switch (menuItemNum) {
			case 1 : newGame(); break;
			case 2 : break;
			case 5 : chessBoard.rollback();break;
			case 8 : break;
			case 9 : break;
			default : break;
			}
			
		}
	}

	


}
