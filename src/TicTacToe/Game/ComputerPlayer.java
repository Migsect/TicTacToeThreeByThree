package TicTacToe.Game;

import TicTacToe.Agent.Agent;
import TicTacToe.Board.Board.Mark;
import TicTacToe.Board.Field;
import TicTacToe.Board.Field.Move;

public class ComputerPlayer implements Player
{
	private final Mark team;
	private final Agent agent;
	private final Field current_field;
	
	public ComputerPlayer(Field field, Agent agent, Mark team)
	{
		this.current_field = field;
		this.agent = agent;
		this.team = team;
	}
	
	@Override public Move getMove(){return agent.decide(current_field);}

	@Override public Mark getTeam(){return team;}
	
}
