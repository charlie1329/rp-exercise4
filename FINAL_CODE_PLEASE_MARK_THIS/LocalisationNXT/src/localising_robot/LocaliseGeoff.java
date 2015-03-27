package localising_robot;



import java.util.ArrayList;
import java.util.Random;

import ActionsForRobot.LocaliseAction;
import ActionsForRobot.MovementChecker;
import configandsetup.GeoffSetUp;
import lejos.nxt.Button;
import lejos.robotics.navigation.Pose;
import lejos.util.Delay;
import lists_and_maybe.*;
import part2.JunctionBehavior;
import part_4_robot_control.SearchToAction;
import rp.robotics.localisation.GridPositionDistribution;
import rp.robotics.mapping.Heading;
import rp.robotics.mapping.IGridMap;
import wrapper.LocaliseMe;
import wrapper.LocationProbability;
import graphs.Coord;
import graphs.Graph;
import graphs.Node;

/**this class will deal with all localisation logistics with respect to the robot
 * 
 * @author charlie street
 *
 */
public class LocaliseGeoff {
	
	private LocaliseMe localiser;
	private Coord firstDestination;
	private Coord secondDestination;
	private final double threshold;
	private boolean haveISetPath;
	private Heading head;
	private Pose pose;
	private GeoffSetUp geoff;
	private Graph graph;
	private SearchToAction search;
	private final float ERROR;
	
	/**this constructor just initiliases the fields
	 * 
	 * @param grid the grid map being used
	 * @param firstDestination the first destination being given
	 * @param secondDestination the second destination being given
	 */
	public LocaliseGeoff(IGridMap grid, Coord firstDestination, Coord secondDestination, GeoffSetUp geoff) 
	{
		this.geoff = geoff;
		this.graph = new Graph(grid);
		this.localiser = new LocaliseMe(grid);
		this.firstDestination = firstDestination;
		this.secondDestination = secondDestination;//determines whether path is set
		this.threshold = 0.95;//when robot is localised
		this.haveISetPath = false;
		this.head = Heading.PLUS_Y;
		System.out.println(Math.round(grid.rangeToObstacleFromGridPosition(3, 0,Heading.toDegrees(Heading.PLUS_X))));
		this.ERROR = 1.1f;
	}
	
	/**this method will check if the robot has localised yet, if it has then it will carry out a search
	 * if it hasn't then it will keep adding random move to the robot
	 */
	public void checkIfLocalised(JunctionBehavior junction)
	{
		LocationProbability location = this.localiser.itsAllAboutTheConfidence();
		if(location.getProbability() < this.threshold)
		{			
			this.produceRandomMove(junction);
		}
		else if(this.haveISetPath == false)//if localised
		{
			System.out.println("Start: " + location.convertToCoord());
			Coord startPoint = location.convertToCoord();
			this.pose = new Pose(location.getX(),location.getY(),Heading.toDegrees(this.head)); 
			Node<Coord> startNode = this.graph.nodeWith(startPoint);
			Node<Coord> interNode = this.graph.nodeWith(this.firstDestination);
			Node<Coord> goalNode = this.graph.nodeWith(this.secondDestination);
			this.search = new SearchToAction(startNode, interNode, goalNode, geoff,this.pose, this.graph);
			IList<MovementChecker> path = search.getCompleteActionSet();
			junction.setPath(path);
			this.haveISetPath = true;
		}
	}
	
	/**this method will return the pose of the robot for use in other classes
	 * 
	 * @return the pose of the robot
	 */
	public Pose getPose()
	{
		return this.pose;
	}
	
	public void produceRandomMove(JunctionBehavior junction)
	{
		Heading previousDirection = this.head;
		
		this.head = this.createRandomHeading(this.localiser.getDistribution());
		LocaliseAction action = new LocaliseAction(previousDirection, this.head, this.localiser, this.geoff, this);
		IList<MovementChecker> singlePath = new Cons<MovementChecker>(action, new Nil<MovementChecker>());
		junction.setPath(singlePath);
		
		//System.out.println("I am here");
	}
	
	/**this method will produce a random heading for an action
	 * 
	 * @param distribution the grid position distribution
	 * @return the heading of the new move
	 */
	public Heading createRandomHeading(GridPositionDistribution distribution){
		LocationProbability mostConfident = localiser.itsAllAboutTheConfidence();
		
		System.out.println(mostConfident);
		while(true){
			int n = (int)(System.currentTimeMillis() % 4);
				
			switch(n){
				case 0:
					if (distribution
							.getGridMap()
							.rangeToObstacleFromGridPosition(
									mostConfident.getX(), mostConfident.getY(), Heading.toDegrees(Heading.PLUS_X)
							) > 32){
						System.out.println("X");
						return Heading.PLUS_X;
					}
					break;
				case 1:
					if (distribution
							.getGridMap()
							.rangeToObstacleFromGridPosition(
									mostConfident.getX(), mostConfident.getY(), Heading.toDegrees(Heading.PLUS_Y)
							) > 32){
						return Heading.PLUS_Y;
					}
					break;
				case 2:
					if (distribution
							.getGridMap()
							.rangeToObstacleFromGridPosition(
									mostConfident.getX(), mostConfident.getY(), Heading.toDegrees(Heading.MINUS_X)
							) > 32){
						return Heading.MINUS_X;
					}
					break;
				default:					
					if (distribution
							.getGridMap()
							.rangeToObstacleFromGridPosition(
									mostConfident.getX(), mostConfident.getY(), Heading.toDegrees(Heading.MINUS_Y)
							) > 32){
						return Heading.MINUS_Y;
					}
					break;
			}
		}
	}
	
	
	
	
	
	/**method returns the graph 
	 * 
	 * @return the graph
	 */
	public Graph getGraph()
	{
		return this.graph;
	}
	
	/**returns the searchToAction object
	 * 
	 * @return the search to action object
	 */
	public SearchToAction getSearch()
	{
		return this.search;
	}
	
	/**this method will allow the heading to be reset from different classes
	 * 
	 * @param head the new value of head
	 */
	public void setHead(Heading head)
	{
		this.head = head;
	}
	
	
}
