package user;


import java.awt.Point;
import java.sql.Struct;
import java.util.ArrayList;

import chessboard.Chess;
import chessboard.ChessBoard;
/**
 * 计算机类(AI)
 * 已完成：棋盘评分
 * 待完善：评分细节，具体看getScore（）函数
 * @author LolitaC
 *
 */
public class Computer extends User{

	final int MAX = 1000000;
	final int MIN = -1000000;
	Chess chess = Chess.WHITE;//本方棋子 （先固定为白棋）
	
	public Computer(ChessBoard chessBoard) {
		super(chessBoard);
		// TODO Auto-generated constructor stub
		init();
	}
	
	private void init(){
		userType = UserType.COMPUTER; //用户类型为计算机
	}
	
	public UserType getUserType(){   //获取用户类型
		return userType;
	}
	
	/**
	 * 估值函数，
	 * @param grid 进行估值的棋盘
	 * @param chess 进行估值的棋子方（黑色或白色）
	 * @return score 估值（即当前得分）  (-1:游戏结束，该方的胜利)
	 */
	public int Evaluate(int[][] grid , Chess chess)
	{
		int score = 0;//最终得分
		
		/* 用小键盘中的数字1-9来表示四个方向 */
		
		// 456方向进行得分计算
		int value = Evaluate456(grid, chess);
		System.out.println("456方向估值：" + value);
		if(value == MAX)
			return MAX;
		else
			score += value;
		
		// 258方向进行得分计算
		value = Evaluate258(grid, chess);
		System.out.println("258方向估值：" + value);
		if(value == MAX)
			return MAX;
		else
			score += value;
		
		// 159方向进行得分计算
		value = Evaluate159(grid, chess);
		System.out.println("159方向估值：" + value);
		if(value == MAX)
			return MAX;
		else
			score += value;
		
		// 357方向进行得分计算
		value = Evaluate357(grid, chess);
		System.out.println("357方向估值：" + value);
		if(value == MAX)
			return MAX;
		else
			score += value;
		
		return score;
	}
	
	//估值函数456方向
	private int Evaluate456(int[][] grid , Chess chess)
	{
		int score = 0;//默认为0   最终得分
		int count;//当前连子数  
		int countJump;//跳跃的棋子数，为了之后的扫描不回退所进行存储
		int countDead; //死棋数    0-左右无棋子    1-一边堵住     2-两边被堵
		int countBorder; //边界之间的距离      1、棋盘边缘     2、敌方棋子
		Boolean isJump; //是否已经跳空格进行判断。           例：10101  这种情况需要当前两个死二，跳空格判断只能进行一次
		
		for(int i=0; i<chessBoard.getRowsCount(); i++)   //遍历行
		{
			count = 0;//连子数
			countJump = 0;//跳棋数
			countDead = 0; //死棋数
			isJump = Boolean.FALSE; //是否跳棋
			countBorder = 0;
			
			/*
			 * 格子无棋子：1、如果已有连棋的记录，则作为结束标志或跳棋标志
			 *           2、无连棋记录，忽视   
			 * 格子为当前棋子：作为连棋进行记录
			 * 格子为反方棋子：作为结束标志，并进行追加死棋标志
			 */
			
			for(int j=0; j<chessBoard.getColumnCount(); j++)  //遍历列
			{
				//如果是首列，  则肯定有一边是堵住的。
				if(j == 0)
				{
					countDead = 1;
					countBorder = 0; //棋盘边缘为起点
				}
				
				//当前棋子为空
				if(grid[j][i] == Chess.EMPTY.getValue())
				{
					countBorder++; //不是棋盘边缘  或 反方棋子
					
					//如果当前连子数为空，即该空格不属于跳格或结束标识，可忽略
					if(count == 0)
					{
						if(isJump)
						{
							countJump = 0;
							isJump = Boolean.FALSE;
						}
						countDead = 0;
					}
					else  //如果还没跳棋，可进行跳棋判断； 否则作为结束标记，
					{
						//进行过跳棋
						if(isJump)
						{
							int value = getScore(count, countDead, isJump, countJump);
							if(value == -1) //游戏结束
								return -1; 
							score += value;
							
							count = 0;//连子数
							//countJump = 0;//跳棋数
							countDead = 0; //死棋数
							isJump = Boolean.TRUE; //是否跳棋
						}
						else
						{
							//进行跳棋
							isJump = Boolean.TRUE;
						}
					}
						
				}
				else if(grid[j][i] == chess.getValue())  //我方棋子
				{
					if(count == 0 && isJump)
					{
						count = countJump;
						countJump = 0;
					}
					countBorder++; //不是棋盘边缘  或 反方棋子
					count++; //连棋数增加
					
					//如果有跳棋，则进行跳棋计数
					if(isJump)
						countJump++;
				}
				else{  //反方棋子
					if(countBorder < 5)//内部小于5 ，即使与边缘之间有空格，也相当于两边被堵，
					{
						countDead = 2; //
					}
					countBorder = 0;//反方棋子相当于边缘，重新计算距离
					if(count > 0)
					{
						//该格子左边若为我方棋子，则该连棋必为死棋
						//无跳棋，countJump = 0 
						//有跳棋，且countJump = 0，则代表着该格子左边为空格。
						if(!isJump || countJump != 0)
							countDead ++;
						
						int value = getScore(count, countDead, isJump, countJump);
						if(value == -1) //游戏结束
							return -1; 
						score += value;
						
						
						
					}
					
					count = 0;//连子数     连棋碰到敌方棋子必然中断
					countJump = 0;//跳棋数
					countDead = 1; ////该棋子为敌方棋子，所以下一个棋子默认一边被堵
					isJump = Boolean.FALSE; //是否跳棋
				}
				
				//最后一列
				if(j == chessBoard.getColumnCount() - 1)
				{
					if(countBorder < 5)//内部小于5 ，即使与边缘之间有空格，也相当于两边被堵，
					{
						countDead = 2; //
					}
					
					if(count > 0)
					{
						//该格子左边若为我方棋子，则该连棋必为死棋
						//无跳棋，countJump = 0 
						//有跳棋，且countJump = 0，则代表着该格子左边为空格。
						if(!isJump || countJump != 0)
							countDead ++;
						
						int value = getScore(count, countDead, isJump, countJump);
						if(value == -1) //游戏结束
							return -1; 
						score += value;
							
					}
				}
			}
		}
		
		//该方向分数
		return score;
	}
	
	//估值函数258方向
	private int Evaluate258(int[][] grid , Chess chess)
	{
		int score = 0;//默认为0   最终得分
		int count;//当前连子数  
		int countJump;//跳跃的棋子数，为了之后的扫描不回退所进行存储
		int countDead; //死棋数
		int countBorder;//边界之间的距离      1、棋盘边缘     2、敌方棋子
		Boolean isJump; //是否已经跳空格进行判断。           例：10101  这种情况需要当前两个死二，跳空格判断只能进行一次
		
		for(int i=0; i<chessBoard.getColumnCount(); i++)   //遍历列
		{
			count = 0;//连子数
			countJump = 0;//跳棋数
			countDead = 0; //死棋数
			countBorder = 0;
			isJump = Boolean.FALSE; //是否跳棋
			
			/*
			 * 格子无棋子：1、如果已有连棋的记录，则作为结束标志或跳棋标志
			 *           2、无连棋记录，忽视   
			 * 格子为当前棋子：作为连棋进行记录
			 * 格子为反方棋子：作为结束标志，并进行追加死棋标志
			 */
			
			for(int j=0; j<chessBoard.getRowsCount(); j++)  //遍历行
			{
				//如果是首行，  则肯定有一边是堵住的。
				if(j == 0)
				{
					countDead = 1;
					countBorder = 0; //棋盘边缘为起点
				}
				
				//当前棋子为空
				if(grid[i][j] == Chess.EMPTY.getValue())
				{
					countBorder++; //不是棋盘边缘  或 反方棋子
					
					//如果当前连子数为空，即该空格不属于跳格或结束标识，可忽略
					if(count == 0)
					{
						if(isJump)
						{
							countJump = 0;
							isJump = Boolean.FALSE;
						}
						
						countDead = 0;
					}
					else  //如果还没跳棋，可进行跳棋判断； 否则作为结束标记，
					{
						//进行过跳棋
						if(isJump)
						{
							
							int value = getScore(count, countDead, isJump, countJump);
							if(value == -1) //游戏结束
								return -1; 
							score += value;
							
							count = 0;//连子数
							//countJump = 0;//跳棋数
							countDead = 0; //是否死棋
							isJump = Boolean.TRUE; //是否跳棋
						}
						else
						{
							//进行跳棋
							isJump = Boolean.TRUE;
						}
					}
						
				}
				else if(grid[i][j] == chess.getValue())  //我方棋子
				{
					if(count == 0 && isJump)
					{
						count = countJump;
						countJump = 0;
					}
					countBorder++; //不是棋盘边缘  或 反方棋子
					count++; //连棋数增加
					
					//如果有跳棋，则进行跳棋计数
					if(isJump)
						countJump++;
				}
				else{  //反方棋子
					if(countBorder < 5)//内部小于5 ，即使与边缘之间有空格，也相当于两边被堵，
					{
						countDead = 2; //
					}
					countBorder = 0; //重新计算边界间的距离
					
					if(count > 0)
					{
						//该格子左边若为我方棋子，则该连棋必为死棋
						//无跳棋，countJump = 0 
						//有跳棋，且countJump = 0，则代表着该格子左边为空格。
						if(!isJump || countJump != 0)
							countDead++;
						
						int value = getScore(count, countDead, isJump, countJump);
						if(value == -1) //游戏结束
							return -1; 
						score += value;
						
						
						
					}
					
					count = 0;//连子数     连棋碰到敌方棋子必然中断
					countJump = 0;//跳棋数
					countDead = 1; //该棋子为敌方棋子，所以下一个棋子默认一边被堵
					isJump = Boolean.FALSE; //是否跳棋
				}
				
				//最后一列
				if(j == chessBoard.getRowsCount() - 1)
				{
					if(countBorder < 5)//内部小于5 ，即使与边缘之间有空格，也相当于两边被堵，
					{
						countDead = 2; //
					}
					if(count > 0)
					{
						//该格子左边若为我方棋子，则该连棋必为死棋
						//无跳棋，countJump = 0 
						//有跳棋，且countJump = 0，则代表着该格子左边为空格。
						if(!isJump || countJump != 0)
							countDead ++;
						
						int value = getScore(count, countDead, isJump, countJump);
						if(value == -1) //游戏结束
							return -1; 
						score += value;
							
					}
				}
			}
		}
		
		//该方向分数
		return score;
	}
	
	//估值函数159方向
	private int Evaluate159(int[][] grid , Chess chess)
	{
		int score = 0;//默认为0   最终得分
		int count;//当前连子数  
		int countJump;//跳跃的棋子数，为了之后的扫描不回退所进行存储
		int countDead; //死棋数
		int countBorder;//边界之间的距离      1、棋盘边缘     2、敌方棋子
		Boolean isJump; //是否已经跳空格进行判断。           例：10101  这种情况需要当前两个死二，跳空格判断只能进行一次
		
		//下半部分三角形，左下到右上
		for(int k=0; k<chessBoard.getColumnCount()-4; k++)   //行
		{
			count = 0;//连子数
			countJump = 0;//跳棋数
			countDead = 0; //死棋数
			countBorder = 0;
			isJump = Boolean.FALSE; //是否跳棋
			
			/*
			 * 格子无棋子：1、如果已有连棋的记录，则作为结束标志或跳棋标志
			 *           2、无连棋记录，忽视   
			 * 格子为当前棋子：作为连棋进行记录
			 * 格子为反方棋子：作为结束标志，并进行追加死棋标志
			 */
			
			for(int i=k,j=chessBoard.getRowsCount()-1; i<chessBoard.getColumnCount(); i++,j--)  //遍历斜线
			{
				//如果是首行，  则肯定有一边是堵住的。
				if(j == chessBoard.getRowsCount() - 1)
				{
					countDead = 1;
					countBorder = 0; //棋盘边缘为起点
				}
				
				//当前棋子为空
				if(grid[i][j] == Chess.EMPTY.getValue())
				{
					
					countBorder++; //不是棋盘边缘  或 反方棋子
					
					//如果当前连子数为空，即该空格不属于跳格或结束标识，可忽略
					if(count == 0)
					{
						if(isJump)
						{
							countJump = 0;
							isJump = Boolean.FALSE;
						}
						countDead = 0;
					}
					else  //如果还没跳棋，可进行跳棋判断； 否则作为结束标记，
					{
						//进行过跳棋
						if(isJump)
						{
							int value = getScore(count, countDead, isJump, countJump);
							if(value == -1) //游戏结束
								return -1; 
							score += value;
							
							count = 0;//连子数
							//countJump = 0;//跳棋数
							countDead = 0; //是否死棋
							isJump = Boolean.TRUE; //是否跳棋
						}
						else
						{
							//进行跳棋
							isJump = Boolean.TRUE;
						}
					}
						
				}
				else if(grid[i][j] == chess.getValue())  //我方棋子
				{
					if(count == 0 && isJump)
					{
						count = countJump;
						countJump = 0;
					}
					countBorder++; //不是棋盘边缘  或 反方棋子
					count++; //连棋数增加
					
					//如果有跳棋，则进行跳棋计数
					if(isJump)
						countJump++;
				}
				else{  //反方棋子
					if(countBorder < 5)//内部小于5 ，即使与边缘之间有空格，也相当于两边被堵，
					{
						countDead = 2; //
					}
					countBorder = 0; //重新计算边界间的距离
					
					if(count > 0)
					{
						//该格子左边若为我方棋子，则该连棋必为死棋
						//无跳棋，countJump = 0 
						//有跳棋，且countJump = 0，则代表着该格子左边为空格。
						if(!isJump || countJump != 0)
							countDead++;
						
						int value = getScore(count, countDead, isJump, countJump);
						if(value == -1) //游戏结束
							return -1; 
						score += value;
						
						
						
					}
					
					count = 0;//连子数     连棋碰到敌方棋子必然中断
					countJump = 0;//跳棋数
					countDead = 1; //该棋子为敌方棋子，所以下一个棋子默认一边被堵
					isJump = Boolean.FALSE; //是否跳棋
				}
				
				//最后一列
				if(i == chessBoard.getColumnCount() - 1)
				{
					if(countBorder < 5)//内部小于5 ，即使与边缘之间有空格，也相当于两边被堵，
					{
						countDead = 2; //
					}
					if(count > 0)
					{
						//该格子左边若为我方棋子，则该连棋必为死棋
						//无跳棋，countJump = 0 
						//有跳棋，且countJump = 0，则代表着该格子左边为空格。
						if(!isJump || countJump != 0)
							countDead ++;
						
						int value = getScore(count, countDead, isJump, countJump);
						if(value == -1) //游戏结束
							return -1; 
						score += value;
							
					}
				}
			}
		}
		
		//上半部分三角形 ，  右上到左下
		for(int k=chessBoard.getColumnCount()-1-1; k>=4; k--)   //行
		{
			count = 0;//连子数
			countJump = 0;//跳棋数
			countDead = 0; //死棋数
			countBorder = 0;
			isJump = Boolean.FALSE; //是否跳棋
			
			/*
			 * 格子无棋子：1、如果已有连棋的记录，则作为结束标志或跳棋标志
			 *           2、无连棋记录，忽视   
			 * 格子为当前棋子：作为连棋进行记录
			 * 格子为反方棋子：作为结束标志，并进行追加死棋标志
			 */
			
			for(int i=k,j=0; i>=0; i--,j++)  //遍历斜线
			{
				//如果是首行，  则肯定有一边是堵住的。
				if(j == 0)
				{
					countDead = 1;
					countBorder = 0; //棋盘边缘为起点
				}
				
				//当前棋子为空
				if(grid[i][j] == Chess.EMPTY.getValue())
				{
					countBorder++; //不是棋盘边缘  或 反方棋子
					
					//如果当前连子数为空，即该空格不属于跳格或结束标识，可忽略
					if(count == 0)
					{
						if(isJump)
						{
							countJump = 0;
							isJump = Boolean.FALSE;
						}
						countDead = 0;
					}
					else  //如果还没跳棋，可进行跳棋判断； 否则作为结束标记，
					{
						//进行过跳棋
						if(isJump)
						{
							int value = getScore(count, countDead, isJump, countJump);
							if(value == -1) //游戏结束
								return -1; 
							score += value;
							
							count = 0;//连子数
							//countJump = 0;//跳棋数
							countDead = 0; //是否死棋
							isJump = Boolean.TRUE; //是否跳棋
						}
						else
						{
							//进行跳棋
							isJump = Boolean.TRUE;
						}
					}
						
				}
				else if(grid[i][j] == chess.getValue())  //我方棋子
				{
					if(count == 0 && isJump)
					{
						count = countJump;
						countJump = 0;
					}
					countBorder++; //不是棋盘边缘  或 反方棋子
					count++; //连棋数增加
					
					//如果有跳棋，则进行跳棋计数
					if(isJump)
						countJump++;
				}
				else{  //反方棋子
					if(countBorder < 5)//内部小于5 ，即使与边缘之间有空格，也相当于两边被堵，
					{
						countDead = 2; //
					}
					countBorder = 0; //重新计算边界间的距离
					
					if(count > 0)
					{
						//该格子左边若为我方棋子，则该连棋必为死棋
						//无跳棋，countJump = 0 
						//有跳棋，且countJump = 0，则代表着该格子左边为空格。
						if(!isJump || countJump != 0)
							countDead++;
						
						int value = getScore(count, countDead, isJump, countJump);
						if(value == -1) //游戏结束
							return -1; 
						score += value;
						
						
						
					}
					
					count = 0;//连子数     连棋碰到敌方棋子必然中断
					countJump = 0;//跳棋数
					countDead = 1; //该棋子为敌方棋子，所以下一个棋子默认一边被堵
					isJump = Boolean.FALSE; //是否跳棋
				}
				
				//最后一列
				if(i == 0)
				{
					if(countBorder < 5)//内部小于5 ，即使与边缘之间有空格，也相当于两边被堵，
					{
						countDead = 2; //
					}
					if(count > 0)
					{
						//该格子左边若为我方棋子，则该连棋必为死棋
						//无跳棋，countJump = 0 
						//有跳棋，且countJump = 0，则代表着该格子左边为空格。
						if(!isJump || countJump != 0)
							countDead ++;
						
						int value = getScore(count, countDead, isJump, countJump);
						if(value == -1) //游戏结束
							return -1; 
						score += value;
							
					}
				}
			}
		}
		
		//该方向分数
		return score;
	}
	//估值函数357方向
	private int Evaluate357(int[][] grid , Chess chess)
	{
		int score = 0;//默认为0   最终得分
		int count;//当前连子数  
		int countJump;//跳跃的棋子数，为了之后的扫描不回退所进行存储
		int countDead; //死棋数
		int countBorder;//边界之间的距离      1、棋盘边缘     2、敌方棋子
		Boolean isJump; //是否已经跳空格进行判断。           例：10101  这种情况需要当前两个死二，跳空格判断只能进行一次
		
		//上半部分三角形，左上到右下
		for(int k=0; k<chessBoard.getColumnCount()-4; k++)   //行
		{
			count = 0;//连子数
			countJump = 0;//跳棋数
			countDead = 0; //死棋数
			countBorder = 0;
			isJump = Boolean.FALSE; //是否跳棋
			
			/*
			 * 格子无棋子：1、如果已有连棋的记录，则作为结束标志或跳棋标志
			 *           2、无连棋记录，忽视   
			 * 格子为当前棋子：作为连棋进行记录
			 * 格子为反方棋子：作为结束标志，并进行追加死棋标志
			 */
			
			for(int i=k,j=0; i<chessBoard.getColumnCount(); i++,j++)  //遍历斜线
			{
				//如果是首行，  则肯定有一边是堵住的。
				if(j == 0)
				{
					countDead = 1;
					countBorder = 0; //棋盘边缘为起点
				}
				
				//当前棋子为空
				if(grid[i][j] == Chess.EMPTY.getValue())
				{
					countBorder++; //不是棋盘边缘  或 反方棋子
					
					//如果当前连子数为空，即该空格不属于跳格或结束标识，可忽略
					if(count == 0)
					{
						if(isJump)
						{
							countJump = 0;
							isJump = Boolean.FALSE;
						}
						countDead = 0;
					}
					else  //如果还没跳棋，可进行跳棋判断； 否则作为结束标记，
					{
						//进行过跳棋
						if(isJump)
						{
							int value = getScore(count, countDead, isJump, countJump);
							if(value == -1) //游戏结束
								return -1; 
							score += value;
							
							count = 0;//连子数
							//countJump = 0;//跳棋数
							countDead = 0; //是否死棋
							isJump = Boolean.TRUE; //是否跳棋
						}
						else
						{
							//进行跳棋
							isJump = Boolean.TRUE;
						}
					}
						
				}
				else if(grid[i][j] == chess.getValue())  //我方棋子
				{
					if(count == 0 && isJump)
					{
						count = countJump;
						countJump = 0;
					}
					countBorder++; //不是棋盘边缘  或 反方棋子
					count++; //连棋数增加
					
					//如果有跳棋，则进行跳棋计数
					if(isJump)
						countJump++;
				}
				else{  //反方棋子
					if(countBorder < 5)//内部小于5 ，即使与边缘之间有空格，也相当于两边被堵，
					{
						countDead = 2; //
					}
					countBorder = 0; //重新计算边界间的距离
					
					if(count > 0)
					{
						//该格子左边若为我方棋子，则该连棋必为死棋
						//无跳棋，countJump = 0 
						//有跳棋，且countJump = 0，则代表着该格子左边为空格。
						if(!isJump || countJump != 0)
							countDead++;
						
						int value = getScore(count, countDead, isJump, countJump);
						if(value == -1) //游戏结束
							return -1; 
						score += value;
						
						
						
					}
					
					count = 0;//连子数     连棋碰到敌方棋子必然中断
					countJump = 0;//跳棋数
					countDead = 1; //该棋子为敌方棋子，所以下一个棋子默认一边被堵
					isJump = Boolean.FALSE; //是否跳棋
				}
				
				//最后一列
				if(i == chessBoard.getColumnCount() - 1)
				{
					if(countBorder < 5)//内部小于5 ，即使与边缘之间有空格，也相当于两边被堵，
					{
						countDead = 2; //
					}
					if(count > 0)
					{
						//该格子左边若为我方棋子，则该连棋必为死棋
						//无跳棋，countJump = 0 
						//有跳棋，且countJump = 0，则代表着该格子左边为空格。
						if(!isJump || countJump != 0)
							countDead ++;
						
						int value = getScore(count, countDead, isJump, countJump);
						if(value == -1) //游戏结束
							return -1; 
						score += value;
							
					}
				}
			}
		}
		
		//上半部分三角形 ，  右下到左上
		for(int k=chessBoard.getColumnCount()-1-1; k>=4; k--)   //行
		{
			count = 0;//连子数
			countJump = 0;//跳棋数
			countDead = 0; //死棋数
			countBorder = 0;
			isJump = Boolean.FALSE; //是否跳棋
			
			/*
			 * 格子无棋子：1、如果已有连棋的记录，则作为结束标志或跳棋标志
			 *           2、无连棋记录，忽视   
			 * 格子为当前棋子：作为连棋进行记录
			 * 格子为反方棋子：作为结束标志，并进行追加死棋标志
			 */
			
			for(int i=k,j=chessBoard.getRowsCount()-1; i>=0; i--,j--)  //遍历斜线
			{
				//如果是首行，  则肯定有一边是堵住的。
				if(j == chessBoard.getRowsCount()-1)
				{
					countDead = 1;
					countBorder = 0; //棋盘边缘为起点
				}
				
				//当前棋子为空
				if(grid[i][j] == Chess.EMPTY.getValue())
				{
					countBorder++; //不是棋盘边缘  或 反方棋子
					
					//如果当前连子数为空，即该空格不属于跳格或结束标识，可忽略
					if(count == 0)
					{
						if(isJump)
						{
							countJump = 0;
							isJump = Boolean.FALSE;
						}
						countDead = 0;
					}
					else  //如果还没跳棋，可进行跳棋判断； 否则作为结束标记，
					{
						//进行过跳棋
						if(isJump)
						{
							int value = getScore(count, countDead, isJump, countJump);
							if(value == -1) //游戏结束
								return -1; 
							score += value;
							
							count = 0;//连子数
							//countJump = 0;//跳棋数
							countDead = 0; //是否死棋
							isJump = Boolean.TRUE; //是否跳棋
						}
						else
						{
							//进行跳棋
							isJump = Boolean.TRUE;
						}
					}
						
				}
				else if(grid[i][j] == chess.getValue())  //我方棋子
				{
					if(count == 0 && isJump)
					{
						count = countJump;
						countJump = 0;
					}
					countBorder++; //不是棋盘边缘  或 反方棋子
					count++; //连棋数增加
					
					//如果有跳棋，则进行跳棋计数
					if(isJump)
						countJump++;
				}
				else{  //反方棋子
					if(countBorder < 5)//内部小于5 ，即使与边缘之间有空格，也相当于两边被堵，
					{
						countDead = 2; //
					}
					countBorder = 0; //重新计算边界间的距离
					
					if(count > 0)
					{
						//该格子左边若为我方棋子，则该连棋必为死棋
						//无跳棋，countJump = 0 
						//有跳棋，且countJump = 0，则代表着该格子左边为空格。
						if(!isJump || countJump != 0)
							countDead++;
						
						int value = getScore(count, countDead, isJump, countJump);
						if(value == -1) //游戏结束
							return -1; 
						score += value;
						
						
						
					}
					
					count = 0;//连子数     连棋碰到敌方棋子必然中断
					countJump = 0;//跳棋数
					countDead = 1; //该棋子为敌方棋子，所以下一个棋子默认一边被堵
					isJump = Boolean.FALSE; //是否跳棋
				}
				
				//最后一列
				if(i == 0)
				{
					if(countBorder < 5)//内部小于5 ，即使与边缘之间有空格，也相当于两边被堵，
					{
						countDead = 2; //
					}
					if(count > 0)
					{
						//该格子左边若为我方棋子，则该连棋必为死棋
						//无跳棋，countJump = 0 
						//有跳棋，且countJump = 0，则代表着该格子左边为空格。
						if(!isJump || countJump != 0)
							countDead ++;
						
						int value = getScore(count, countDead, isJump , countJump);
						if(value == -1) //游戏结束
							return -1; 
						score += value;
							
					}
				}
			}
		}
		
		//该方向分数
		return score;
	}
	
	
	/**
	 * 计分函数，对棋盘的分数判断
	 * (countDead 死棋数        之后应该优化为   isLeftDead 和 isRightDead  是否左死棋 和 是否右死棋）
	 * @param count 连棋数
	 * @param countDead 死棋：即有被堵住的棋子，   当countDead为2时，代表两边都被堵住
	 * @return 分数
	 * 
	 */
	private int getScore(int count , int countDead , Boolean isJump , int countJump)
	{
		//连棋为0   0分
		if(count == 0)
			return 0;
		
		//两边都堵住的情况
		//1、连棋小于5，   并且没有跳棋                   2、连棋小于4
		if(count < 5 && !isJump && countDead >= 2 || count < 4 && countDead >= 2)
			return 0;
		
		//如果有跳棋
		if(countJump >= 5 || count - countJump >= 5)
			count = 5;
		
		
		
		/**     计分板（出现一个根据下表加分，五子连棋除外）
		 * 死一                    1
		 * 死二/活一          10
		 * 死三/活二          100
		 * 死四/活三          1000
		 * 活四                    10000
		 * 五子连棋             1000000 
		 */
		final int ZERO = 0;
		final int DEAD_ONE = 1;
		final int DEAD_TWO, LIVE_ONE = 10;
		final int DEAD_THREE, LIVE_TWO = 100;
		final int DEAD_FOUR, LIVE_THREE = 1000;
		final int LIVE_FOUR = 10000;
		final int FIVE = 100000;
		
		
		//大于五，并且是死棋，那么就是死四
		if(count >= 5 && countDead > 0 || count >=5 && isJump)
			count = 4;
		
		if(count >= 5)  
			return FIVE;  //五子连棋
		else
		{
			int score = 0;//获得的分数
			
			//假设都是活棋
			switch(count)
			{
			case 1:score = LIVE_ONE;break;
			case 2:score = LIVE_TWO;break;
			case 3:score = LIVE_THREE;break;
			case 4:score = LIVE_FOUR;break;
			default:;break; //
			}
			
			//判断是否为死棋,   因为死棋跟活棋刚好是10倍的分数差，所以直接除10即可
			if(countDead > 0 || countJump > 0)
				score = score/10;
			
			
			//返回分数
			return score;
			
		}
	}
	

	//point:坐标
	//metric：估值
	class PointMetric{
		Point point;
		int metric;
		
		public PointMetric(Point p , int m) {
			// TODO Auto-generated constructor stub
			point = p;
			metric = m;
		}
		
		public int getX()
		{
			return (int) point.getX();
		}
		public int getY()
		{
			return (int) point.getY();
		}
		public int getMetric()
		{
			return metric;
		}
	};
	
	

	//极大 - 极小 搜索算法        
	/*
	 * alpha表示了MAX的最坏情况，beta表示了MIN的最坏情况，
	 * 因此他们的初始值为负无穷和正无穷。在递归的过程中，
	 * 在轮到MAX的回合，如果极小极大的值比alpha大，则更新alpha；
	 * 在MIN的回合中，如果极小极大值比beta小，则更新beta。
	 * 
	 */
	//max
	/**
	 * [max max下棋]
	 * @param  depth        [考虑深度]
	 * @return {[type]}              [description]
	 */
	public PointMetric max( int depth, int beta)
	{
		//记录优势值，应该下棋的位置
	    int row = -1;
	    int column = -1;
        int alpha = -9999999;
        
	    //什么都不下，直接返回当前棋盘评估值
	    if (depth == 0) {
	        alpha = Evaluate(chessBoard.getGrid(), Chess.WHITE) - Evaluate(chessBoard.getGrid(), Chess.BLACK);
	        
	        return new PointMetric(new Point(row, column), alpha);
	    }
	    else{
	    	//获取每一步可以走的方案
	        ArrayList<Point> steps = chessBoard.getAvailable();
	        // console.log('搜索MAX' + steps.length + '个棋局');
	        if (!steps.isEmpty()) {
	            //对于每一种走法
	            for (int i = 0, l = steps.size(); i < l; i++) {
	                Point step = steps.get(i);
	                //下棋
	                
	                //如果已经赢了，则直接下棋，不再考虑对方下棋
	                if(chessBoard.isWinner((int)(step.getX()), (int)(step.getY())))
	                {
	                	alpha = MAX;
	                    row = (int)(step.getX());
	                    column = (int)(step.getY());
	                    
	                   
	                    break;
	                }
	                else{
	                	chessBoard.setChess((int)(step.getX()), (int)(step.getY()));
	                	//考虑对方depth-1步下棋之后的优势值，如果对方没棋可下了，则返回当前棋盘估值
	                    PointMetric res = min(depth - 1, alpha);
	                    
	                    //退回上一步下棋
	                    chessBoard.rollback();
	                    if (res.getMetric() > alpha) {
	                        //选择最大优势的走法
	                        alpha = res.getMetric();
	                        row = (int) step.getX();
	                        column = (int) step.getY();
	                    }
	                    
	                    //如果人可以获得更好的走法，则AI必然不会选择这一步走法，所以不用再考虑人的其他走法
	                    if (alpha >= beta) {
	                        
	                        break;
	                    }
	                    
	                    
	                }
	            }
	        }
	        
	    }
	    
	    return new PointMetric(new Point(row, column), alpha);
	}
	
	//min
	/**
	 * [max max下棋]
	 * @param  depth        [考虑深度]
	 * @return {[type]}              [description]
	 */
	public PointMetric min( int depth, int alpha)
	{
		//记录优势值，应该下棋的位置
	    int row = -1;
	    int column = -1;
        int beta = 9999999;
        
	    //什么都不下，直接返回当前棋盘评估值
	    if (depth == 0) {
	        beta = Evaluate(chessBoard.getGrid(), Chess.WHITE) - Evaluate(chessBoard.getGrid(), Chess.BLACK);
	        
	        return new PointMetric(new Point(row, column), beta);
	    }
	    else{
	    	//获取每一步可以走的方案
	        ArrayList<Point> steps = chessBoard.getAvailable();
	        // console.log('搜索MIN' + steps.length + '个棋局');
	        if (!steps.isEmpty()) {
	            //对于每一种走法
	            for (int i = 0, l = steps.size(); i < l; i++) {
	                Point step = steps.get(i);
	                
	                //如果已经赢了，则直接下棋，不再考虑对方下棋
	                if(chessBoard.isWinner((int)(step.getX()), (int)(step.getY())))
	                {
	                	beta = MIN;
	                    row = (int)(step.getX());
	                    column = (int)(step.getY());
	                    //退回上一步下棋
	                    
	                    break;
	                }
	                else{
	                	//下棋
	                    chessBoard.setChess((int)(step.getX()), (int)(step.getY()));
	                	//考虑对方depth-1步下棋之后的优势值，如果对方没棋可下了，则返回当前棋盘估值
	                    PointMetric res = max(depth - 1, beta);
	                    
	                    //退回上一步下棋
	                    chessBoard.rollback();
	                    if (res.getMetric() < beta ) {
	                        //选择最大优势的走法
	                        beta = res.getMetric();
	                        row = (int) step.getX();
	                        column = (int) step.getY();
	                    }
	                    
	                    //如果人可以获得更好的走法，则AI必然不会选择这一步走法，所以不用再考虑人的其他走法
	                    
	                    if (alpha >= beta) {
	                        // console.log('MAX节点' + l + '个棋局，剪掉了' + (l - 1 - i) + '个MIN棋局');
	                        break;
	                    }
	                    
	                }
	            }
	        }
	        
	    }
	    
	    return new PointMetric(new Point(row, column), beta);
	
	}
	
	
	public void play()
	{
		PointMetric p = max(2, MAX);
		chessBoard.setChess(p.getX(), p.getY());
		chessBoard.repaint();
	}
}
