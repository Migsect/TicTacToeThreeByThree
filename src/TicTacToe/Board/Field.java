package TicTacToe.Board;

import java.util.ArrayList;
import java.util.List;

import TicTacToe.Board.Board.Mark;

public class Field
{
	private static final int field_size = 3;
	private static final String h_seperator = ":";
	private static final String v_seperator = ".";
	private static final String selection_h_seperator = "#";
	private static final String selection_v_seperator = "#";
	
	private Board[][] boards = new Board[field_size][field_size];
	// selected board has an interval of [0, field_size*field_size - 1]
	private int selected_board = field_size * (field_size / 2) + (field_size / 2); // the selected board
	
	/**Checks to see if the board is complete
	 * If it is this will return true
	 * 
	 * @return true or false
	 */
	public boolean isComplete(){return this.getMacroBoard().isComplete();}
	/**Returns a winner if there is a winner
	 * This depends on whether or not the board is complete as determined
	 * by "isComplete()"
	 * 
	 * @return true or false
	 */
	public Mark getWinner(){return this.getMacroBoard().getWinner();}
	/**Gets a board object that represents the current macro-state of the field board
	 * This board object is only generated and not a true reference to the state of the
	 * macro board
	 * 
	 * @return A board object
	 */
	public Board getMacroBoard()
	{
		Board board = new Board();
		for(int r = 0; r < field_size; r++) for(int c = 0; c < field_size; c++)
		{
		  board.set(c, r, this.getBoard(c, r).getWinner());
		}
		return board;
	}
	/**Returns a board based on the column (x) and row (y) specified
	 * 
	 * @param c The column
	 * @param r The row
	 * @return A board object
	 */
	public Board getBoard(int c, int r){return this.boards[c % field_size][r % field_size];}
	/**Returns a board based on the index of the board where columns increment
	 * by 1 and rows increment by field_size (number of elements per row)
	 * 
	 * @param x An index
	 * @return A board object
	 */
	public Board getBoard(int x){return this.getBoard(x % field_size, x / field_size);}
	/**Returns the currently selected board.
	 * A new board can be selected by using the ".select()" method
	 * 
	 * @return A board object
	 */
	public Board getBoard(){return this.getBoard(this.selected_board);}
	
	/**Will set the selected board based on the inputted column and row
	 * 
	 * @param c The column
	 * @param r The row
	 * @return The field object that this method was called on
	 */
	public Field select(int c, int r){this.select(c + field_size * r); return this;}
	/**Sets the selected table to the specified index
	 * 
	 * @param x The index of the table to select
	 * @return The field object that this method was called on
	 */
	public Field select(int x){this.selected_board = x % (field_size*field_size); return this;}
	
	/**Generates a new empty field
	 * 
	 */
	public Field()
	{
		for(int c = 0; c < field_size; c++) for(int r = 0; r < field_size; r++) this.boards[c][r] = new Board();
	}
	
	/**Converts the field into displayable a string list
	 * 
	 * @return A list of strings
	 */
	public List<String> toStringList()
	{
		List<String> ret = new ArrayList<>();
		for(int r = 0; r < field_size; r++)
		{
			List<String> rows = new ArrayList<>();
			for(int c = 0; c < field_size; c++)
			{
				List<String> board_rows = this.getBoard(c, r).toDisplay();
				while(rows.size() < board_rows.size()) rows.add("");
				for(int i = 0; i < board_rows.size(); i++)
				{
					rows.set(i, rows.get(i) + board_rows.get(i));
					if(c < field_size - 1) rows.set(i, rows.get(i) + v_seperator);
				}
			}
			if(r < field_size - 1)
			{
				String seperator = "";
				for(int i = 0; i < rows.get(0).length(); i++) seperator += h_seperator;
				rows.add(seperator);
			}
			for(String s : rows) ret.add(s);
		}
		return ret;
	}
}
