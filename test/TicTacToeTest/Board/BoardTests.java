package TicTacToeTest.Board;

import static org.junit.Assert.*;

import org.junit.Test;

import TicTacToe.Board.Board;

public class BoardTests
{

	@Test
	public void test_has_0()
	{
		Board board = new Board();
		for(int c = 0; c < Board.boardSize(); c++) for(int r = 0; r < Board.boardSize(); r++) assertEquals(board.has(c, r), false);
	}
	@Test
	public void test_has_1()
	{
		Board board = Board.getFilledBoard(Board.Mark.X);
		for(int c = 0; c < Board.boardSize(); c++) for(int r = 0; r < Board.boardSize(); r++) assertEquals(board.has(c, r), true);
	}
	@Test
	public void test_has_2()
	{
		Board board = Board.getFilledBoard(Board.Mark.O);
		for(int c = 0; c < Board.boardSize(); c++) for(int r = 0; r < Board.boardSize(); r++) assertEquals(board.has(c, r), true);
	}
}
