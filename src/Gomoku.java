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
	
	
	ChessBoard chessBoard;//����
	scoreBoard user1; //�û�һ�ļƷְ�
	scoreBoard user2; //�û����ļƷְ�
	User userBlack;//�ڷ��û�
	User userWhite;//�׷��û�
	
	final private int MARGIN = 1;//�ؼ�֮��ľ���
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Gomoku gomoku = new Gomoku();
		gomoku.setVisible(true);
	}
	
	public Gomoku(){
		init_menu();
		init();
		setResizable(false); //���ڹ̶���С
		
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
		
		JMenu testMenu = new JMenu("�¹��ܲ���");
		JMenuItem blackEvaluate = new JMenuItem("�����ֵ");
		JMenuItem whiteEvaluate = new JMenuItem("�����ֵ");
		testMenu.add(blackEvaluate);
		testMenu.add(whiteEvaluate);
		
		menuBar.add(gameMenu);
		menuBar.add(helpMenu);
		menuBar.add(testMenu);
		
		this.setJMenuBar(menuBar);
		
		//�¼�����
		newGameMenuItem.addActionListener(new JMenuHander(1));
		exitGameMenuItem.addActionListener(new JMenuHander(2));
		supportMenuItem.addActionListener(new JMenuHander(3));
		aboutMenuItem.addActionListener(new JMenuHander(4));
		
		blackEvaluate.addActionListener(new JMenuHander(8));
		whiteEvaluate.addActionListener(new JMenuHander(9));
		
		repaint();

	}
	

	private void init(){
		
		//���ڹر��¼�
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				 System.exit(0);
			}
		});
		
		chessBoard = new stdChessBoard();//----------------------����
		//��ʼ���û�
		userBlack = new localUser(chessBoard);
		userWhite = new Computer(chessBoard);
		
		
		
		setBounds(100, 100, 769, 625);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		
		
		
		chessBoard.setBounds(10, 10, 500, 500);
		getContentPane().add(chessBoard);
		
		user1 = new scoreBoard("resource/robot.jpg", Chess.WHITE, userWhite);//-------------------------�û�һ
		user1.setBounds(542, 10, 200, 226);
		getContentPane().add(user1);
		
		user2 = new scoreBoard("resource/user.png", Chess.BLACK, userBlack);//-------------------------�û���
		user2.setBounds(542, 284, 200, 226);
		getContentPane().add(user2);
		
		//���û���������
		chessBoard.setUserBlack(user2);
		chessBoard.setUserWhite(user1);
				
		JButton btnNewButton = new JButton("����Ϸ");
		btnNewButton.setBounds(20, 533, 93, 30);
		getContentPane().add(btnNewButton);
		
		JButton button = new JButton("����");
		button.setBounds(150, 533, 93, 30);
		getContentPane().add(button);
		
		JButton button_1 = new JButton("����");
		button_1.setBounds(280, 533, 93, 30);
		getContentPane().add(button_1);
		
		JButton button_2 = new JButton("���");
		button_2.setBounds(410, 533, 93, 30);
		getContentPane().add(button_2);
		
		//�¼�����
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
		 * 1-----����Ϸ 
		 * 2-----�˳���Ϸ
		 * 3-----����bug
		 * 4-----����
		 * 
		 * 5-----����
		 * 6-----����
		 * 7-----���
		 * 
		 * 8-----�����ֵ
		 * 9-----�����ֵ
		 */
		
		public JMenuHander(int select) {
			// TODO �Զ����ɵĹ��캯�����
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
