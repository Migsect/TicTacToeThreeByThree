package TicTacToe;

import java.util.List;
import java.util.Scanner;

import TicTacToe.Board.Board;
import TicTacToe.Board.Board.Mark;
import TicTacToe.Board.Field;

public class TicTacToe
{
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
	static public void main(String[] argv)
	{
		// Creating the input scanner
		Scanner scanner = new Scanner(System.in);
		
		// creating the field and setting the selected board
		Field field = new Field();
		field.select(5);
		
		// Whoever starts first
		Mark turn = Mark.X;
		
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
			System.out.print("It is " + turn.getDisplay() + "'s turn. ");
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
				board.set(select, turn);
				// swapping turns
				if(turn == Mark.X) turn = Mark.O;
				else turn = Mark.X;
				// selecting the next board
				Board next_board = field.getBoard(select);
				if(next_board.isComplete()) field.select(0);
				else field.select(select);
			}
			if(!field.getWinner().equals(Mark.EMPTY))
			{
				System.out.println("Player " + field.getWinner().getDisplay() + " is the winner!");
				break;
			}
			
		}
		
		scanner.close();
		/*
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
		*/
	}
	
	public static void printLines(List<String> lines)
	{
		for(String s : lines) System.out.println(s);
	}
}
