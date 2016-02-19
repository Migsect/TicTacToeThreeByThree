package TicTacToe;

import java.util.Scanner;

import TicTacToe.Board.Board;
import TicTacToe.Board.Field;
import TicTacToe.Board.Field.Move;

public class HumanPlayer implements Player
{
	
	public final Field current_field;
	public final Scanner scanner;
	
	public static final String BOARD_PROMPT = "Please select a spot... [1-9] or 0 to exit";
	public static final String FIELD_PROMPT = "Please select a board... [1-9] or 0 to exit";
	public static final String INVALID_INPUT = "Sorry, that is not a correct input.";
	public static final String BAD_MOVE_INPUT = "Sorry, you cannot make that move.";
	
	HumanPlayer(Field field, Scanner scanner)
	{
		this.current_field = field;
		this.scanner = scanner;
	}
	
	public static int translateInput(int input)
	{
		switch (input)
		{
		  case 1: return 7;
		  case 2: return 8;
		  case 3: return 9;
		  case 4: return 4;
		  case 5: return 5;
		  case 6: return 6;
		  case 7: return 1;
		  case 8: return 2;
		  case 9: return 3;
		  default: return 0;
		}
	}
	
	@Override
	public Field.Move getMove()
	{
		boolean valid_move = false;
		
		// Getting the first input
		String line_input = "";
		while(!valid_move)
		{
			// Printing out what we want
			if(current_field.getSelected() == 0) System.out.println(HumanPlayer.FIELD_PROMPT);
			else System.out.println(HumanPlayer.BOARD_PROMPT);
			
			// Getting the first input
			line_input = scanner.nextLine();
			int input = -1;
			try{input = Integer.parseInt(line_input);}catch(NumberFormatException e){;}
			
			// Returning a null move will signify that the program should exit
			if(input == 0) return null;
			// Checking to see if the input is invalid
			if(input < 1 || input > 9)
			{
				System.out.println(HumanPlayer.INVALID_INPUT);
				continue;
			}
			
			// Translating the input
			input = HumanPlayer.translateInput(input);
			
			// Checking to see if they are selecting a bad field
			if(current_field.getSelected() == 0 && current_field.getBoard(input).isComplete())
			{
				System.out.println(HumanPlayer.BAD_MOVE_INPUT);
				continue;
			}
			if(!current_field.getBoard().get(input).equals(Board.Mark.EMPTY))
			{
				System.out.println(HumanPlayer.BAD_MOVE_INPUT);
				continue;
			}
			
			if(current_field.getSelected() == 0)
			{
				current_field.select(input);
				
				// Redisplaying the updated field so the user can select the correct one
				for(String s : current_field.toStringList()) System.out.println(s);
				continue;
			}
			else
			{
				
			}
		}
		
		return null;
	}
	
}
