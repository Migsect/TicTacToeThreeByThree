package TicTacToe.Agent;

import java.util.ArrayList;
import java.util.List;

import TicTacToe.Board.Board;
import TicTacToe.Board.Field;

public class Decision
{
	private static final double CHILD_IMPACT = 0.5;
	
	private final Field field; // The field that the decision will be made on
	private final Field.Move move; // The move that the decision will make
	List<Impact> impact_calculators = new ArrayList<>();
	
	/**Constructs a decision based upon the move and the field
	 * 
	 * @param field
	 * @param move
	 */
	public Decision(Field field, Field.Move move)
	{
		this.field = field;
		this.move = move;
	}
	/**Generates the decisions that the opponent will have after this decision is made.
	 * 
	 */
	private Decision[] getOpponentDecisions()
	{
		List<Board> boards = new ArrayList<>();
		Board selected_board = field.getBoard();
		if(selected_board == null)
		{
			
		}
		else boards.add(selected_board);
		
		// Looping through the boards to generate moves
		for(Board b : boards)
		{
			
		}
		return null;
	}
	
	/**This calculates the impact that the decision will cause
	 * 
	 * @return A double that represents the impact.  Negative means good, 
	 */
	public double getImpact()
	{
		double sum = 0;
		for(Impact i : impact_calculators) sum += i.calculate(field, move);
		return sum;
	}
	/**Gets the impact of the opponent's decisions
	 * 
	 * @return
	 */
	public double getOpponentDecisionImpact(int steps)
	{
		double sum = 0;
		for(Decision d : this.getOpponentDecisions())sum += d.getImpact(steps);
		return sum;
	}
	/**Gets the impact of the decision n-steps into the future
	 * 
	 * @param steps The number of player-independany moves that will be made to affect the impact
	 * @return The impact
	 */
	public double getImpact(int steps)
	{
		if(steps == 0) return this.getImpact(); // only the impact of the calculators
		else return this.getImpact() * (1 - Decision.CHILD_IMPACT) - this.getOpponentDecisionImpact(steps - 1) * (Decision.CHILD_IMPACT);
	}
}
