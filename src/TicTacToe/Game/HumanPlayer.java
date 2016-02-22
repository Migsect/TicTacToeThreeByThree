package TicTacToe.Game;

import java.util.Scanner;

import TicTacToe.Board.Board;
import TicTacToe.Board.Board.Mark;
import TicTacToe.Board.Field;

public class HumanPlayer implements Player
{
	
	public final Field current_field;
	public final Scanner scanner;
	public final Mark team;
	
	public static final String BOARD_PROMPT = "Please select a spot... [1-9] or 0 to exit";
	public static final String FIELD_PROMPT = "Please select a board... [1-9] or 0 to exit";
	public static final String INVALID_INPUT = "Sorry, that is not a correct input.";
	public static final String BAD_MOVE_INPUT = "Sorry, you cannot make that move.";
	
	HumanPlayer(Field field, Scanner scanner, Mark team)
	{
		this.current_field = field;
		this.scanner = scanner;
		this.team = team;
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
		int selected_board = current_field.getSelected();
	  int selected_spot = -1;
		
		// Getting the first input
		String line_input = "";
		while(selected_board < 1 || selected_board > 9 || selected_spot < 1 || selected_spot > 9)
		{
			// Printing out what we want
			if(selected_board == 0) System.out.println(HumanPlayer.FIELD_PROMPT);
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
			if(selected_board == 0 && current_field.getBoard(input).isComplete())
			{
				System.out.println(HumanPlayer.BAD_MOVE_INPUT);
				continue;
			}
			// Checking to see if they are making a bad move (for a board)
			if(selected_board != 0 && !current_field.getBoard().get(input).equals(Board.Mark.EMPTY))
			{
				System.out.println(HumanPlayer.BAD_MOVE_INPUT);
				continue;
			}
			
			// Selecting a board
			if(selected_board == 0)
			{
				current_field.select(input);
				selected_board = input;
				
				// Redisplaying the updated field so the user can select the correct one
				for(String s : current_field.toStringList()) System.out.println(s);
				continue;
			}
			else
			{
				selected_spot = input;
			}
		}
		
		Board.Location field_loc = new Board.Location(selected_board);
		Board.Location board_loc = new Board.Location(selected_spot);
		Board.Move b_move = new Board.Move(team, board_loc);
		Field.Move f_move = new Field.Move(b_move, field_loc);
		
		return f_move;
	}

	@Override
	public Mark getTeam(){return this.team;}
	
}
