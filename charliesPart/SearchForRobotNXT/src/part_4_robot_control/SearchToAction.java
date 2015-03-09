package part_4_robot_control;

import configandsetup.GeoffSetUp;
import data_structures.*;
import ActionsForRobot.Action;
import ActionsForRobot.MovementChecker;
import ActionsForRobot.SoundPrompt;
import aStarFunctions.Cost;
import lejos.robotics.navigation.Pose;
import graphs.Coord;
import graphs.GoalPredicate;
import graphs.Graph;
import graphs.Node;
import lists_and_maybe.*;

/**this class will carry out search and convert it into a list of runnable actions for the robot
 * 
 * @author charlie street
 *
 */
public class SearchToAction
{
	private GeoffSetUp geoff;
	private Node<Coord> start;
	private Node<Coord> intermediate;
	private Node<Coord> goal;
	private Pose pose;
	private Graph graph;

	/**constructor initialises attributes
	 * 
	 * @param grid the grid map being used
	 * @param start the start node
	 * @param intermediate the checkpoint node
	 * @param goal the goal node
	 * @param geoff the robot being used
	 * @param pose the pose of the robot
	 */
	public SearchToAction(Node<Coord> start, Node<Coord> intermediate, Node<Coord> goal, GeoffSetUp geoff, Pose pose, Graph graph) 
	{
		this.geoff = geoff;
		this.start = start;
		this.intermediate = intermediate;
		this.goal = goal;
		this.pose = pose;
		this.graph = graph;
	}
	
	/**method will return a list of actions for the robot to adhere to
	 * 
	 */
	private IList<MovementChecker> getListOfActions(Node<Coord> start, Node<Coord> goal)
	{
		Cost cost = new Cost(this.graph,start,goal);
		PriorityQueue<Node<Coord>,Integer> priQueue = new PriorityQueue<Node<Coord>,Integer>(cost);
		GoalPredicate interGoal = new GoalPredicate(goal.getContent());
		Maybe<IList<Node<Coord>>> Path = this.graph.findPathFrom(start, interGoal, priQueue);
		IList<MovementChecker> actions = new Nil<MovementChecker>();
		if(Path.size() != 0)//if not nothing
		{
			IList<Node<Coord>> PathNotMaybe = Path.fromMaybe();
			for(int i = 0; i < PathNotMaybe.size()+1; i++)//+1 to account for nil
			{
				Node<Coord> current = PathNotMaybe.head();
				Node<Coord> next = PathNotMaybe.tail().head();
				if(next.getContent().getX() - current.getContent().getX() == 1)//right
				{
					actions = actions.append(new Action(geoff,pose,Action.RIGHT));
					System.out.println("RIGHT");
				}
				else if(next.getContent().getX() - current.getContent().getX() == -1)//left
				{
					actions = actions.append(new Action(geoff,pose,Action.LEFT));
					System.out.println("LEFT");
				}
				else if(next.getContent().getY() - current.getContent().getY() == 1)//up
				{
					actions = actions.append(new Action(geoff,pose,Action.UP));
					System.out.println("UP");
				}
				else if(next.getContent().getY() - current.getContent().getY() == -1)//down
				{
					actions = actions.append(new Action(geoff,pose,Action.DOWN));
					System.out.println("DOWN");
				}
				PathNotMaybe = PathNotMaybe.tail();
			}
			actions = actions.append(new SoundPrompt());//adding sound once destination reached
		}
		return actions;
		
	}
	
	/**method will return the complete set of instructions for the robot
	 * 
	 * @return the list of instructions
	 */
	public IList<MovementChecker> getCompleteActionSet()
	{
		return this.getListOfActions(this.start, this.intermediate).append(this.getListOfActions(this.intermediate,this.goal));
	}
}
