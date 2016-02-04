package TicTacToe.TextPicture;

import java.util.ArrayList;
import java.util.List;

public class TextPicture
{
	private static final char EMPTY_SPACE = ' ';
	
	public static class Coord
	{
		private final int x;
		private final int y;
		public Coord(int x, int y){this.x = x; this.y = y;}
		public int getX(){return this.x;}
		public int getY(){return this.y;}
		public int hashCode(){return this.x + this.y;}
		public boolean equals(Object other){if (other instanceof Coord) return this.equals((Coord) other); else return false;}
		public boolean equals(Coord other){return this.x == other.x && this.y == other.y;}
		public Object clone(){return this;} // because the coord is a final object in its variables
	}
	/** A pair of text picture coord
	 * 
	 * @author Alex
	 *
	 */
	private class LocatedTextPicture
	{
		private final Coord c;
		private final TextPicture p;
		private LocatedTextPicture(Coord c, TextPicture p)
		{
			this.c = c;
			this.p = p;
		}
		public Coord getCoord(){return c;}
		public TextPicture getPicture(){return p;}
	}
	
	// The width of the picture
	private final int width;
	// The height of the picture
	private final int height;
	
	private final char[][] elements;
	
	// Mapping 
	private List<LocatedTextPicture> inner_pictures = new ArrayList<>();
	/**Constructs a TextPicture of width and height filled with space
	 * 
	 * @param width
	 * @param height
	 */
	public TextPicture(int width, int height)
	{
		this.width = width;
		this.height = height;
		
		this.elements = new char[width][height];
		for(int c = 0; c < width; c++) for(int r = 0; r < height; r++) this.elements[c][r] = EMPTY_SPACE;
	}
	
	/**Sets another text picture to be displayed within itself
	 * 
	 * 
	 * @param coord The coord to place the picture at
	 * @param picture The text picture to add
	 * @return The set TextPicture
	 * @throws Exception If the picture is the same picture
	 */
	public TextPicture set(Coord coord, TextPicture picture) throws Exception
	{
		LocatedTextPicture l_picture = new LocatedTextPicture(coord, picture);
		this.inner_pictures.add(l_picture);
		return this;
	}
	
	/**Gets the width of the picture
	 * 
	 * @return The width of the picture
	 */
	public int getWidth(){return this.width;}
	/**Gets the height of the picture
	 * 
	 * @return The height of the picture
	 */
	public int getHeight(){return this.height;}
	/**Returns the character at the column and row
	 * 
	 * @param c The column
	 * @param r The row
	 * @return The character at the position
	 */
	public char getElement(int c, int r){return this.elements[c % width][r % height];}
	/**Sets a character in the picture
	 * 
	 * @param c The column
	 * @param r The row
	 * @param e The character
	 * @return The textpicture object for smiles
	 */
	public TextPicture setElement(int c, int r, char e){this.elements[c % width][r % height] = e; return this;}
	
	/**Combines the picture into a singular string seperated with newlines
	 * 
	 * @return A string of the picture
	 */
	public String build()
	{
		String s = "";
		List<String> rows = this.buildByRows();
		for(String r : rows) s += r + '\n';
		return s;
	}
	/**Returns a list of strings that are the picture
	 * 
	 * @return A list of strings that are the picture
	 */
	public List<String> buildByRows()
	{
		List<String> rows = new ArrayList<>();
		TextPicture collapsed = this.collapse();
		for(int r = 0; r < this.height; r++)
		{
			String row = "";
			for(int c = 0; c < this.width; c++) row += collapsed.elements[c][r];
		  rows.add(row);
		}
		return rows;
	}
	/**Collapses all the inner pictures into a singualr text picture
	 * 
	 * @return A collapsed text picture
	 */
	public TextPicture collapse()
	{
		TextPicture collapsed = new TextPicture(this.width, this.height);
		for(int x = 0; x < this.width; x++) for(int y = 0; y < this.height; y++)
		{
			collapsed.setElement(x, y, this.getElement(x, y));
		}
		if(this.inner_pictures.size() < 1) return collapsed;
		for(LocatedTextPicture l_p : this.inner_pictures)
		{
			Coord coord = l_p.getCoord();
			TextPicture picture = l_p.getPicture().collapse();
			
			int c_start = coord.getX();
			int r_start = coord.getY();
			int c_stop = c_start + picture.getWidth();
			int r_stop = r_start + picture.getHeight();
			if(c_stop > this.width) c_stop = this.width;
			if(r_stop > this.height) r_stop = this.height;
			
			for(int c = c_start; c < c_stop; c++) for(int r = r_start; r < r_stop; r++)
			{
				int inner_c = c - c_start;
				int inner_r = r - r_start;
				collapsed.setElement(c, r, picture.getElement(inner_c, inner_r));
			}
		}
		return collapsed;
	}
	
}
