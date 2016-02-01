package TicTacToe;

import java.util.List;
import java.util.Scanner;

import TicTacToe.Board.Board;
import TicTacToe.Board.Field;

public class TicTacToe
{
	static public void main(String[] argv)
	{
		Scanner scanner = new Scanner(System.in);
		
		Field field = new Field();
		
		Board.Mark current_turn = Board.Mark.X;
		int current_board = 5;
		
		String line_input = "";
		while(!line_input.equals("e"))
		{
			System.out.println();
			printLines(field.toStringList());
			
			// Input Scanning
			System.out.println("It is " + current_turn.getDisplay() + "'s turn");
			line_input = scanner.nextLine();
			int selection = -1;
			while(selection == -1 && !line_input.equals("e") &&
					!field.getBoard(current_board - 1).get(selection).equals(Board.Mark.EMPTY))
			{
  			try
  			{
  				selection = Integer.parseInt(line_input);
  			}
  			catch(NumberFormatException e){;}
  			if(selection < 1 || selection > 9)
  			{
  				System.out.println("Please enter a number from 1 to 9.  If you wish to exit then enter 'e'");
  			}
  			if(!field.getBoard(current_board - 1).get(selection).equals(Board.Mark.EMPTY))
  			{
  				System.out.println("Please select a not filled space!");
  			}
			}
			// setting the board as we got a good input
			field.getBoard(current_board - 1).set(selection - 1, current_turn);
			current_board = selection;
			if(current_turn.equals(Board.Mark.X)) current_turn = Board.Mark.O;
			else current_turn = Board.Mark.X;
		}
	}
	
	public static void printLines(List<String> lines)
	{
		for(String s : lines) System.out.println(s);
	}
}
