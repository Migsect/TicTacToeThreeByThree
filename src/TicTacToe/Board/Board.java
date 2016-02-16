package TicTacToe.Board;

import java.util.ArrayList;
import java.util.List;

import TicTacToe.TextPicture.TextPicture;

public class Board
{
	/**Used to representing marks on the board
	 * As well as to represent players.
	 */
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
		/**Gets the display string for the mark
		 * 
		 * @return A display string
		 */
		public String getDisplay(){return this.display;}
		/**Gets a integer representation of the mark
		 * 
		 * @return A int that represents the mark
		 */
		public int getValue(){return this.value;}
		/**Gets the opposite of the mark
		 * 
		 * @return The opposite mark of the mark
		 */
		public Mark getOpposite()
		{
			switch(this)
			{
  			case X: return Mark.O;
  			case O: return Mark.X;
  			case EMPTY: return Mark.EMPTY;
  			default: return null;
			} 
		}
	}
	/**Location objects allows places on the board to categorized as an object
	 */
	public static class Location
	{
		private final int row;
		private final int col;
		public Location(int col, int row){this.row = row; this.col = col;}
		public int getRow(){return row;}
		public int getCol(){return col;}
		public String toString(){return "(c" + col + ", r" + row + ")";}
	}
	/**Move objects allow moves that change to board to be categorized as an object
	 */
	public static class Move
	{
		private final Mark m;
		private final Location l;
		public Move(Mark m, Location l){this.m = m; this.l = l;}
		public Move(Mark m, int c, int r){this.m = m; this.l = new Location(c,r);}
		public Mark getMark(){return m;}
		public Location getLocation(){return l;}
		public String toString(){return "[l-" + l.toString() + " : mk-" + m.toString() + "]";}
	}
	
	private static final int BOARD_SIZE = 3;
	private static final char H_SEPERATOR = '=';
	private static final char V_SEPERATOR = '|';
	private static final int EDGE_SPACING = 1;
	
	private Mark[][] places = new Mark[BOARD_SIZE][BOARD_SIZE];
	
	/**Returns the board size of all boards
	 * 
	 * @return The board size
	 */
	public static int boardSize(){return Board.BOARD_SIZE;}
	
	/**Returns the length of the board's display with its edge spacing.
	 * 
	 * @return The length of a displayed board
	 */
	public static int boardDisplaySize(){return 2*BOARD_SIZE + 2*EDGE_SPACING - 1;}
	
	/**Returns the collapsed text picture of the board with the mark m
	 * 
	 * @param m The mark to fill the collapsed board
	 * @return A text picture of the collapsed board
	 */
	private static TextPicture getCollapsedPicture(Mark m)
	{
		int picture_side = boardDisplaySize();
	  TextPicture picture = new TextPicture(picture_side, picture_side);
	  for(int r = EDGE_SPACING; r < BOARD_SIZE + 2*EDGE_SPACING + 1; r++) for(int c = EDGE_SPACING; c < BOARD_SIZE + 2*EDGE_SPACING + 1; c++)
	  {
	  	picture.setElement(c, r, m.getDisplay().charAt(0));
	  }
	  
	  return picture;
	}
	
	public static boolean isCorner(Location l)
	{
		int col = l.getCol();
		int row = l.getRow();
		
		if(col == 0 && row == 0) return true;
		if(col == Board.BOARD_SIZE && row == 0) return true;
		if(col == 0 && row == Board.BOARD_SIZE) return true;
		if(col == Board.BOARD_SIZE && row == Board.BOARD_SIZE) return true;
		
		return false;
	}
	public static boolean isMiddle(Location l)
	{
		if(l.getCol() == Board.BOARD_SIZE / 2 && l.getRow() == Board.BOARD_SIZE / 2) return true;
		else return false;
	}
	public static boolean isEdge(Location l)
	{
		return !Board.isMiddle(l) && !Board.isCorner(l);
	}
	
	/**Returns a board filled with the marks m
	 * 
	 * @param m The mark to fill the board with
	 * @return The board
	 */
	public static Board getFilledBoard(Mark m)
	{
		Board board = new Board();
		for(int c = 0; c < Board.BOARD_SIZE; c++) for(int r = 0; r < Board.BOARD_SIZE; r++) board.set(c, r, m);
		return board;
	}
	
	/**Sets a mark on the board to the location
	 * 
	 * @param c The column
	 * @param r The row
	 * @param m The mark
	 * @return The board
	 */
	public Board set(int c, int r, Mark m){this.places[c % BOARD_SIZE][r % BOARD_SIZE] = m; return this;}
	
	/**Befores the move on the board
	 * 
	 * @param move The move
	 * @return The board that the move was enacted on
	 */
	public Board set(Move move){return this.set(move.getLocation().getCol(), move.getLocation().getRow(), move.getMark());}
	
	/**Checks to see if the move can be made
	 * 
	 * @param move The move to see if can be made
	 * @return True if the move can be made (the spot is empty)
	 */
	public boolean can(Move move){return this.has(move.getLocation().getCol(), move.getLocation().getRow());}
	
	/**Gets the mark at a location on the board
	 * 
	 * @param c The column
	 * @param r The row
	 * @return The mark
	 */
	public Mark get(int c, int r){return this.places[c % BOARD_SIZE][r % BOARD_SIZE];}
	
	/**Returns whether or not this board has a mark in the place
	 * 
	 * @param c The column
	 * @param r The row
	 * @return True if there is a mark in the place
	 */
	public boolean has(int c, int r){return !this.get(c, r).equals(Mark.EMPTY);}
	
	/**Sets a mark based on the board's index (1 to size*size)
	 * 
	 * @param x The index
	 * @param m The mark
	 * @return The board
	 */
	public Board set(int x, Mark m){this.set((x-1) % BOARD_SIZE, (x-1) / BOARD_SIZE, m); return this;}
	
	/**Gets a mark based on the inputted index (1 to size*size)
	 * 
	 * @param x The index
	 * @return The mark
	 */
	public Mark get(int x){return this.get((x-1) % BOARD_SIZE, (x-1) / BOARD_SIZE);}
	
	/**Returns whether or not this board has a mark in the place
	 * 
	 * @param x The index
	 * @return True if there is a mark in the place
	 */
	public boolean has(int x){return !this.get(x).equals(Mark.EMPTY);}
	
	/**Checks to see if the board is completed
	 * 
	 * @return
	 */
	public boolean isComplete()
	{
	  if(!this.getWinner().equals(Mark.EMPTY)) return true;
		for(Mark[] a : this.places) for(Mark m : a) if(m.equals(Mark.EMPTY)) return false;
	  return true;
	}
	/**Gets the winner if there is one
	 * Else it will return an empty mark
	 * 
	 * @return The mark of the winner
	 */
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
	
	/**Creates a new board (not a super board)
	 */
	public Board()
	{
		for(int r = 0; r < BOARD_SIZE; r++) for(int c = 0; c < BOARD_SIZE; c++) places[c][r] = Mark.EMPTY;
	}
	
	/**Gets the board as a string list for displaying
	 * 
	 * @return A string list representing the board
	 */
	public List<String> toStringList(){return this.toPicture().buildByRows();}
	/**Displays the board as a string. This is not meant for display and will be
	 * arranged in arrays of arrays of the mark enums
	 * This is a programic representation
	 */
	public String toString()
	{
		String str = "";
		for(int r = 0; r < Board.BOARD_SIZE; r++) 
		{
			str += "{";
			for(int c = 0; c < Board.BOARD_SIZE; c++)
  		{
				str += this.places[c][r].toString();
				if(c < Board.BOARD_SIZE - 1) str += ",";
  		}
			str += "}";
			if(r < Board.BOARD_SIZE - 1) str += ",";
				
		}
		return str;
	}
	
	/**Gets the board as a text picture for displaying and manipulation
	 * 
	 * @return A text picture representing the board
	 */
	public TextPicture toPicture()
	{
		int picture_side = boardDisplaySize();
	  TextPicture picture = new TextPicture(picture_side, picture_side);
	  // drawing the marks
	  for(int r = 0; r < BOARD_SIZE; r++) for(int c = 0; c < BOARD_SIZE; c++)
	  {
	  	Mark m = this.get(c, r);
	    int p_c = EDGE_SPACING + 2 * c;
	  	int p_r = EDGE_SPACING + 2 * r;
	  	picture.setElement(p_c, p_r, m.getDisplay().charAt(0));
	  }
	  
	  // drawing the horizontal lines
	  for(int r = EDGE_SPACING + 1; r < 2 * BOARD_SIZE + EDGE_SPACING - 1; r+=2) for(int c = EDGE_SPACING; c < 2 * BOARD_SIZE + EDGE_SPACING - 1; c++)
	  {
	  	picture.setElement(c, r, H_SEPERATOR);
	  	//System.out.println("Setting (" + c + ", " + r + ") to element '" + H_SEPERATOR + "'");
	  }
	  // drawing the vertical lines
	  for(int r = EDGE_SPACING ; r < 2 * BOARD_SIZE + EDGE_SPACING - 1; r++) for(int c = EDGE_SPACING + 1; c < 2 * BOARD_SIZE + EDGE_SPACING - 1; c+=2)
	  {
	  	picture.setElement(c, r, V_SEPERATOR);
	  	//System.out.println("Setting (" + c + ", " + r + ") to element '" + V_SEPERATOR + "'");
	  }
	  return picture;
	}
	/**Returns a display version of the board that will collapse the board if it is complete
	 * 
	 * @return A text picture of the board display
	 */
	public TextPicture toDisplay()
	{
		if(this.isComplete()) return Board.getCollapsedPicture(this.getWinner());
		else return this.toPicture();
	}
	/**Gets a list of strings that display some information about the board
	 * 
	 * @return list of strings
	 */
	public List<String> getInformation()
	{
		List<String> strings = new ArrayList<>();
		
		strings.add("Board:");
		strings.add("  isComplete: " + this.isComplete());
		strings.add("  getWinner: '" + this.getWinner() + "'");
		strings.addAll(this.toStringList());
		
		return strings;
	}
	/**Clones the board and returns a unique board object
	 * 
	 */
	public Board clone()
	{
		Board clone = new Board();
		for(int r = 0; r < Board.BOARD_SIZE; r++) for(int c = 0; c < Board.BOARD_SIZE; c++) clone.places[c][r] = this.places[c][r];
		return clone;
	}
	/**Checks to see if the boards are equal
	 * 
	 * @param other The other board
	 * @return True if they are equal
	 */
	public boolean equals(Board other)
	{
		for(int r = 0; r < Board.BOARD_SIZE; r++) for(int c = 0; c < Board.BOARD_SIZE; c++) if(this.places[c][r] != other.places[c][r]) return false;
		return true;
	}
	/**Checks to see if the board is equal to another object
	 * 
	 */
	public boolean equals(Object other)
	{
		if(other instanceof Board) return this.equals((Board)other);
		return false;
	}
	/**Returns the hashcode for the object.  Due to there being so little states, this hashcode will
	 * allow equalivation
	 * 
	 */
	public int hashCode()
	{
		return this.sum() + Board.BOARD_SIZE * Board.BOARD_SIZE * this.count();
	}
	
	/**Will take the sum of all the marks in the board
	 * This sum has a range between -board_size and board_size given the case that
	 * the board is all O or all X
	 * 
	 * @return The sum of the marks' values
	 */
	public int sum()
	{
		int sum = 0;
		for(Mark[] marks : this.places) for(Mark m : marks) sum += m.getValue();
		return sum;
	}
	
	/**Counts the number of non-empty marks in the board
	 * 
	 * @return The number of non-empty marks
	 */
	public int count()
	{
		int count = 0;
		for(Mark[] marks : this.places) for(Mark m : marks) if(!m.equals(Mark.EMPTY)) count++;
		return count;
	}
	
	/**Checks to see if the mark can win in one move.
	 * 
	 * @param m The mark to check to see if it can win
	 * @return True if the mark can win in one move
	 */
	public boolean canWin(Mark m)
	{
		for(int r = 0; r < Board.BOARD_SIZE; r++) for(int c = 0; c < Board.BOARD_SIZE; c++)
		{
			if(this.has(c, r)) continue;
			if(this.clone().set(c, r, m).getWinner().equals(m)) return true;
		}
		return false;
	}
	/**Checks to see if the mark can win by placing up to moves marks
	 * 
	 * @param m The mark to check
	 * @param moves The number of marks that are allowed to be placed
	 * @return True if they can win the number of marks
	 */
	public boolean canWin(Mark m, int moves)
	{
		for(int r = 0; r < Board.BOARD_SIZE; r++) for(int c = 0; c < Board.BOARD_SIZE; c++)
		{
			Board board = this.clone().set(c, r, m);
			if(moves <= 1 && this.canWin(m)) return true;
			else if(board.canWin(m, moves - 1)) return true;
		}
		return false;
	}
	/**Checks to see if the mark can complete the board (fill or win)
	 * 
	 * @param m The mark
	 * @return True if the board can be completed
	 */
	public boolean canComplete(Mark m)
	{
		if(this.canWin(m)) return true;
		if(Math.abs(this.count() + 1) == Board.BOARD_SIZE * Board.BOARD_SIZE) return true;
		return false;
	}
	
	/**Checks to see if using the mark can complete by placing up to moves marks
	 * 
	 * @param m The mark
	 * @param moves The number of marks allowed to be placed
	 * @return True if the board can be completed
	 */
	public boolean canComplete(Mark m, int moves)
	{
		if(this.canWin(m, moves)) return true;
		if(Math.abs(this.count() + moves) == Board.BOARD_SIZE * Board.BOARD_SIZE) return true;
		return false;
	}
	
	/**Get the winning locations for the mark
	 * 
	 * @param m The mark
	 * @return The locations that will allow the mark to win
	 */
	public Location[] getWinningLocations(Mark m) 
	{
		List<Location> winning = new ArrayList<>();
		Location[] empty = this.getEmptyLocations();
		for(Location l : empty)
		{
			if(this.set(new Move(m, l)).getWinner().equals(m)) winning.add(l);
		}
		return winning.toArray(new Location[winning.size()]);
	}
	/**Gets the winning moves that either player can make
	 * 
	 * @return The moves for both players that would make to win
	 */
	public Move[] getWinningMoves()
	{
		List<Move> winning = new ArrayList<>();
		for(Location l : this.getWinningLocations(Mark.X)) winning.add(new Move(Mark.X, l));
		for(Location l : this.getWinningLocations(Mark.O)) winning.add(new Move(Mark.O, l));
		return winning.toArray(new Move[winning.size()]);	
	}
	/**Returns an array of all the empty locations that a mark can be placed in
	 * 
	 * @return The empty locations
	 */
	public Location[] getEmptyLocations()
	{
		List<Location> empty = new ArrayList<>();
		for(int r = 0; r < Board.BOARD_SIZE; r++) for(int c = 0; c < Board.BOARD_SIZE; c++){if(!this.has(c, r)) empty.add(new Location(c, r));}	
		return empty.toArray(new Location[empty.size()]);	
	}
}
