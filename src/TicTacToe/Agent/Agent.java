package TicTacToe.Agent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import TicTacToe.Board.Field;

public class Agent
{
	private static final double EPSILON = 0.01;
	
	private final int depth; // determines how far the agent will look into the decissions
	private List<Impact> decision_impacts = new ArrayList<>(); // The impact calculators
	
	public Agent(int depth)
	{
		this.depth = depth;
	}
	
	public Agent addImpact(Impact impact){this.decision_impacts.add(impact); return this;}
	
	public Field.Move decide(Field current_field)
	{
		// Getting the decissions currently open to the agent
		Decision[] decisions = Decision.getDecisions(current_field, this.decision_impacts);
		double[] impacts = new double[decisions.length];
		for(int i = 0 ; i < impacts.length; i++) impacts[i] = decisions[i].getImpact(this.depth);
		// Printing out the decisions
		System.out.println("Possible Moves and Weights:");
		for(int i = 0 ; i < impacts.length; i++)
		{
			System.out.println("  " + impacts[i] + " : " + decisions[i].getMove().toString());
		}
		
		// Calculating the best move
		double best_impact = -Double.MAX_VALUE;
		List<Decision> best = new ArrayList<>();
		for(int i = 0 ; i < impacts.length; i++)
		{
			double impact = impacts[i];
			Decision d = decisions[i];
			if(Math.abs(impact - best_impact) < Agent.EPSILON) best.add(d);
			else if(impact > best_impact) // new best decision
			{
				best_impact = impact;
				best.clear();
				best.add(d);
			}
			// end loop
		}
		
		// If there isn't even a move, we return null
		if(best.isEmpty()) return null;
		// Randomly selecting the decision if they have equal impact
		Random rand = new Random();
		return best.get(rand.nextInt(best.size())).getMove();
	}
}
