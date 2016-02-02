package TicTacToe.Board;

import java.util.List;

import TicTacToe.Board.Board.Mark;
import TicTacToe.TextPicture.TextPicture;
import TicTacToe.TextPicture.TextPicture.Coord;

public class Field
{
	private static final int field_size = 3;
	//private static final char H_SEPERATOR = '=';
	//private static final char V_SEPERATOR = '|';
	private static final char SELECTED = '#';
	private static final char UNSELECTED = ' ';
	private static final int SELECT_SPACING = 1;
	private static final int EDGE_SPACING = 1;
	
	private Board[][] boards = new Board[field_size][field_size];
	// selected board has an interval of [0, field_size*field_size - 1]
	private int selected_board = field_size * (field_size / 2) + (field_size / 2) + 1; // the selected board
	
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
	
	/**Returns the column that is selected
	 * 
	 * @return The selected column
	 */
	public int getSelectedColumn(){return (this.selected_board - 1) % Field.field_size;}
	/**Returns the row that is selected
	 * 
	 * @return The selected row
	 */
	public int getSelectedRow(){return (this.selected_board - 1) / Field.field_size;}
	
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
		// Creating all the cells
		TextPicture[][] cells = new TextPicture[Field.field_size][Field.field_size];
		for(int r = 0; r < Field.field_size; r++) for(int c = 0; c < Field.field_size; c++)
		{
			TextPicture board = this.getBoard(r,c).toDisplay();
			TextPicture select_space_board = new TextPicture(board.getHeight() + 2 * Field.SELECT_SPACING, board.getWidth() + 2 * Field.SELECT_SPACING);
			
			// Filling the cell with the selection character if it is selected
			boolean do_fill = this.getSelectedRow() == r && this.getSelectedColumn() == c; // If we fill the space with the selection character
			for(int b_r = 0; b_r < select_space_board.getHeight(); b_r++) for(int b_c = 0; b_c < select_space_board.getHeight(); b_c++)
			{
				if(do_fill) select_space_board.setElement(b_c, b_r, Field.SELECTED);
				else select_space_board.setElement(b_c, b_r, Field.UNSELECTED);
			}
			try // Adding board to the select_space_baord
			{
				select_space_board.set(new Coord(Field.SELECT_SPACING, Field.SELECT_SPACING), board);
			}
			catch (Exception e){e.printStackTrace();}
			cells[r][c] = select_space_board;
		}

		int cell_size = Field.SELECT_SPACING * 2 + Board.boardLength();
		int field_size = Field.field_size * cell_size  + Field.field_size - 1;
		TextPicture field = new TextPicture(field_size, field_size);
		// adding the cells to their locations
		for(int r = 0; r < Field.field_size; r++) for(int c = 0; c < Field.field_size; c++)
		{
			int coord_r = r * (cell_size + 1);
			int coord_c = c * (cell_size + 1);
			try
			{
				field.set(new Coord(coord_r, coord_c), cells[r][c]);
				System.out.println("Setting cell to (" +r + ", " + c+ ")");
			}
			catch (Exception e){e.printStackTrace();}
		}
		TextPicture space_field = new TextPicture(field_size + 2 * Field.EDGE_SPACING, field_size + 2 * Field.EDGE_SPACING);
		boolean do_fill = this.selected_board == 0;
		for(int r = 0; r < space_field.getHeight(); r++) for(int c = 0; c < space_field.getWidth(); c++)
		{
			if(do_fill) space_field.setElement(c, r, Field.SELECTED);
		}
		try
		{
			space_field.set(new Coord(Field.EDGE_SPACING , Field.EDGE_SPACING), field);
		}
		catch (Exception e){e.printStackTrace();}
		
		
		
		return space_field.buildByRows();
	}
}
