package TicTacToe.Game;

import TicTacToe.Board.Board.Mark;
import TicTacToe.Board.Field;
import TicTacToe.Board.Field.Move;

public class Game
{
	private Field field;
	private Player player_X;
	private Player player_O;
	
	public Game(Field field)
	{
		this.field = field;
	}
	
	/**Sets who is player x
	 * 
	 * @param player
	 */
	public void setPlayerX(Player player){this.player_X = player;}
	/**Sets who is player o
	 * 
	 * @param player
	 */
	public void setPlayerO(Player player){this.player_O = player;}
	
	/**Takes a turn depending on who's turn it is
	 * 
	 * @return
	 */
	public boolean turn()
	{
		Move move = null;
		if(field.getTurn().equals(Mark.X)) move = player_X.getMove();
		if(field.getTurn().equals(Mark.O)) move = player_O.getMove();
		if(move == null) return false;
		field.makeMove(move);
		return true;
	}
	
	public boolean detectWinner()
	{
		if(field.isComplete())
		{
			Mark winner = field.getWinner();
			if(winner.equals(Mark.EMPTY)) System.out.println("Player " + winner.getDisplay() + " has won!");
			else System.out.println("Itsa Tie!");
			return true;
		}
		return false;
	}
	
	/**Displays the board
	 */
	public void display(){for(String s : field.toStringList()) System.out.println(s);}
	
}
