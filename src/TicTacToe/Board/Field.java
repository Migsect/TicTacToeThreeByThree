package TicTacToe.Board;

import java.util.ArrayList;
import java.util.List;

import TicTacToe.Board.Board.Location;
import TicTacToe.Board.Board.Mark;
import TicTacToe.TextPicture.TextPicture;
import TicTacToe.TextPicture.TextPicture.Coord;

public class Field
{
	public static class Move
	{
		private final Board.Location loc; // the board to select
		private final Board.Move move;
		public Move(Board.Move move, Board.Location loc){this.loc = loc; this.move = move;}
		public Board.Location getLocation(){return loc;}
		public Board.Move getMove(){return move;}
		public String toString(){return "{l-" + loc.toString() + " : m-" + move.toString() + "}";}
	}
	
	private static final int field_size = 3;
	//private static final char H_SEPERATOR = '=';
	//private static final char V_SEPERATOR = '|';
	private static final char SELECTED = '#';
	private static final char UNSELECTED = ' ';
	private static final int SELECT_SPACING = 1;
	private static final int EDGE_SPACING = 1;
	
	private Board[][] boards = new Board[field_size][field_size];
	// selected board has an interval of [0, field_size*field_size]
	private int selected_board = field_size * (field_size / 2) + (field_size / 2) + 1; // the selected board
	private Mark current_turn = Mark.EMPTY;
	
	/**Makes a move
	 * This will mutate the field it is made on
	 * This will also apply the rules to the move
	 * 
	 * @param move The move that will be made
	 * @return The field the move was made on
	 */
	public Field makeMove(Field.Move move)
	{
		// Updating the field with the new move
		this.getBoard(move.getLocation()).set(move.getMove()); 
		// Updating the current turn
		this.current_turn = this.current_turn.getOpposite();
		// Updating the selected board
		this.select(move.getMove().getLocation().getCol(), move.getMove().getLocation().getRow());
		if(this.getBoard(this.getSelected()).isComplete()) this.select(0); 
		
		// Returning this field
		return this;
	}
	
	/**Sets the current turn to the mark m
	 * This will mutate the field it is made on
	 * 
	 * @param m The mark to set the turn to
	 * @return The field object that was set
	 */
	public Field setTurn(Mark m){this.current_turn = m; return this;}
	
	/**Gets the current turn of the field
	 * 
	 * @return The turn
	 */
	public Mark getTurn(){return this.current_turn;}
	
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
	public Board getBoard(int x)
	{
		if(x == 0) return null;
		return this.getBoard((x-1) % field_size, (x-1) / field_size);
	}
	
	/**Returns the board at the location
	 * 
	 * @param l The location to get the board at
	 * @return The board at the location
	 */
	public Board getBoard(Location l){return this.getBoard(l.getCol(), l.getRow());}
	
	/**Returns the currently selected board.
	 * A new board can be selected by using the ".select()" method
	 * 
	 * @return A board object
	 */
	public Board getBoard(){return this.getBoard(this.getSelected());}
	
	/**Will set the selected board based on the inputted column and row
	 * 
	 * @param c The column
	 * @param r The row
	 * @return The field object that this method was called on
	 */
	public Field select(int c, int r){this.select(c + field_size * r + 1); return this;}
	
	/**Sets the selected table to the specified index
	 * 
	 * @param x The index of the table to select
	 * @return The field object that this method was called on
	 */
	public Field select(int x){this.selected_board = x; return this;}
	
	/**Returns the selected board index
	 * This will return values ranging from 0 to 9
	 * where 0 specifies that there is not a selected board
	 * 
	 * @return The selected index
	 */
	public int getSelected(){return this.selected_board;}
	
	/**Get the selected board as a location
	 * 
	 * @return The selected location, returns null if the selected is all
	 */
	public Location getSelectedLocation()
	{
		if(this.getSelected() == 0) return null;
		else return new Location(this.getSelectedColumn(), this.getSelectedRow());
	}
	
	/**Returns the column that is selected
	 * Returns -1 if there is not a selected board
	 * 
	 * @return The selected column
	 */
	public int getSelectedColumn()
	{
		if(this.selected_board == 0) return -1;
		return (this.selected_board - 1) % Field.field_size;
	}
	
	/**Returns the row that is selected
	 * Returns -1 if there is not a selected board
	 *  
	 * @return The selected row
	 */
	public int getSelectedRow()
	{
		if(this.selected_board == 0) return -1;
		return (this.selected_board - 1) / Field.field_size;
	}
	
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
			TextPicture board = this.getBoard(c,r).toDisplay();
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
			cells[c][r] = select_space_board;
		}

		int cell_size = Field.SELECT_SPACING * 2 + Board.boardDisplaySize();
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
				// System.out.println("Setting cell to (" +r + ", " + c+ ")");
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
	
	/**Returns a list of strings that describe the state of the field and not the sub-boards
	 * 
	 * @return A list of strings
	 */
	public List<String> getInformation()
	{
		List<String> strings = new ArrayList<>();
		
		strings.add("Field:");
		strings.add("  Selected Board: " + this.getSelected());
		strings.add("  Selected Column: " + this.getSelectedColumn());
		strings.add("  Selected Row: " + this.getSelectedRow());
		
		return strings;
	}
	/**Gets all the locations of the boards
	 * This will generally return field_size * field_size elements
	 * 
	 * @return An array of all board locations
	 */
	public Location[] getBoardLocations()
	{
		List<Location> boards = new ArrayList<>();
		
		for(int c = 0; c < Field.field_size; c++) for(int r = 0; r < Field.field_size; r++) boards.add(new Board.Location(c, r));
		
		return boards.toArray(new Location[boards.size()]);
	}
	
	/**Returns all the board locations that are completed.
	 * 
	 * @return An array of all complete boards
	 */
	public Location[] getCompleteBoardLocations()
	{
		List<Location> boards = new ArrayList<>();
		
		for(Location l : this.getBoardLocations()) if(this.getBoard(l.getCol(), l.getRow()).isComplete()) boards.add(l);
		
		return boards.toArray(new Location[boards.size()]);
	}
	
	/**Returns all the board locations that are still incomplete
	 * 
	 * @return An array of all incomplete boards
	 */
	public Location[] getIncompleteBoardLocations()
	{
		List<Location> boards = new ArrayList<>();
		
		for(Location l : this.getBoardLocations()) if(!this.getBoard(l.getCol(), l.getRow()).isComplete()) boards.add(l);
		
		return boards.toArray(new Location[boards.size()]);
	}
	
	/**Gets all the boards in the field
	 * 
	 * @return The boards in the field
	 */
	public Board[] getBoards(){return this.getBoards(this.getBoardLocations());}
	
	/**Gets all the boards in the field at the listed locations
	 * 
	 * @param locations The locations of the boards
	 * @return The boards with the locations specified
	 */
	public Board[] getBoards(Location[] locations)
	{
		List<Board> boards = new ArrayList<>();
		for(Location l : locations) boards.add(this.getBoard(l));
		return boards.toArray(new Board[boards.size()]);
	}
	
	/**Clones the field
	 * All objects within it should be unique and immutable from the original field.
	 */
	public Field clone()
	{
		Field field = new Field();
		
		// copying all fields
		for(int c = 0; c < Field.field_size; c++) for(int r = 0; r < Field.field_size; r++) field.boards[c][r] = this.boards[c][r].clone();
		field.selected_board = this.selected_board;
		field.current_turn = this.current_turn;
		
		return field;
	}
}
