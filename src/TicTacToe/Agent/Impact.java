package TicTacToe.Agent;

import TicTacToe.Board.Board;
import TicTacToe.Board.Board.Location;
import TicTacToe.Board.Board.Location.Direction;
import TicTacToe.Board.Board.Mark;
import TicTacToe.Board.Field;

public interface Impact
{
	/**Will return an impact that checks to see if the move will complete a board
	 * 
	 * @return An impact object
	 */
	public static Impact doesComplete(int weight, int win_mult){return new Impact(){@Override public double calculate(Decision decision){
		Field field = decision.getField();
		Field new_field = decision.getNextField();
		
		Field.Move move = decision.getMove();
		Location board = move.getLocation();
		
		Location[] completed = field.getCompleteBoardLocations();
		Location[] next_complete = new_field.getCompleteBoardLocations();
		
		if(new_field.getBoard(board).getWinner().equals(move.getMove().getMark())) return (next_complete.length - completed.length) * weight * win_mult;
		
		return (next_complete.length - completed.length) * weight;
	}};}
	
	/**Will return an impact that checks to see if the move will place the mark in a strategic line
	 * 
	 * @param weight The weight each neaby mark grants
	 * @return The impact
	 */
	public static Impact doesPlaceNextToSame(int weight){return new Impact(){@Override public double calculate(Decision decision){
		Field field = decision.getField();
		Field.Move move = decision.getMove();
		
		Board board = field.getBoard(move.getLocation());
		Location board_l = move.getMove().getLocation();
		Mark m = move.getMove().getMark();
		
		int sum = 0;
		Location[] locations = null;
		if(Board.isEdge(board_l)) locations = board_l.get(Direction.getOrthagonal());
		else locations = board_l.get(Direction.values());
		for(Location l : locations) if(board.get(l).equals(m)) sum++;
		
		return sum * weight;
	}};}
	

	/**If the impact is that the move will result in a win, this returns a large number
	 * Otherwise this will return 0;
	 * 
	 * @return An impact object
	 */
	public static Impact doesWinImpact(int weight){return new Impact(){@Override public double calculate(Decision decision){
		Field new_field = decision.getNextField();
		if(new_field.getWinner().equals(decision.getMove().getMove().getMark())) return weight;
		return 0;
	}};}
	
	/**Returns an impact that says the move will give the player a free move to any square.
	 * 
	 * @return An impact object
	 */
	public static Impact doesSelectComplete(int weight){return new Impact(){@Override public double calculate(Decision decision){
		Field new_field = decision.getNextField();
		if(new_field.getSelected() == 0) return weight;
		return 0;
	}};}
	
	/**Calculates the impact the decision will have for this impact
	 * 
	 * @param decision The decision to calculate the impact for
	 * @return A double of the impact
	 */
	public double calculate(Decision decision);

}
