package user;

import java.util.ArrayList;

import javax.swing.text.StyledEditorKit.BoldAction;

import org.omg.CORBA.PRIVATE_MEMBER;

import chessboard.Chess;
import chessboard.ChessBoard;

public class Computer extends User{

	public Computer(ChessBoard chessBoard) {
		super(chessBoard);
		// TODO Auto-generated constructor stub
		init();
	}
	
	private void init(){
		userType = UserType.COMPUTER; //�û�����Ϊ�����
	}
	
	public UserType getUserType(){   //��ȡ�û�����
		return userType;
	}
	
	/**
	 * ��ֵ������
	 * @param grid ���й�ֵ������
	 * @param chess ���й�ֵ�����ӷ�����ɫ���ɫ��
	 * @return score ��ֵ������ǰ�÷֣�  (-1:��Ϸ�������÷���ʤ��)
	 */
	public int Evaluate(int[][] grid , Chess chess)
	{
		int score = 0;//���յ÷�
		
		/* ��С�����е�����1-9����ʾ�ĸ����� */
		
		// 456������е÷ּ���
		int value = Evaluate456(grid, chess);
		System.out.println("456�����ֵ��" + value);
		if(value == -1)
			return -1;
		else
			score += value;
		
		// 258������е÷ּ���
		value = Evaluate258(grid, chess);
		System.out.println("258�����ֵ��" + value);
		if(value == -1)
			return -1;
		else
			score += value;
		
		// 159������е÷ּ���
		value = Evaluate159(grid, chess);
		System.out.println("159�����ֵ��" + value);
		if(value == -1)
			return -1;
		else
			score += value;
		
		// 357������е÷ּ���
		value = Evaluate357(grid, chess);
		System.out.println("357�����ֵ��" + value);
		if(value == -1)
			return -1;
		else
			score += value;
		
		return score;
	}
	
	//��ֵ����456����
	private int Evaluate456(int[][] grid , Chess chess)
	{
		int score = 0;//Ĭ��Ϊ0   ���յ÷�
		int count;//��ǰ������  
		int countJump;//��Ծ����������Ϊ��֮���ɨ�費���������д洢
		int countDead; //������    0-����������    1-һ�߶�ס     2-���߱���
		int countBorder; //�߽�֮��ľ���      1�����̱�Ե     2���з�����
		Boolean isJump; //�Ƿ��Ѿ����ո�����жϡ�           ����10101  ���������Ҫ��ǰ�������������ո��ж�ֻ�ܽ���һ��
		
		for(int i=0; i<chessBoard.getRowsCount(); i++)   //������
		{
			count = 0;//������
			countJump = 0;//������
			countDead = 0; //������
			isJump = Boolean.FALSE; //�Ƿ�����
			countBorder = 0;
			
			/*
			 * ���������ӣ�1�������������ļ�¼������Ϊ������־�������־
			 *           2���������¼������   
			 * ����Ϊ��ǰ���ӣ���Ϊ������м�¼
			 * ����Ϊ�������ӣ���Ϊ������־��������׷�������־
			 */
			
			for(int j=0; j<chessBoard.getColumnCount(); j++)  //������
			{
				//��������У�  ��϶���һ���Ƕ�ס�ġ�
				if(j == 0)
				{
					countDead = 1;
					countBorder = 0; //���̱�ԵΪ���
				}
				
				//��ǰ����Ϊ��
				if(grid[j][i] == Chess.EMPTY.getValue())
				{
					countBorder++; //�������̱�Ե  �� ��������
					
					//�����ǰ������Ϊ�գ����ÿո���������������ʶ���ɺ���
					if(count == 0)
					{
						countDead = 0;
					}
					else  //�����û���壬�ɽ��������жϣ� ������Ϊ������ǣ�
					{
						//���й�����
						if(isJump)
						{
							int value = getScore(count, countDead, isJump);
							if(value == -1) //��Ϸ����
								return -1; 
							score += value;
							
							count = countJump;//������
							countJump = 0;//������
							countDead = 0; //������
							isJump = Boolean.FALSE; //�Ƿ�����
						}
						else
						{
							//��������
							isJump = Boolean.TRUE;
						}
					}
						
				}
				else if(grid[j][i] == chess.getValue())  //�ҷ�����
				{
					countBorder++; //�������̱�Ե  �� ��������
					count++; //����������
					
					//��������壬������������
					if(isJump)
						countJump++;
				}
				else{  //��������
					if(countBorder < 5)//�ڲ�С��5 ����ʹ���Ե֮���пո�Ҳ�൱�����߱��£�
					{
						countDead = 2; //
					}
					countBorder = 0;//���������൱�ڱ�Ե�����¼������
					if(count > 0)
					{
						//�ø��������Ϊ�ҷ����ӣ���������Ϊ����
						//�����壬countJump = 0 
						//�����壬��countJump = 0��������Ÿø������Ϊ�ո�
						if(!isJump || countJump != 0)
							countDead ++;
						
						int value = getScore(count, countDead, isJump);
						if(value == -1) //��Ϸ����
							return -1; 
						score += value;
						
						
						
					}
					
					count = 0;//������     ���������з����ӱ�Ȼ�ж�
					countJump = 0;//������
					countDead = 1; ////������Ϊ�з����ӣ�������һ������Ĭ��һ�߱���
					isJump = Boolean.FALSE; //�Ƿ�����
				}
				
				//���һ��
				if(j == chessBoard.getColumnCount() - 1)
				{
					if(countBorder < 5)//�ڲ�С��5 ����ʹ���Ե֮���пո�Ҳ�൱�����߱��£�
					{
						countDead = 2; //
					}
					
					if(count > 0)
					{
						//�ø��������Ϊ�ҷ����ӣ���������Ϊ����
						//�����壬countJump = 0 
						//�����壬��countJump = 0��������Ÿø������Ϊ�ո�
						if(!isJump || countJump != 0)
							countDead ++;
						
						int value = getScore(count, countDead, isJump);
						if(value == -1) //��Ϸ����
							return -1; 
						score += value;
							
					}
				}
			}
		}
		
		//�÷������
		return score;
	}
	
	//��ֵ����258����
	private int Evaluate258(int[][] grid , Chess chess)
	{
		int score = 0;//Ĭ��Ϊ0   ���յ÷�
		int count;//��ǰ������  
		int countJump;//��Ծ����������Ϊ��֮���ɨ�費���������д洢
		int countDead; //������
		int countBorder;//�߽�֮��ľ���      1�����̱�Ե     2���з�����
		Boolean isJump; //�Ƿ��Ѿ����ո�����жϡ�           ����10101  ���������Ҫ��ǰ�������������ո��ж�ֻ�ܽ���һ��
		
		for(int i=0; i<chessBoard.getColumnCount(); i++)   //������
		{
			count = 0;//������
			countJump = 0;//������
			countDead = 0; //������
			countBorder = 0;
			isJump = Boolean.FALSE; //�Ƿ�����
			
			/*
			 * ���������ӣ�1�������������ļ�¼������Ϊ������־�������־
			 *           2���������¼������   
			 * ����Ϊ��ǰ���ӣ���Ϊ������м�¼
			 * ����Ϊ�������ӣ���Ϊ������־��������׷�������־
			 */
			
			for(int j=0; j<chessBoard.getRowsCount(); j++)  //������
			{
				//��������У�  ��϶���һ���Ƕ�ס�ġ�
				if(j == 0)
				{
					countDead = 1;
					countBorder = 0; //���̱�ԵΪ���
				}
				
				//��ǰ����Ϊ��
				if(grid[i][j] == Chess.EMPTY.getValue())
				{
					countBorder++; //�������̱�Ե  �� ��������
					
					//�����ǰ������Ϊ�գ����ÿո���������������ʶ���ɺ���
					if(count == 0)
					{
						countDead = 0;
					}
					else  //�����û���壬�ɽ��������жϣ� ������Ϊ������ǣ�
					{
						//���й�����
						if(isJump)
						{
							int value = getScore(count, countDead, isJump);
							if(value == -1) //��Ϸ����
								return -1; 
							score += value;
							
							count = countJump;//������
							countJump = 0;//������
							countDead = 0; //�Ƿ�����
							isJump = Boolean.FALSE; //�Ƿ�����
						}
						else
						{
							//��������
							isJump = Boolean.TRUE;
						}
					}
						
				}
				else if(grid[i][j] == chess.getValue())  //�ҷ�����
				{
					countBorder++; //�������̱�Ե  �� ��������
					count++; //����������
					
					//��������壬������������
					if(isJump)
						countJump++;
				}
				else{  //��������
					if(countBorder < 5)//�ڲ�С��5 ����ʹ���Ե֮���пո�Ҳ�൱�����߱��£�
					{
						countDead = 2; //
					}
					countBorder = 0; //���¼���߽��ľ���
					
					if(count > 0)
					{
						//�ø��������Ϊ�ҷ����ӣ���������Ϊ����
						//�����壬countJump = 0 
						//�����壬��countJump = 0��������Ÿø������Ϊ�ո�
						if(!isJump || countJump != 0)
							countDead++;
						
						int value = getScore(count, countDead, isJump);
						if(value == -1) //��Ϸ����
							return -1; 
						score += value;
						
						
						
					}
					
					count = 0;//������     ���������з����ӱ�Ȼ�ж�
					countJump = 0;//������
					countDead = 1; //������Ϊ�з����ӣ�������һ������Ĭ��һ�߱���
					isJump = Boolean.FALSE; //�Ƿ�����
				}
				
				//���һ��
				if(j == chessBoard.getRowsCount() - 1)
				{
					if(countBorder < 5)//�ڲ�С��5 ����ʹ���Ե֮���пո�Ҳ�൱�����߱��£�
					{
						countDead = 2; //
					}
					if(count > 0)
					{
						//�ø��������Ϊ�ҷ����ӣ���������Ϊ����
						//�����壬countJump = 0 
						//�����壬��countJump = 0��������Ÿø������Ϊ�ո�
						if(!isJump || countJump != 0)
							countDead ++;
						
						int value = getScore(count, countDead, isJump);
						if(value == -1) //��Ϸ����
							return -1; 
						score += value;
							
					}
				}
			}
		}
		
		//�÷������
		return score;
	}
	
	//��ֵ����159����
	private int Evaluate159(int[][] grid , Chess chess)
	{
		int score = 0;//Ĭ��Ϊ0   ���յ÷�
		int count;//��ǰ������  
		int countJump;//��Ծ����������Ϊ��֮���ɨ�費���������д洢
		int countDead; //������
		int countBorder;//�߽�֮��ľ���      1�����̱�Ե     2���з�����
		Boolean isJump; //�Ƿ��Ѿ����ո�����жϡ�           ����10101  ���������Ҫ��ǰ�������������ո��ж�ֻ�ܽ���һ��
		
		//�°벿�������Σ����µ�����
		for(int k=0; k<chessBoard.getColumnCount()-4; k++)   //��
		{
			count = 0;//������
			countJump = 0;//������
			countDead = 0; //������
			countBorder = 0;
			isJump = Boolean.FALSE; //�Ƿ�����
			
			/*
			 * ���������ӣ�1�������������ļ�¼������Ϊ������־�������־
			 *           2���������¼������   
			 * ����Ϊ��ǰ���ӣ���Ϊ������м�¼
			 * ����Ϊ�������ӣ���Ϊ������־��������׷�������־
			 */
			
			for(int i=k,j=chessBoard.getRowsCount()-1; i<chessBoard.getColumnCount(); i++,j--)  //����б��
			{
				//��������У�  ��϶���һ���Ƕ�ס�ġ�
				if(j == chessBoard.getRowsCount() - 1)
				{
					countDead = 1;
					countBorder = 0; //���̱�ԵΪ���
				}
				
				//��ǰ����Ϊ��
				if(grid[i][j] == Chess.EMPTY.getValue())
				{
					countBorder++; //�������̱�Ե  �� ��������
					
					//�����ǰ������Ϊ�գ����ÿո���������������ʶ���ɺ���
					if(count == 0)
					{
						countDead = 0;
					}
					else  //�����û���壬�ɽ��������жϣ� ������Ϊ������ǣ�
					{
						//���й�����
						if(isJump)
						{
							int value = getScore(count, countDead, isJump);
							if(value == -1) //��Ϸ����
								return -1; 
							score += value;
							
							count = countJump;//������
							countJump = 0;//������
							countDead = 0; //�Ƿ�����
							isJump = Boolean.FALSE; //�Ƿ�����
						}
						else
						{
							//��������
							isJump = Boolean.TRUE;
						}
					}
						
				}
				else if(grid[i][j] == chess.getValue())  //�ҷ�����
				{
					countBorder++; //�������̱�Ե  �� ��������
					count++; //����������
					
					//��������壬������������
					if(isJump)
						countJump++;
				}
				else{  //��������
					if(countBorder < 5)//�ڲ�С��5 ����ʹ���Ե֮���пո�Ҳ�൱�����߱��£�
					{
						countDead = 2; //
					}
					countBorder = 0; //���¼���߽��ľ���
					
					if(count > 0)
					{
						//�ø��������Ϊ�ҷ����ӣ���������Ϊ����
						//�����壬countJump = 0 
						//�����壬��countJump = 0��������Ÿø������Ϊ�ո�
						if(!isJump || countJump != 0)
							countDead++;
						
						int value = getScore(count, countDead, isJump);
						if(value == -1) //��Ϸ����
							return -1; 
						score += value;
						
						
						
					}
					
					count = 0;//������     ���������з����ӱ�Ȼ�ж�
					countJump = 0;//������
					countDead = 1; //������Ϊ�з����ӣ�������һ������Ĭ��һ�߱���
					isJump = Boolean.FALSE; //�Ƿ�����
				}
				
				//���һ��
				if(i == chessBoard.getColumnCount() - 1)
				{
					if(countBorder < 5)//�ڲ�С��5 ����ʹ���Ե֮���пո�Ҳ�൱�����߱��£�
					{
						countDead = 2; //
					}
					if(count > 0)
					{
						//�ø��������Ϊ�ҷ����ӣ���������Ϊ����
						//�����壬countJump = 0 
						//�����壬��countJump = 0��������Ÿø������Ϊ�ո�
						if(!isJump || countJump != 0)
							countDead ++;
						
						int value = getScore(count, countDead, isJump);
						if(value == -1) //��Ϸ����
							return -1; 
						score += value;
							
					}
				}
			}
		}
		
		//�ϰ벿�������� ��  ���ϵ�����
		for(int k=chessBoard.getColumnCount()-1-1; k>=4; k--)   //��
		{
			count = 0;//������
			countJump = 0;//������
			countDead = 0; //������
			countBorder = 0;
			isJump = Boolean.FALSE; //�Ƿ�����
			
			/*
			 * ���������ӣ�1�������������ļ�¼������Ϊ������־�������־
			 *           2���������¼������   
			 * ����Ϊ��ǰ���ӣ���Ϊ������м�¼
			 * ����Ϊ�������ӣ���Ϊ������־��������׷�������־
			 */
			
			for(int i=k,j=0; i>=0; i--,j++)  //����б��
			{
				//��������У�  ��϶���һ���Ƕ�ס�ġ�
				if(j == 0)
				{
					countDead = 1;
					countBorder = 0; //���̱�ԵΪ���
				}
				
				//��ǰ����Ϊ��
				if(grid[i][j] == Chess.EMPTY.getValue())
				{
					countBorder++; //�������̱�Ե  �� ��������
					
					//�����ǰ������Ϊ�գ����ÿո���������������ʶ���ɺ���
					if(count == 0)
					{
						countDead = 0;
					}
					else  //�����û���壬�ɽ��������жϣ� ������Ϊ������ǣ�
					{
						//���й�����
						if(isJump)
						{
							int value = getScore(count, countDead, isJump);
							if(value == -1) //��Ϸ����
								return -1; 
							score += value;
							
							count = countJump;//������
							countJump = 0;//������
							countDead = 0; //�Ƿ�����
							isJump = Boolean.FALSE; //�Ƿ�����
						}
						else
						{
							//��������
							isJump = Boolean.TRUE;
						}
					}
						
				}
				else if(grid[i][j] == chess.getValue())  //�ҷ�����
				{
					countBorder++; //�������̱�Ե  �� ��������
					count++; //����������
					
					//��������壬������������
					if(isJump)
						countJump++;
				}
				else{  //��������
					if(countBorder < 5)//�ڲ�С��5 ����ʹ���Ե֮���пո�Ҳ�൱�����߱��£�
					{
						countDead = 2; //
					}
					countBorder = 0; //���¼���߽��ľ���
					
					if(count > 0)
					{
						//�ø��������Ϊ�ҷ����ӣ���������Ϊ����
						//�����壬countJump = 0 
						//�����壬��countJump = 0��������Ÿø������Ϊ�ո�
						if(!isJump || countJump != 0)
							countDead++;
						
						int value = getScore(count, countDead, isJump);
						if(value == -1) //��Ϸ����
							return -1; 
						score += value;
						
						
						
					}
					
					count = 0;//������     ���������з����ӱ�Ȼ�ж�
					countJump = 0;//������
					countDead = 1; //������Ϊ�з����ӣ�������һ������Ĭ��һ�߱���
					isJump = Boolean.FALSE; //�Ƿ�����
				}
				
				//���һ��
				if(i == 0)
				{
					if(countBorder < 5)//�ڲ�С��5 ����ʹ���Ե֮���пո�Ҳ�൱�����߱��£�
					{
						countDead = 2; //
					}
					if(count > 0)
					{
						//�ø��������Ϊ�ҷ����ӣ���������Ϊ����
						//�����壬countJump = 0 
						//�����壬��countJump = 0��������Ÿø������Ϊ�ո�
						if(!isJump || countJump != 0)
							countDead ++;
						
						int value = getScore(count, countDead, isJump);
						if(value == -1) //��Ϸ����
							return -1; 
						score += value;
							
					}
				}
			}
		}
		
		//�÷������
		return score;
	}
	//��ֵ����357����
	private int Evaluate357(int[][] grid , Chess chess)
	{
		int score = 0;//Ĭ��Ϊ0   ���յ÷�
		int count;//��ǰ������  
		int countJump;//��Ծ����������Ϊ��֮���ɨ�費���������д洢
		int countDead; //������
		int countBorder;//�߽�֮��ľ���      1�����̱�Ե     2���з�����
		Boolean isJump; //�Ƿ��Ѿ����ո�����жϡ�           ����10101  ���������Ҫ��ǰ�������������ո��ж�ֻ�ܽ���һ��
		
		//�ϰ벿�������Σ����ϵ�����
		for(int k=0; k<chessBoard.getColumnCount()-4; k++)   //��
		{
			count = 0;//������
			countJump = 0;//������
			countDead = 0; //������
			countBorder = 0;
			isJump = Boolean.FALSE; //�Ƿ�����
			
			/*
			 * ���������ӣ�1�������������ļ�¼������Ϊ������־�������־
			 *           2���������¼������   
			 * ����Ϊ��ǰ���ӣ���Ϊ������м�¼
			 * ����Ϊ�������ӣ���Ϊ������־��������׷�������־
			 */
			
			for(int i=k,j=0; i<chessBoard.getColumnCount(); i++,j++)  //����б��
			{
				//��������У�  ��϶���һ���Ƕ�ס�ġ�
				if(j == 0)
				{
					countDead = 1;
					countBorder = 0; //���̱�ԵΪ���
				}
				
				//��ǰ����Ϊ��
				if(grid[i][j] == Chess.EMPTY.getValue())
				{
					countBorder++; //�������̱�Ե  �� ��������
					
					//�����ǰ������Ϊ�գ����ÿո���������������ʶ���ɺ���
					if(count == 0)
					{
						countDead = 0;
					}
					else  //�����û���壬�ɽ��������жϣ� ������Ϊ������ǣ�
					{
						//���й�����
						if(isJump)
						{
							int value = getScore(count, countDead, isJump);
							if(value == -1) //��Ϸ����
								return -1; 
							score += value;
							
							count = countJump;//������
							countJump = 0;//������
							countDead = 0; //�Ƿ�����
							isJump = Boolean.FALSE; //�Ƿ�����
						}
						else
						{
							//��������
							isJump = Boolean.TRUE;
						}
					}
						
				}
				else if(grid[i][j] == chess.getValue())  //�ҷ�����
				{
					countBorder++; //�������̱�Ե  �� ��������
					count++; //����������
					
					//��������壬������������
					if(isJump)
						countJump++;
				}
				else{  //��������
					if(countBorder < 5)//�ڲ�С��5 ����ʹ���Ե֮���пո�Ҳ�൱�����߱��£�
					{
						countDead = 2; //
					}
					countBorder = 0; //���¼���߽��ľ���
					
					if(count > 0)
					{
						//�ø��������Ϊ�ҷ����ӣ���������Ϊ����
						//�����壬countJump = 0 
						//�����壬��countJump = 0��������Ÿø������Ϊ�ո�
						if(!isJump || countJump != 0)
							countDead++;
						
						int value = getScore(count, countDead, isJump);
						if(value == -1) //��Ϸ����
							return -1; 
						score += value;
						
						
						
					}
					
					count = 0;//������     ���������з����ӱ�Ȼ�ж�
					countJump = 0;//������
					countDead = 1; //������Ϊ�з����ӣ�������һ������Ĭ��һ�߱���
					isJump = Boolean.FALSE; //�Ƿ�����
				}
				
				//���һ��
				if(i == chessBoard.getColumnCount() - 1)
				{
					if(countBorder < 5)//�ڲ�С��5 ����ʹ���Ե֮���пո�Ҳ�൱�����߱��£�
					{
						countDead = 2; //
					}
					if(count > 0)
					{
						//�ø��������Ϊ�ҷ����ӣ���������Ϊ����
						//�����壬countJump = 0 
						//�����壬��countJump = 0��������Ÿø������Ϊ�ո�
						if(!isJump || countJump != 0)
							countDead ++;
						
						int value = getScore(count, countDead, isJump);
						if(value == -1) //��Ϸ����
							return -1; 
						score += value;
							
					}
				}
			}
		}
		
		//�ϰ벿�������� ��  ���µ�����
		for(int k=chessBoard.getColumnCount()-1-1; k>=4; k--)   //��
		{
			count = 0;//������
			countJump = 0;//������
			countDead = 0; //������
			countBorder = 0;
			isJump = Boolean.FALSE; //�Ƿ�����
			
			/*
			 * ���������ӣ�1�������������ļ�¼������Ϊ������־�������־
			 *           2���������¼������   
			 * ����Ϊ��ǰ���ӣ���Ϊ������м�¼
			 * ����Ϊ�������ӣ���Ϊ������־��������׷�������־
			 */
			
			for(int i=k,j=chessBoard.getRowsCount()-1; i>=0; i--,j--)  //����б��
			{
				//��������У�  ��϶���һ���Ƕ�ס�ġ�
				if(j == chessBoard.getRowsCount()-1)
				{
					countDead = 1;
					countBorder = 0; //���̱�ԵΪ���
				}
				
				//��ǰ����Ϊ��
				if(grid[i][j] == Chess.EMPTY.getValue())
				{
					countBorder++; //�������̱�Ե  �� ��������
					
					//�����ǰ������Ϊ�գ����ÿո���������������ʶ���ɺ���
					if(count == 0)
					{
						countDead = 0;
					}
					else  //�����û���壬�ɽ��������жϣ� ������Ϊ������ǣ�
					{
						//���й�����
						if(isJump)
						{
							int value = getScore(count, countDead, isJump);
							if(value == -1) //��Ϸ����
								return -1; 
							score += value;
							
							count = countJump;//������
							countJump = 0;//������
							countDead = 0; //�Ƿ�����
							isJump = Boolean.FALSE; //�Ƿ�����
						}
						else
						{
							//��������
							isJump = Boolean.TRUE;
						}
					}
						
				}
				else if(grid[i][j] == chess.getValue())  //�ҷ�����
				{
					countBorder++; //�������̱�Ե  �� ��������
					count++; //����������
					
					//��������壬������������
					if(isJump)
						countJump++;
				}
				else{  //��������
					if(countBorder < 5)//�ڲ�С��5 ����ʹ���Ե֮���пո�Ҳ�൱�����߱��£�
					{
						countDead = 2; //
					}
					countBorder = 0; //���¼���߽��ľ���
					
					if(count > 0)
					{
						//�ø��������Ϊ�ҷ����ӣ���������Ϊ����
						//�����壬countJump = 0 
						//�����壬��countJump = 0��������Ÿø������Ϊ�ո�
						if(!isJump || countJump != 0)
							countDead++;
						
						int value = getScore(count, countDead, isJump);
						if(value == -1) //��Ϸ����
							return -1; 
						score += value;
						
						
						
					}
					
					count = 0;//������     ���������з����ӱ�Ȼ�ж�
					countJump = 0;//������
					countDead = 1; //������Ϊ�з����ӣ�������һ������Ĭ��һ�߱���
					isJump = Boolean.FALSE; //�Ƿ�����
				}
				
				//���һ��
				if(i == 0)
				{
					if(countBorder < 5)//�ڲ�С��5 ����ʹ���Ե֮���пո�Ҳ�൱�����߱��£�
					{
						countDead = 2; //
					}
					if(count > 0)
					{
						//�ø��������Ϊ�ҷ����ӣ���������Ϊ����
						//�����壬countJump = 0 
						//�����壬��countJump = 0��������Ÿø������Ϊ�ո�
						if(!isJump || countJump != 0)
							countDead ++;
						
						int value = getScore(count, countDead, isJump);
						if(value == -1) //��Ϸ����
							return -1; 
						score += value;
							
					}
				}
			}
		}
		
		//�÷������
		return score;
	}
	
	
	/**
	 * �Ʒֺ����������̵ķ����ж�
	 * @param count ������
	 * @param countDead ���壺���б���ס�����ӣ�   ��countDeadΪ2ʱ���������߶�����ס
	 * @return ����
	 */
	private int getScore(int count , int countDead , Boolean isJump)
	{
		//����Ϊ0   0��
		if(count == 0)
			return 0;
		
		//���߶���ס�����
		//1������С��5��   ����û������                   2������С��4
		if(count < 5 && !isJump && countDead >= 2 || count < 4 && countDead >= 2)
			return 0;
		
		/**     �Ʒְ壨����һ�������±�ӷ֣�����������⣩
		 * ��һ                    1
		 * ����/��һ          10
		 * ����/���          100
		 * ����/����          1000
		 * ����                    10000
		 * ��������            -1      
		 */
		final int ZERO = 0;
		final int DEAD_ONE = 1;
		final int DEAD_TWO, LIVE_ONE = 10;
		final int DEAD_THREE, LIVE_TWO = 100;
		final int DEAD_FOUR, LIVE_THREE = 1000;
		final int LIVE_FOUR = 10000;
		final int FIVE = -1;
		
		
		//�����壬���������壬��ô��������
		if(count >= 5 && countDead > 0)
			count = 4;
		
		if(count >= 5)  
			return FIVE;  //��������
		else
		{
			int score = 0;//��õķ���
			
			//���趼�ǻ���
			switch(count)
			{
			case 1:score = LIVE_ONE;break;
			case 2:score = LIVE_TWO;break;
			case 3:score = LIVE_THREE;break;
			case 4:score = LIVE_FOUR;break;
			default:;break; //
			}
			
			//�ж��Ƿ�Ϊ����,   ��Ϊ���������պ���10���ķ��������ֱ�ӳ�10����
			if(countDead > 0 && score!=ZERO)
				score = score/10;
			
			
			//���ط���
			return score;
			
		}
	}
	
	//������
	class GameTree {
		
		int color; //����������ɫ
		ArrayList<GameTree> childList;//�����б�
		private int[][] grid; //��ǰ����
		
		public GameTree() {
			// TODO Auto-generated constructor stub
		}
	}

}
