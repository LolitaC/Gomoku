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
	final private int ROWS = 15;  //����
	final private int COLMNS = 15; //����
	private int[][] grid; //��������    0--������      1--������       2--������
	final private int IMAGE_WIDTH = 600 ;   //ͼƬ��ԭʼ���
	final private int IMAGE_HEIGHT = 600 ;   //ͼƬ��ԭʼ�߶�
	final private int BORDER_THICKNESS = 24 + 24;  //ԭʼͼƬ�߿���  + �߿������̵ļ��
	final private int SPACE = 36 ;          //ԭʼͼƬ  ��� 
	final private int CLIABLE_RADIU = 12; //ԭʼͼƬ    ����������Ч�뾶   �����ڣ�0��0�����Ϸ������ӣ���ô����õ�Ϊԭ��  �߳�Ϊ��ֵ��������
	
	final private int WHITE = 2; //����
	final private int BLACK = 1; //����
	private int current;//��ǰ����
	
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
		
		
		//ע��������
		this.addMouseListener(this);
		
		init_game();
		
	}
	
	public void paint(Graphics g)
	{
		//����
		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
		
		double scaleX = this.getWidth()*1.0/IMAGE_WIDTH;   //���ű���
		double scaleY = this.getHeight()*1.0/IMAGE_HEIGHT;

		
		//����  + ����
		for(int i=0; i<ROWS; i++)
		{
			//����
			int x1 = (int) (BORDER_THICKNESS*scaleX + SPACE*scaleX*i);
			int y1 = (int) (BORDER_THICKNESS*scaleY);
			int x2 = (int) (BORDER_THICKNESS*scaleX + SPACE*scaleX*i);
			int y2 = (int) (getHeight()-BORDER_THICKNESS*scaleY);
			g.drawLine( x1, y1, x2, y2);
			
			//����
			x1 = (int)(BORDER_THICKNESS*scaleX);
			y1 = (int)(BORDER_THICKNESS*scaleY + SPACE*scaleY*i);
			x2 = (int)(getWidth() - BORDER_THICKNESS*scaleX);
			y2 = (int)(BORDER_THICKNESS*scaleY + SPACE*scaleY*i);
			g.drawLine( x1, y1, x2, y2);
			
		}
		
		
		//������
		for(int i=0; i<ROWS; i++)
		{
			for(int j=0; j<COLMNS; j++)
			{
				
				if(grid[i][j] == 1) //������
				{
					int width = (int)(SPACE*scaleX);
					int height = (int)(SPACE*scaleY);
					int x1 = (int) (BORDER_THICKNESS*scaleX + SPACE*scaleX*i - width*1.0/2);
					int y1 = (int)(BORDER_THICKNESS*scaleY + SPACE*scaleY*j - height*1.0/2);

					//�������
					final int shadeX = (int) (25*scaleX);
					final int shadeY = (int) (10*scaleY);
					final int shadeRadius = (int) (SPACE*scaleX*2/3);
					
					
					//����
					RadialGradientPaint paint = new RadialGradientPaint(x1+shadeX, y1+shadeY, shadeRadius, new float[]{0f, 1f}  
		               , new Color[]{Color.WHITE, Color.BLACK});  
		               ((Graphics2D) g).setPaint(paint);  
		               ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  
		               ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);
		               
		            //x1 -- ���Ͻ�x����      y1 -- ���Ͻ�y����       width -- ���        height -- �߶�
		            Ellipse2D e = new Ellipse2D.Float(x1, y1, width, height);  
		            ((Graphics2D) g).fill(e); 
				}
				else if(grid[i][j] == 2) //������
				{
					int width = (int)(SPACE*scaleX);
					int height = (int)(SPACE*scaleY);
					int x1 = (int) (BORDER_THICKNESS*scaleX + SPACE*scaleX*i - width*1.0/2);
					int y1 = (int)(BORDER_THICKNESS*scaleY + SPACE*scaleY*j - height*1.0/2);

					//�������
					final int shadeX = (int) (25*scaleX);
					final int shadeY = (int) (10*scaleY);
					final int shadeRadius = (int) (SPACE*scaleX*2);
					
					
					//����
					RadialGradientPaint paint = new RadialGradientPaint(x1+shadeX, y1+shadeY, shadeRadius, new float[]{0f, 1f}  
		               , new Color[]{Color.WHITE, Color.BLACK});  
		               ((Graphics2D) g).setPaint(paint);  
		               ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  
		               ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);
		               
		            //x1 -- ���Ͻ�x����      y1 -- ���Ͻ�y����       width -- ���        height -- �߶�
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
		
		if(e.getButton() != MouseEvent.BUTTON1) //����Ĳ���������
			return;
		
		double scaleX = this.getWidth()*1.0/IMAGE_WIDTH;   //���ű���
		double scaleY = this.getHeight()*1.0/IMAGE_HEIGHT;
		
		int i = (int) ((e.getX() - BORDER_THICKNESS*scaleX)/(SPACE*scaleX));//����
		int j =  (int) ((e.getY() - BORDER_THICKNESS*scaleY)/(SPACE*scaleY));
		
		int a = (int) ((e.getX() - BORDER_THICKNESS*scaleX)%(SPACE*scaleX));//��Ŀ��� ֮��x��ľ���
		int b = (int) ((e.getY() - BORDER_THICKNESS*scaleY)%(SPACE*scaleY));//��Ŀ��� ֮��y��ľ���
		
		//��ͨ������������ж����������Ϊ�ӽ���һ����(ͨ��SPACE/2���жϣ����С�ڣ��򿿽������ϲ࣬���򿿽��Ҳ���²ࣩ
		//Ȼ�����жϸõ㵽������ľ����Ƿ�����Ч�뾶�ڡ�
		//X����ж�
		if( a<0 ) //������������̵����
		{
			//����� ������CLIABLE_RADIU���ҵ�������������̵���࣬�������û�����ֱ꣬�Ӻ��Ӹ��¼�
			if (Math.abs(a) > CLIABLE_RADIU*scaleX) {
				return;
			}
			//�����Ч��i��a����Ҫ���иı�
		}
		else
		{
			//���Ҳ�Y������� CLIABLE_RADIU֮��
			if ( (SPACE*scaleX - a) < CLIABLE_RADIU*scaleX ) {
				
				//�����������������Ҳ�
				if( i>COLMNS-1 )
					return;
				
				//��ӽ���Y���Ϊ�Ҳ�
				i++;
				//��Y�����ı�
				a = (int) (SPACE*scaleX - a);	
				
			} 
			else if( a< CLIABLE_RADIU*scaleX){  //�����Y�������CLIABLE_RADIU֮��
				
				;//�������κβ���
			}
			else {   //�������Ҳ�ľ��붼��������Ч�뾶
				return;
			}
		}
		
		//Y����ж�
		if( b<0 ) //������������̵��ϲ�
		{
			//����� ������CLIABLE_RADIU���ҵ�������������̵��ϲ࣬�����ϲ�û�����ֱ꣬�Ӻ��Ӹ��¼�
			if (Math.abs(b) > CLIABLE_RADIU*scaleY) {
				return;
			}
		    //�����Ч��j��b����Ҫ���иı�
		}
		else
		{
			//���Ҳ�Y������� CLIABLE_RADIU֮��
			if ( (SPACE*scaleY - b) < CLIABLE_RADIU*scaleY ) {
						
			    //�����������������²�
				if( j>ROWS-1 )
					return;
						
				//��ӽ���X���Ϊ�²�
				j++;
				//��X�����ı�
				b = (int) (SPACE*scaleY - b);	
						
		    } 
		    else if( b< CLIABLE_RADIU*scaleY){  //���ϲ�X�������CLIABLE_RADIU֮��
						
			    ;//�������κβ���
			    }
		    else {   //���ϲ���²�ľ��붼��������Ч�뾶
			    return;
		    }
		}
		
		//System.out.println(i);
		//System.out.println(j);
		
		if(grid[i][j] == 0){
			grid[i][j] = current;
			//�ж��Ƿ�����������
			//...
			if (isWinner(i, j)) {
				
				repaint();
				if (current == WHITE) {
					JOptionPane.showMessageDialog(null, "�����ʤ", "��Ϸ����", JOptionPane.WARNING_MESSAGE);
				}
				else
					JOptionPane.showMessageDialog(null, "�����ʤ", "��Ϸ����", JOptionPane.WARNING_MESSAGE);
				
				//init_game();
				return;
			}
			//û���������壬�ı䵱ǰ���巽
			current = current == WHITE ? BLACK:WHITE;
			
		    repaint();
		}
		
		
		//������Ӧ��Χ
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
		int count = 1;//������������5--��������
		
		//�����ּ��̵�9����������ʾ����
		//258����
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
		
		//456����
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
		
		//159����
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
		
		
		//753����
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
