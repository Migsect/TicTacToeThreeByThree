package TicTacToe;

import java.util.List;
import java.util.Scanner;

import TicTacToe.Agent.Agent;
import TicTacToe.Agent.Impact;
import TicTacToe.Board.Board;
import TicTacToe.Board.Board.Mark;
import TicTacToe.Board.Field;

public class TicTacToe
{
	/**Translates num pad inputs to indexes
	 * 
	 * @param input numpad input
	 * @return The input as index
	 */
	static public int inputToIndex(int input)
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
	/**Main method
	 * 
	 * @param argv command line arguments
	 */
	static public void main(String[] argv)
	{
		// Creating the input scanner
		Scanner scanner = new Scanner(System.in);
		
		// creating the field and setting the selected board
		Field field = new Field();
		field.select(5);
		field.setTurn(Mark.X);
		
		// Creating the Agent AI
		Agent agent = new Agent(Mark.O, 4);
		// Adding the agents impact calculators
		agent.addImpact(Impact.doesWinImpact());
		
		// Getting the stuff
		String line_input = "";
		boolean bad_input = false;
		boolean bad_move = false;
		while(!line_input.equals("0"))
		{
			//printLines(field.getInformation());
			//printLines(field.getBoard().getInformation());
			printLines(field.toStringList());
			if(bad_input) System.out.println("Sorry, please enter a move between 1 and 9. Enter 0 to exit."); 
			else if(bad_move) System.out.println("Sorry, you cannot make that move.");
			else System.out.println();
			System.out.print("It is " + field.getTurn().getDisplay() + "'s turn. ");
			if(field.getSelected() == 0) System.out.println("  Please select a board...");
			else System.out.println("  Please select a spot...");
			
			// Reading the input
			line_input = scanner.nextLine();
			int input = -1;
			try{input = Integer.parseInt(line_input);}catch(NumberFormatException e){;}
			
			// Responding to the input
			if(input == 0) break;
			if(input == -1 || input < 0 || input > 9)
			{
				bad_input = true;
				continue;
			} else bad_input = false;
			
			int select = inputToIndex(input);
			if(field.getSelected() == 0)
			{
				Board next_board = field.getBoard(select);
				if(next_board.isComplete())
				{
					bad_move = true;
					continue;
				}
				field.select(select);
			}
			else
			{
				Board board = field.getBoard();
				// Checking to see if the move is legal
				if(board.get(select) != Mark.EMPTY)
				{
					bad_move = true;
					continue;
				}
				// setting the board's element
				board.set(select, field.getTurn());
				// swapping turns
				field.setTurn(field.getTurn().getOpposite());
				
				// selecting the next board
				Board next_board = field.getBoard(select);
				if(next_board.isComplete()) field.select(0);
				else field.select(select);
				
				// Displaying the board after the player makes their move
				printLines(field.toStringList());
				
				// Time for the AI to decide
				Field.Move move = agent.decide(field);
				field.makeMove(move);
				
				// Displaying the AI's move
				System.out.println(move.toString());
				
			}
			
			// Win check
			if(!field.getWinner().equals(Mark.EMPTY))
			{
				// Displaying the final state of the field
				printLines(field.toStringList());
				// Saying who won!
				System.out.println("Player " + field.getWinner().getDisplay() + " is the winner!");
				break;
			}
			
			
			
		}
		
		scanner.close();
	}
	
	/**Printing all the lines of the string list input
	 * 
	 * @param lines The lines to print
	 */
	public static void printLines(List<String> lines)
	{
		for(String s : lines) System.out.println(s);
	}
}
