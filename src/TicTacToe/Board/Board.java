package TicTacToe.Board;

import java.util.List;

public class Board
{
	public enum Mark{
		EMPTY (" "),
		X ("X"),
		O ("O");
		private String display;
		Mark(String display)
		{
			this.display = display;
		}
		public String getDisplay(){return this.display;}
	}
	
	private static final int board_size = 3;
	private Mark[][] places = new Mark[board_size][board_size];
	
	public void set(int c, int r, Mark m){this.places[c % board_size][r % board_size] = m;}
	public Mark get(int c, int r){return this.places[c % board_size][r % board_size];}
	
	/**Creates a new board (not a super board)
	 */
	public Board()
	{
		for(int r = 0; r < board_size; r++) for(int c = 0; c < board_size; c++) places[c][r] = Mark.EMPTY;
	}
	
	public String toString()
	{
		return "";
	}
	public List<String> toStringList()
	{
		
		return null;
	}
}
