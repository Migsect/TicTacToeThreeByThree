package TicTacToe.Agent;

import java.util.ArrayList;
import java.util.List;

import TicTacToe.Board.Board;
import TicTacToe.Board.Board.Location;
import TicTacToe.Board.Board.Mark;
import TicTacToe.Board.Field;

public class Decision
{
	private static final double CHILD_IMPACT = 0.5;
	
	/**Gets all the decisions that are possible for the current player of the field
	 * 
	 * @param field
	 * @return
	 */
	public static Decision[] getDecisions(Field field)
	{
		// Creating the decissions
		List<Decision> decisions = new ArrayList<>();
		
		// Getting the boards that can have moves be made on.
		List<Location> boards = new ArrayList<>();
		if(field.getSelected() == 0) for(Location l : field.getIncompleteBoardLocations()) boards.add(l);
		else boards.add(new Location(field.getSelectedColumn(), field.getSelectedRow()));
		
		for(Location board_l : boards)
		{
			// Getting the board
			Board board = field.getBoard(board_l);
			Mark current_turn = field.getTurn();
			// Generating decisions for all the empty locations on the board
			for(Location empty_l : board.getEmptyLocations())
			{
				Board.Move b_move = new Board.Move(current_turn, empty_l);
				Field.Move f_move = new Field.Move(b_move, board_l);
				Decision descision = new Decision(field, f_move);
				decisions.add(descision);
			}
		}
		
		// Returning the decissions as an array
		return decisions.toArray(new Decision[decisions.size()]);
	}
	
	private final Field field; // The field that the decision will be made on
	private final Field next_field; // The field state that will be made after the decision has been made
	private final Field.Move move; // The move that the decision will make, this is not reprented in the referred field
	List<Impact> impact_calculators = new ArrayList<>(); // A lis
	
	/**Constructs a decision based upon the move and the field
	 * 
	 * @param field
	 * @param move
	 */
	public Decision(Field field, Field.Move move)
	{
		this.field = field;
		this.move = move;
		
		// getting the next field from a clone of the current field
		this.next_field = this.field.clone().makeMove(move);
	}
	/**Generates the decisions that the opponent will have after this decision is made.
	 * 
	 * @return an array of the decisions an opponent can make after this move was made
	 */
	private Decision[] getOpponentDecisions(){return Decision.getDecisions(this.next_field);}
	
	/**This calculates the impact that the decision will cause
	 * 
	 * @return A double that represents the impact.  Negative means good, 
	 */
	public double getImpact()
	{
		double sum = 0;
		for(Impact i : impact_calculators) sum += i.calculate(this);
		return sum;
	}
	/**Gets the impact of the opponent's decisions
	 * This is an average of the impact of all the devisions an opponent can make
	 * If the opponent cannot make any decisions, it returns 0;
	 * 
	 * @return
	 */
	public double getOpponentDecisionImpact(int steps)
	{
		double sum = 0;
		
		Decision[] opponent_decisions = this.getOpponentDecisions();
		if(opponent_decisions.length == 0) return 0;
		for(Decision d : opponent_decisions) sum += d.getImpact(steps);
		
		return sum / opponent_decisions.length;
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
	
	/**Returns the field that this decision will make a move on
	 * 
	 * @return The field
	 */
	public Field getField(){return this.field;}
	/**Returns the next field if this decision would be made
	 * 
	 * @return A field if the move this decission is deciding on
	 */
	public Field getNextField(){return this.next_field;}
	/**Returns the move that this decission is deciding on
	 * 
	 * @return
	 */
	public Field.Move getMove(){return this.move;}
}
