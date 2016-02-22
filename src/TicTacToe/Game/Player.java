package TicTacToe.Game;

import TicTacToe.Board.Board.Mark;
import TicTacToe.Board.Field;

public interface Player
{
	public Field.Move getMove();
	public Mark getTeam();
}
