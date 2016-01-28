package TicTacToe.Board;

import java.util.ArrayList;
import java.util.List;

public class Field
{
	private static final int field_size = 3;
	
	private Board[][] boards = new Board[field_size][field_size];
	
	public Board getBoard(int c, int r){return this.boards[r % field_size][c % field_size];}
	
	/**Generates a new field
	 * 
	 */
	public Field()
	{
		for(int c = 0; c < field_size; c++) for(int r = 0; r < field_size; r++) this.boards[c][r] = new Board();
	}
	public String toString()
	{
		List<String> lines = new ArrayList<>();
		
		String ret = "";
		for(String l : lines) ret += l + "\n";
		return ret;
	}
	public String toDisplay()
	{
    List<String> lines = new ArrayList<>();
		
		String ret = "";
		for(String l : lines) ret += l + "\n";
		return ret;
	}
}
