package TicTacToe;

import java.util.Scanner;

import TicTacToe.Board.Field;

public class TicTacToe
{
	static public void main(String[] argv)
	{
		Scanner scanner = new Scanner(System.in);
		
		Field field = new Field();
		
		String line_input = scanner.nextLine();
		while(!line_input.equals("e"))
		{
			System.out.println("  " + line_input);
			line_input = scanner.nextLine();
		}
	}
}
