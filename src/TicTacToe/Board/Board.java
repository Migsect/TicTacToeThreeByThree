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
		public String getDisplay(){return this.display;}
		public int getValue(){return this.value;}
	}
	
	private static final int BOARD_SIZE = 3;
	private static final char H_SEPERATOR = '=';
	private static final char V_SEPERATOR = '|';
	private static final int EDGE_SPACING = 1;
	private Mark[][] places = new Mark[BOARD_SIZE][BOARD_SIZE];
	
	/**Returns the length of the board's display with its edge spacing.
	 * 
	 * @return The length of a displayed board
	 */
	public static int boardLength(){return 2*BOARD_SIZE + 2*EDGE_SPACING - 1;}
	
	/**Returns the collapsed text picture of the board with the mark m
	 * 
	 * @param m The mark to fill the collapsed board
	 * @return A text picture of the collapsed board
	 */
	private static TextPicture getCollapsedPicture(Mark m)
	{
		int picture_side = boardLength();
	  TextPicture picture = new TextPicture(picture_side, picture_side);
	  for(int r = EDGE_SPACING; r < BOARD_SIZE + 2*EDGE_SPACING + 1; r++) for(int c = EDGE_SPACING; c < BOARD_SIZE + 2*EDGE_SPACING + 1; c++)
	  {
	  	picture.setElement(c, r, m.getDisplay().charAt(0));
	  }
	  
	  return picture;
	}
	
	/**Sets a mark on the board to the location
	 * 
	 * @param c The column
	 * @param r The row
	 * @param m The mark
	 */
	public void set(int c, int r, Mark m){this.places[c % BOARD_SIZE][r % BOARD_SIZE] = m;}
	/**Gets the mark at a location on the board
	 * 
	 * @param c The column
	 * @param r The row
	 * @return The mark
	 */
	public Mark get(int c, int r){return this.places[c % BOARD_SIZE][r % BOARD_SIZE];}
	
	/**Sets a mark based on the board's index (1 to size*size)
	 * 
	 * @param x The index
	 * @param m The mark
	 */
	public void set(int x, Mark m){this.set((x-1) % BOARD_SIZE, (x-1) / BOARD_SIZE, m);}
	/**Gets a mark based on the inputted index (1 to size*size)
	 * 
	 * @param x The index
	 * @return The mark
	 */
	public Mark get(int x){return this.get((x-1) % BOARD_SIZE, (x-1) / BOARD_SIZE);}
	
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
	/**Gets the board as a text picture for displaying and manipulation
	 * 
	 * @return A text picture representing the board
	 */
	public TextPicture toPicture()
	{
		int picture_side = boardLength();
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
}
