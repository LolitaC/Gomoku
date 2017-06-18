package chessboard;

public enum Chess {
	EMPTY(0),
	BLACK(1),
	WHITE(2);
	
	int value;
	private Chess(int value) {
		// TODO Auto-generated constructor stub
		this.value = value;
	}
	
	public int getValue()
	{
		return value;
	}
}
