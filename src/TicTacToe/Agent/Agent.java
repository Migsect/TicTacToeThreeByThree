package TicTacToe.Agent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import TicTacToe.Board.Board.Mark;
import TicTacToe.Board.Field;

public class Agent
{
	private static final double EPSILON = 0.01;
	
	private final Mark team; // The team of the agent
	private final int depth; // determines how far the agent will look into the decissions
	private List<Impact> decision_impacts = new ArrayList<>(); // The impact calculators
	
	public Agent(Mark team, int depth)
	{
		this.team = team;
		this.depth = depth;
	}
	
	public Mark getTeam(){return this.team;}
	public Agent addImpact(Impact impact){this.decision_impacts.add(impact); return this;}
	
	public Field.Move decide(Field current_field)
	{
		// Getting the decissions currently open to the agent
		Decision[] decisions = Decision.getDecisions(current_field);
		double best_impact = 0;
		List<Decision> best = new ArrayList<>();
		for(Decision d : decisions)
		{
			double impact = d.getImpact(this.depth); 
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
