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
	
	Image image;//人物图标
	CheckboxGroup c;
	String color;//黑棋放还是白棋方
	Chess chess;
	
	User user;
	JLabel labelWin;
	JLabel labelLose;
	JLabel labeltie;
	
    
    int win = 0;  //赢
    int lose = 0; //输
    int tie = 0;  //平局
    
	public scoreBoard(String filename, Chess chess, User user){
		
		this.user = user;
		this.chess = chess;
		if(chess == Chess.BLACK)
			color = "黑棋方";
		else
			color = "白棋方";
		
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
		c = new CheckboxGroup();//单选组
		
        setLayout(null);
		
		JPanel panel_1 = new JPanel(){
			public void paint(Graphics g){
				g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
			}
		};
		panel_1.setBounds(10, 10, 121, 152);
		add(panel_1);
		
		//电脑  单选按钮
		Checkbox checkbox = new Checkbox("电脑", c, chess == Chess.WHITE);
		checkbox.setEnabled(chess == Chess.WHITE);
		checkbox.setBounds(133, 22, 67, 23);
		add(checkbox);
		
		//人类 单选按钮
		Checkbox checkbox1 = new Checkbox("人类", c, chess == Chess.BLACK);
		checkbox1.setEnabled(chess == Chess.BLACK);
		checkbox1.setBounds(133, 52, 67, 23);
		add(checkbox1);
		
		//远程  单选按钮 ，用于以后的远程用户
		Checkbox checkbox2 = new Checkbox("远程", c,false);
		checkbox2.setEnabled(false);
		checkbox2.setBounds(133, 82, 67, 23);
		add(checkbox2);
		
		JLabel colorName = new JLabel(color);
		colorName.setFont(new Font("宋体", Font.PLAIN, 17));
		colorName.setBounds(133, 132, 67, 23);
		add(colorName);
		
		labelWin = new JLabel("胜:0");
		labelWin.setFont(new Font("宋体", Font.PLAIN, 17));
		labelWin.setBounds(10, 172, 56, 44);
		add(labelWin);
		
		labelLose = new JLabel("负:0");
		labelLose.setFont(new Font("宋体", Font.PLAIN, 17));
		labelLose.setBounds(70, 172, 56, 44);
		add(labelLose);
		
		labeltie = new JLabel("平:0");
		labeltie.setFont(new Font("宋体", Font.PLAIN, 17));
		labeltie.setBounds(130, 172, 56, 44);
		add(labeltie);
		
	}
	
	//返回当前棋子方
	public Chess getChess()
	{
		return chess;
	}
	
	//更新胜负信息
	public void update()
	{
		labelWin.setText("胜:" + getWin());
		labelLose.setText("负:" + getLose());
		labeltie.setText("平:" + getTie());
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
