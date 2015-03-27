package part2;

import java.util.ArrayList;

import lejos.nxt.Button;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import configandsetup.GeoffSetUp;
import GenericListeners.ExitListener;
import Part1.*;

public class GridFollower {
	
	private Arbitrator arbitrator;
	private DriveForwardBehavior forward;
	private TurnBehavior turn;
	private ArrayList<Direction> path;
	private JunctionBehavior junction;//this seems unnecessary but prevents some unwanted behaviour with junctionBehavior
	private CalibrateBehavior calibrate;
	
		
	public GridFollower(GeoffSetUp geoff)
	{
		this.path = new ArrayList<Direction>();
		this.path.add(Direction.RIGHT);//scripting robot
		this.path.add(Direction.LEFT);
		this.path.add(Direction.RIGHT);
		this.path.add(Direction.LEFT);
		this.path.add(Direction.RIGHT);
		this.path.add(Direction.RIGHT);
		this.path.add(Direction.RIGHT);
		this.path.add(Direction.LEFT);
		this.path.add(Direction.RIGHT);
		this.path.add(Direction.LEFT);
		geoff.setTravelSpeed(15);
		geoff.setRotateSpeed(80);
		this.forward = new DriveForwardBehavior(geoff);
		this.turn = new TurnBehavior(geoff);
		//this.junction = new JunctionBehavior(geoff,this.path);
		//this.calibrate = new CalibrateBehavior(geoff,this.junction);
		Button.ESCAPE.addButtonListener(new ExitListener(geoff));
		this.arbitrator = new Arbitrator(new Behavior[]
			{
				forward,turn,junction,calibrate
			});	
	}
		
	public static void main(String[] args)
	{
		GeoffSetUp geoff = new GeoffSetUp();
		GridFollower grid = new GridFollower(geoff);
		grid.Run();
	}
		
		
	public void Run()
	{
		Button.waitForAnyPress();
		arbitrator.start();
	}

}
