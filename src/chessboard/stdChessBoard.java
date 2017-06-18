package chessboard;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class stdChessBoard extends ChessBoard {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Image image;
	final private int ROWS = 15;  //行数
	final private int COLMNS = 15; //列数
	private int[][] grid; //网格数组    0--无棋子      1--黑棋子       2--白棋子
	final private int IMAGE_WIDTH = 600 ;   //图片的原始宽度
	final private int IMAGE_HEIGHT = 600 ;   //图片的原始高度
	final private int BORDER_THICKNESS = 24 + 24;  //原始图片边框厚度  + 边框与棋盘的间隔
	final private int SPACE = 36 ;          //原始图片  间隔 
	final private int CLIABLE_RADIU = 12; //原始图片    放置棋子有效半径   例：在（0，0）点上放置棋子，那么点击该点为原点  边长为该值的正方形
	
	final private int WHITE = 2; //白棋
	final private int BLACK = 1; //黑棋
	private int current;//当前棋子
	
	public stdChessBoard() {
		// TODO Auto-generated constructor stub
		init();
		
	}
	
	private void init(){
		
		try {

            image=ImageIO.read(new File("resource/chessboard.jpg"));

        } catch (Exception e) {

            // TODO Auto-generated catch block

            e.printStackTrace();

        }
		
		
		//注册鼠标监听
		this.addMouseListener(this);
		
		init_game();
		
	}
	
	public void paint(Graphics g)
	{
		//棋盘
		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
		
		double scaleX = this.getWidth()*1.0/IMAGE_WIDTH;   //缩放比例
		double scaleY = this.getHeight()*1.0/IMAGE_HEIGHT;

		
		//纵线  + 横线
		for(int i=0; i<ROWS; i++)
		{
			//纵线
			int x1 = (int) (BORDER_THICKNESS*scaleX + SPACE*scaleX*i);
			int y1 = (int) (BORDER_THICKNESS*scaleY);
			int x2 = (int) (BORDER_THICKNESS*scaleX + SPACE*scaleX*i);
			int y2 = (int) (getHeight()-BORDER_THICKNESS*scaleY);
			g.drawLine( x1, y1, x2, y2);
			
			//横线
			x1 = (int)(BORDER_THICKNESS*scaleX);
			y1 = (int)(BORDER_THICKNESS*scaleY + SPACE*scaleY*i);
			x2 = (int)(getWidth() - BORDER_THICKNESS*scaleX);
			y2 = (int)(BORDER_THICKNESS*scaleY + SPACE*scaleY*i);
			g.drawLine( x1, y1, x2, y2);
			
		}
		
		
		//画棋子
		for(int i=0; i<ROWS; i++)
		{
			for(int j=0; j<COLMNS; j++)
			{
				
				if(grid[i][j] == 1) //黑棋子
				{
					int width = (int)(SPACE*scaleX);
					int height = (int)(SPACE*scaleY);
					int x1 = (int) (BORDER_THICKNESS*scaleX + SPACE*scaleX*i - width*1.0/2);
					int y1 = (int)(BORDER_THICKNESS*scaleY + SPACE*scaleY*j - height*1.0/2);

					//渐变参数
					final int shadeX = (int) (25*scaleX);
					final int shadeY = (int) (10*scaleY);
					final int shadeRadius = (int) (SPACE*scaleX*2/3);
					
					
					//渐变
					RadialGradientPaint paint = new RadialGradientPaint(x1+shadeX, y1+shadeY, shadeRadius, new float[]{0f, 1f}  
		               , new Color[]{Color.WHITE, Color.BLACK});  
		               ((Graphics2D) g).setPaint(paint);  
		               ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  
		               ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);
		               
		            //x1 -- 左上角x坐标      y1 -- 左上角y坐标       width -- 宽度        height -- 高度
		            Ellipse2D e = new Ellipse2D.Float(x1, y1, width, height);  
		            ((Graphics2D) g).fill(e); 
				}
				else if(grid[i][j] == 2) //白棋子
				{
					int width = (int)(SPACE*scaleX);
					int height = (int)(SPACE*scaleY);
					int x1 = (int) (BORDER_THICKNESS*scaleX + SPACE*scaleX*i - width*1.0/2);
					int y1 = (int)(BORDER_THICKNESS*scaleY + SPACE*scaleY*j - height*1.0/2);

					//渐变参数
					final int shadeX = (int) (25*scaleX);
					final int shadeY = (int) (10*scaleY);
					final int shadeRadius = (int) (SPACE*scaleX*2);
					
					
					//渐变
					RadialGradientPaint paint = new RadialGradientPaint(x1+shadeX, y1+shadeY, shadeRadius, new float[]{0f, 1f}  
		               , new Color[]{Color.WHITE, Color.BLACK});  
		               ((Graphics2D) g).setPaint(paint);  
		               ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  
		               ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);
		               
		            //x1 -- 左上角x坐标      y1 -- 左上角y坐标       width -- 宽度        height -- 高度
		            Ellipse2D e = new Ellipse2D.Float(x1, y1, width, height);  
		            ((Graphics2D) g).fill(e); 
				}
				
				
			}
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println(e.getX());
		//System.out.println(e.getY());
		
		if(e.getButton() != MouseEvent.BUTTON1) //点击的不是鼠标左键
			return;
		
		double scaleX = this.getWidth()*1.0/IMAGE_WIDTH;   //缩放比例
		double scaleY = this.getHeight()*1.0/IMAGE_HEIGHT;
		
		int i = (int) ((e.getX() - BORDER_THICKNESS*scaleX)/(SPACE*scaleX));//行列
		int j =  (int) ((e.getY() - BORDER_THICKNESS*scaleY)/(SPACE*scaleY));
		
		int a = (int) ((e.getX() - BORDER_THICKNESS*scaleX)%(SPACE*scaleX));//与目标点 之间x轴的距离
		int b = (int) ((e.getY() - BORDER_THICKNESS*scaleY)%(SPACE*scaleY));//与目标点 之间y轴的距离
		
		//先通过点击的坐标判断与该坐标最为接近的一个点(通过SPACE/2来判断，如果小于，则靠近左侧或上侧，否则靠近右侧或下侧）
		//然后再判断该点到该坐标的距离是否在有效半径内。
		//X轴的判断
		if( a<0 ) //点击坐标在棋盘的左侧
		{
			//距离点 超过了CLIABLE_RADIU并且点击的坐标在棋盘的左侧，所以左侧没有坐标，直接忽视该事件
			if (Math.abs(a) > CLIABLE_RADIU*scaleX) {
				return;
			}
			//点击有效，i跟a不需要进行改变
		}
		else
		{
			//与右侧Y轴距离在 CLIABLE_RADIU之内
			if ( (SPACE*scaleX - a) < CLIABLE_RADIU*scaleX ) {
				
				//如果点击坐标在棋盘右侧
				if( i>COLMNS-1 )
					return;
				
				//最接近的Y轴变为右侧
				i++;
				//与Y轴距离改变
				a = (int) (SPACE*scaleX - a);	
				
			} 
			else if( a< CLIABLE_RADIU*scaleX){  //与左侧Y轴距离在CLIABLE_RADIU之内
				
				;//无需做任何操作
			}
			else {   //与左侧和右侧的距离都超过了有效半径
				return;
			}
		}
		
		//Y轴的判断
		if( b<0 ) //点击坐标在棋盘的上侧
		{
			//距离点 超过了CLIABLE_RADIU并且点击的坐标在棋盘的上侧，所以上侧没有坐标，直接忽视该事件
			if (Math.abs(b) > CLIABLE_RADIU*scaleY) {
				return;
			}
		    //点击有效，j跟b不需要进行改变
		}
		else
		{
			//与右侧Y轴距离在 CLIABLE_RADIU之内
			if ( (SPACE*scaleY - b) < CLIABLE_RADIU*scaleY ) {
						
			    //如果点击坐标在棋盘下侧
				if( j>ROWS-1 )
					return;
						
				//最接近的X轴变为下侧
				j++;
				//与X轴距离改变
				b = (int) (SPACE*scaleY - b);	
						
		    } 
		    else if( b< CLIABLE_RADIU*scaleY){  //与上侧X轴距离在CLIABLE_RADIU之内
						
			    ;//无需做任何操作
			    }
		    else {   //与上侧和下侧的距离都超过了有效半径
			    return;
		    }
		}
		
		//System.out.println(i);
		//System.out.println(j);
		
		if(grid[i][j] == 0){
			grid[i][j] = current;
			//判断是否有五子连棋
			//...
			if (isWinner(i, j)) {
				
				repaint();
				if (current == WHITE) {
					JOptionPane.showMessageDialog(null, "白棋获胜", "游戏结束", JOptionPane.WARNING_MESSAGE);
				}
				else
					JOptionPane.showMessageDialog(null, "黑棋获胜", "游戏结束", JOptionPane.WARNING_MESSAGE);
				
				//init_game();
				return;
			}
			//没有五子连棋，改变当前下棋方
			current = current == WHITE ? BLACK:WHITE;
			
		    repaint();
		}
		
		
		//超出响应范围
		//if()
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private Boolean isWinner(int x,int y)
	{
		int count = 1;//棋子连棋数，5--五子连棋
		
		//用数字键盘的9个数字来表示方向
		//258方向
		for(int i=y-1; i>=0; i--)
		{
			if(grid[x][i] == current)
			{
				count++;
			}
			else
				break;
		}
		for(int i=y+1; i<ROWS; i++)
		{
			if(grid[x][i] == current)
				count++;
			else
				break;
		}
		if(count >= 5)
			return Boolean.TRUE;
		else
			count = 1;
		
		//456方向
		for(int i=x-1; i>=0; i--)
		{
			if(grid[i][y] == current)
				count++;
			else
				break;
		}
		for(int i=x+1; i<COLMNS; i++)
		{
			if(grid[i][y] == current)
				count++;
			else
				break;
		}
		if(count >= 5)
			return Boolean.TRUE;
		else
			count = 1;
		
		//159方向
		for(int i=x-1,j=y+1; i>=0 && j<ROWS; i--,j++)
		{
			if(grid[i][j] == current)
				count++;
			else
				break;
		}
		for(int i=x+1,j=y-1; i<COLMNS && j>=0; i++,j--)
		{
			if(grid[i][j] == current)
				count++;
			else
				break;
		}
		if(count >= 5)
			return Boolean.TRUE;
		else
			count = 1;
		
		
		//753方向
		for(int i=x-1,j=y-1; i>=0 && j>=0; i--,j--)
		{
			if(grid[i][j] == current)
				count++;
			else
				break;
		}
		for(int i=x+1,j=y+1; i<COLMNS && j<ROWS; i++,j++)
		{
			if(grid[i][j] == current)
				count++;
			else
				break;
		}
		if(count >= 5)
			return Boolean.TRUE;
		else
			count = 1;
		
		
		return Boolean.FALSE;
	}
	
	private void init_game(){
		grid = new int[ROWS][COLMNS];
		current = BLACK;
		repaint();
	}
	
	public void newGame(){
		init_game();
	}

	@Override
	public int getRowsCount() {
		// TODO Auto-generated method stub
		return ROWS;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return COLMNS;
	}
	
	public int[][] getGrid()
	{
		return grid.clone();
	}
}
