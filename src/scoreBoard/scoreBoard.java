package scoreBoard;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import chessboard.Chess;
import user.User;

public class scoreBoard extends JPanel{
	
	Image image;//����ͼ��
	CheckboxGroup c;
	String color;//����Ż��ǰ��巽
	Chess chess;
	
	User user;
	JLabel labelWin;
	JLabel labelLose;
	JLabel labeltie;
	
    
    int win = 0;  //Ӯ
    int lose = 0; //��
    int tie = 0;  //ƽ��
    
	public scoreBoard(String filename, Chess chess, User user){
		
		this.user = user;
		this.chess = chess;
		if(chess == Chess.BLACK)
			color = "���巽";
		else
			color = "���巽";
		
		try {

            image=ImageIO.read(new File(filename));

        } catch (Exception e) {

            // TODO Auto-generated catch block

            e.printStackTrace();

        }
		init();
	}
	
	private void init()
	{
		c = new CheckboxGroup();//��ѡ��
		
        setLayout(null);
		
		JPanel panel_1 = new JPanel(){
			public void paint(Graphics g){
				g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
			}
		};
		panel_1.setBounds(10, 10, 121, 152);
		add(panel_1);
		
		//����  ��ѡ��ť
		Checkbox checkbox = new Checkbox("����", c, chess == Chess.WHITE);
		checkbox.setEnabled(chess == Chess.WHITE);
		checkbox.setBounds(133, 22, 67, 23);
		add(checkbox);
		
		//���� ��ѡ��ť
		Checkbox checkbox1 = new Checkbox("����", c, chess == Chess.BLACK);
		checkbox1.setEnabled(chess == Chess.BLACK);
		checkbox1.setBounds(133, 52, 67, 23);
		add(checkbox1);
		
		//Զ��  ��ѡ��ť �������Ժ��Զ���û�
		Checkbox checkbox2 = new Checkbox("Զ��", c,false);
		checkbox2.setEnabled(false);
		checkbox2.setBounds(133, 82, 67, 23);
		add(checkbox2);
		
		JLabel colorName = new JLabel(color);
		colorName.setFont(new Font("����", Font.PLAIN, 17));
		colorName.setBounds(133, 132, 67, 23);
		add(colorName);
		
		labelWin = new JLabel("ʤ:0");
		labelWin.setFont(new Font("����", Font.PLAIN, 17));
		labelWin.setBounds(10, 172, 56, 44);
		add(labelWin);
		
		labelLose = new JLabel("��:0");
		labelLose.setFont(new Font("����", Font.PLAIN, 17));
		labelLose.setBounds(70, 172, 56, 44);
		add(labelLose);
		
		labeltie = new JLabel("ƽ:0");
		labeltie.setFont(new Font("����", Font.PLAIN, 17));
		labeltie.setBounds(130, 172, 56, 44);
		add(labeltie);
		
	}
	
	//���ص�ǰ���ӷ�
	public Chess getChess()
	{
		return chess;
	}
	
	//����ʤ����Ϣ
	public void update()
	{
		labelWin.setText("ʤ:" + getWin());
		labelLose.setText("��:" + getLose());
		labeltie.setText("ƽ:" + getTie());
	}
	
    public void winner()
    {
    	win++;
    }
    public void loser()
    {
    	lose++;
    }
    public void tier()
    {
    	tie++;
    }
    public int getWin()
    {
    	return win;
    }
    public int getLose()
    {
    	return lose;
    }
    public int getTie()
    {
    	return tie;
    }
    public void play()
    {
    	user.play();
    }
}
