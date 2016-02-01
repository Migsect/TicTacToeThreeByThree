package TicTacToe.Board;

import java.util.ArrayList;
import java.util.List;

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
	
	private static final int board_size = 3;
	private static final String horizontal_seperator = "-";
	private static final String vertical_seperator = "|";
	private Mark[][] places = new Mark[board_size][board_size];
	
	private static List<String> getCollapsed(Mark m)
	{
	  List<String> ret = new ArrayList<>();
	  
	  for(int r = 0; r < board_size; r++)
	  {
	  	String row = "";
	  	for(int c = 0; c < board_size; c++)
	  	{
	  		row += m.getDisplay();
	  		if(c < board_size - 1) row += m.getDisplay();
	  	}
	  	ret.add(row);
	  	if(r < board_size - 1)
	  	{
	  		String seperator = "";
	  		for(int i = 0; i < row.length(); i++) seperator += m.display;
	  		ret.add(seperator);
	  	}
	  }
	  
	  return ret;
	}
	
	public void set(int c, int r, Mark m){this.places[c % board_size][r % board_size] = m;}
	public Mark get(int c, int r){return this.places[c % board_size][r % board_size];}
	
	public void set(int x, Mark m){this.set(x % board_size, x / board_size, m);}
	public Mark get(int x){return this.get(x % board_size, x / board_size);}
	
	public boolean isComplete()
	{
	  return !this.getWinner().equals(Mark.EMPTY);
	}
	public Mark getWinner()
	{
		// summing rows
		for(int c = 0; c < board_size; c++)
		{
			int sum = 0;
			for(int r = 0; r < board_size; r++)
			{
				sum += this.get(c, r).getValue();
			}
			if(sum == board_size) return Mark.X;
			if(sum == -board_size) return Mark.O;
		}
		// getting columns
		for(int r = 0; r < board_size; r++)
		{
			int sum = 0;
			for(int c = 0; c < board_size; c++)
			{
				sum += this.get(c, r).getValue();
			}
			if(sum == board_size) return Mark.X;
			if(sum == -board_size) return Mark.O;
		}
		// getting diagonals
		int sum_right = 0;
		int sum_left = 0;
		for(int i = 0; i < board_size; i++)
		{
			sum_right += this.get(i, i).getValue();
			sum_left += this.get(i, board_size - i - 1).getValue();
		}
		if(sum_right == board_size || sum_left == board_size) return Mark.X;
		if(sum_right == -board_size || sum_left == -board_size) return Mark.O;
		
		return Mark.EMPTY;
	}
	
	public String getHorizontalSeperator()
	{
		String row = "";
		for(int i = 0; i < board_size; i++)
		{
			row += horizontal_seperator;
			if(i < board_size-1) row += vertical_seperator;
		}
		return row;
	}
	public String getVerticalSeperator(){return vertical_seperator;}
	
	/**Creates a new board (not a super board)
	 */
	public Board()
	{
		for(int r = 0; r < board_size; r++) for(int c = 0; c < board_size; c++) places[c][r] = Mark.EMPTY;
	}
	
	public List<String> toStringList()
	{
	  List<String> ret = new ArrayList<String>();
	  for(int r = 0; r < board_size; r++) 
	  {
	  	String row = "";
	  	for(int c = 0; c < board_size; c++)
	  	{
	  		row += this.get(c,r).getDisplay();
	  		if(c < board_size-1) row += this.getVerticalSeperator();
	  	}
	  	ret.add(row);
	  	if(r < board_size-1) ret.add(getHorizontalSeperator());
	  }	
	  
		return ret;
	}
	public List<String> toDisplay()
	{
		if(this.isComplete()) return Board.getCollapsed(this.getWinner());
		else return this.toStringList();
	}
}
