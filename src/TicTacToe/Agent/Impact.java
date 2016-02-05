package TicTacToe.Agent;

import TicTacToe.Board.Field;

public interface Impact
{
	/**Calculates the impact  of the move being made
	 * 
	 * @return
	 */
	public double calculate(Field field, Field.Move move);
}
