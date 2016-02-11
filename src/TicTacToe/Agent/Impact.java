package TicTacToe.Agent;

import TicTacToe.Board.Board.Location;
import TicTacToe.Board.Field;

public interface Impact
{
	/**Will return an impact that checks to see if the move will complete a board
	 * 
	 * @return An impact object
	 */
	public static Impact doesCompleteImpact(){return new Impact(){@Override public double calculate(Decision decision){
		Field field = decision.getField();
		Field new_field = decision.getNextField();
		
		Location[] completed = field.getCompleteBoardLocations();
		Location[] next_complete = new_field.getCompleteBoardLocations();
		
		return next_complete.length - completed.length;
	}};}
	
	/**If the impact is that the move will result in a win, this returns a large number
	 * Otherwise this will return 0;
	 * 
	 * @return An impact object
	 */
	public static Impact doesWinImpact(){return new Impact(){@Override public double calculate(Decision decision){
		Field new_field = decision.getNextField();
		if(new_field.getWinner().equals(decision.getMove().getMove().getMark())) return 1000;
		return 0;
	}};}
	
	/**Returns an impact that says the move will give the player a free move to any square.
	 * 
	 * @return An impact object
	 */
	public static Impact doesSelectComplete(){return new Impact(){@Override public double calculate(Decision decision){
		Field new_field = decision.getNextField();
		if(new_field.getSelected() == 0) return 10;
		return 0;
	}};}
	
	
	/**Calculates the impact  of the move being made
	 * 
	 * @return
	 */
	public double calculate(Decision decision);
}
