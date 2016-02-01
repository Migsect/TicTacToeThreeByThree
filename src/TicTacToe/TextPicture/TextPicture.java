package TicTacToe.TextPicture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextPicture
{
	private static final char EMPTY_SPACE = ' ';
	
	public class Coord
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
	private List<LocatedTextPicture> inner_generators = new ArrayList<>();
	
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
	
	public int getWidth(){return this.width;}
	public int getHeight(){return this.height;}
	public char getElement(int c, int r){return this.elements[c % width][r % height];}
	public TextPicture setElement(int c, int r, char e){this.elements[c % width][r % height] = e; return this;}
	
	public String build()
	{
		String s = "";
		List<String> rows = this.buildByRows();
		for(String r : rows) s += r + '\n';
		return s;
	}
	public List<String> buildByRows()
	{
		List<String> rows = new ArrayList<>();
		for(int r = 0; r < this.height; r++)
		{
			String row = "";
			for(int c = 0; c < this.width; c++)
			{
				
			}
		}
	}
	
}
