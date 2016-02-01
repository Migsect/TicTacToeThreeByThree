package TicTacToe.Board;

import java.util.ArrayList;
import java.util.List;

import TicTacToe.TextPicture.TextPicture;

public class Board
{
	public static enum Mark{
		EMPTY (" ", 0),
		X ("X", 1),
		O ("O", -1);
		private String display;
		private int value;
		Mark(String display, int value)
		{
			this.display = display;
			this.value = value;
		}
		public String getDisplay(){return this.display;}
		public int getValue(){return this.value;}
	}
	
	private static final int BOARD_SIZE = 3;
	private static final String H_SEPERATOR = "-";
	private static final String V_SEPERATOR = "|";
	private static final int EDGE_SPACING = 1;
	private Mark[][] places = new Mark[BOARD_SIZE][BOARD_SIZE];
	
	private static List<String> getCollapsed(Mark m)
	{
	  List<String> ret = new ArrayList<>();
	  
	  for(int r = 0; r < BOARD_SIZE; r++)
	  {
	  	String row = "";
	  	for(int c = 0; c < BOARD_SIZE; c++)
	  	{
	  		row += m.getDisplay();
	  		if(c < BOARD_SIZE - 1) row += m.getDisplay();
	  	}
	  	ret.add(row);
	  	if(r < BOARD_SIZE - 1)
	  	{
	  		String seperator = "";
	  		for(int i = 0; i < row.length(); i++) seperator += m.display;
	  		ret.add(seperator);
	  	}
	  }
	  
	  return ret;
	}
	
	public void set(int c, int r, Mark m){this.places[c % BOARD_SIZE][r % BOARD_SIZE] = m;}
	public Mark get(int c, int r){return this.places[c % BOARD_SIZE][r % BOARD_SIZE];}
	
	public void set(int x, Mark m){this.set(x % BOARD_SIZE, x / BOARD_SIZE, m);}
	public Mark get(int x){return this.get(x % BOARD_SIZE, x / BOARD_SIZE);}
	
	public boolean isComplete()
	{
	  return !this.getWinner().equals(Mark.EMPTY);
	}
	public Mark getWinner()
	{
		// summing rows
		for(int c = 0; c < BOARD_SIZE; c++)
		{
			int sum = 0;
			for(int r = 0; r < BOARD_SIZE; r++)
			{
				sum += this.get(c, r).getValue();
			}
			if(sum == BOARD_SIZE) return Mark.X;
			if(sum == -BOARD_SIZE) return Mark.O;
		}
		// getting columns
		for(int r = 0; r < BOARD_SIZE; r++)
		{
			int sum = 0;
			for(int c = 0; c < BOARD_SIZE; c++)
			{
				sum += this.get(c, r).getValue();
			}
			if(sum == BOARD_SIZE) return Mark.X;
			if(sum == -BOARD_SIZE) return Mark.O;
		}
		// getting diagonals
		int sum_right = 0;
		int sum_left = 0;
		for(int i = 0; i < BOARD_SIZE; i++)
		{
			sum_right += this.get(i, i).getValue();
			sum_left += this.get(i, BOARD_SIZE - i - 1).getValue();
		}
		if(sum_right == BOARD_SIZE || sum_left == BOARD_SIZE) return Mark.X;
		if(sum_right == -BOARD_SIZE || sum_left == -BOARD_SIZE) return Mark.O;
		
		return Mark.EMPTY;
	}
	
	public String getHorizontalSeperator()
	{
		String row = "";
		for(int i = 0; i < BOARD_SIZE; i++)
		{
			row += H_SEPERATOR;
			if(i < BOARD_SIZE-1) row += V_SEPERATOR;
		}
		return row;
	}
	public String getVerticalSeperator(){return V_SEPERATOR;}
	
	/**Creates a new board (not a super board)
	 */
	public Board()
	{
		for(int r = 0; r < BOARD_SIZE; r++) for(int c = 0; c < BOARD_SIZE; c++) places[c][r] = Mark.EMPTY;
	}
	
	public List<String> toStringList()
	{
		int picture_side = 2*BOARD_SIZE + 2*EDGE_SPACING - 1;
	  TextPicture picture = new TextPicture(picture_side, picture_side);
	  // drawing the marks
	  for(int r = 0; r < BOARD_SIZE; r++) for(int c = 0; c < BOARD_SIZE; c++)
	  {
	  	Mark m = this.get(c, r);
	    int p_c = EDGE_SPACING + 2 * c;
	  	int p_r = EDGE_SPACING + 2 * r;
	  	picture.setElement(p_c, p_r, m.getDisplay().charAt(0));
	  }
	  
	  // drawing the lines
	  
		return picture.buildByRows();
	}
	public List<String> toDisplay()
	{
		if(this.isComplete()) return Board.getCollapsed(this.getWinner());
		else return this.toStringList();
	}
}
