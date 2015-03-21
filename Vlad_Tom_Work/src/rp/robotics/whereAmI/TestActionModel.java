package rp.robotics.whereAmI;

import static org.testng.AssertJUnit.assertEquals;
import lejos.geom.Point;
import lejos.robotics.navigation.Pose;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import rp.robotics.localisation.GridPositionDistribution;
import rp.robotics.localisation.MarkovLocalisationSkeleton;
import rp.robotics.localisation.PerfectSensorModel;
import rp.robotics.localisation.SensorModel;
import rp.robotics.mapping.Heading;
import rp.robotics.mapping.IGridMap;
import rp.robotics.mapping.MapUtils;
import rp.robotics.mapping.RPLineMap;
import rp.robotics.simulation.SimulatedRobot;
import rp.robotics.visualisation.GridMapViewer;

public class TestActionModel {
	GridPositionDistribution m_distribution;
	VladPerfectActionModel actionmodel;
	IGridMap m_gridMap;
	SimulatedRobot m_robot;
	MarkovLocalisationSkeleton ml;
	float junctionSeparation;
	int xJunctions;
	int yJunctions;
	
	int startGridX = 2;
	int startGridY = 1;
	@BeforeClass
	public void setUp()
	{
		RPLineMap lineMap = MapUtils.create2014Map2();

		// Grid map configuration

		// Grid junction numbers
		xJunctions = 10;
		yJunctions = 7;

		junctionSeparation = 30;

		int xInset = 14;
		int yInset = 31;

		m_gridMap = new GridMap(lineMap, xJunctions,
				yJunctions, xInset, yInset, junctionSeparation);
		m_distribution = new GridPositionDistribution(m_gridMap);
		actionmodel=new VladPerfectActionModel();
		

		// this converts the grid position into the underlying continuous
		// coordinate frame
		Point startPoint = m_gridMap.getCoordinatesOfGridPosition(startGridX,
				startGridY);

		// starting heading
		float startTheta = Heading.toDegrees(Heading.PLUS_X);

		Pose startPose = new Pose(startPoint.x, startPoint.y, startTheta);
		m_robot=SimulatedRobot.createSingleNoiseFreeSensorRobot(
				startPose, lineMap);
		ml = new MarkovLocalisationSkeleton(m_robot,
				lineMap, m_gridMap, junctionSeparation);
	}
	@Test
	public void Test()
	{	
		SensorModel sensorModel = new PerfectSensorModel();
		float max=0;
		int maxX=0;
		int maxY=0;
		ml.move(junctionSeparation, Heading.PLUS_X, actionmodel, sensorModel);
		ml.move(junctionSeparation, Heading.PLUS_X, actionmodel, sensorModel);
		ml.move(junctionSeparation, Heading.PLUS_Y, actionmodel, sensorModel);
		ml.move(junctionSeparation, Heading.PLUS_Y, actionmodel, sensorModel);
		ml.move(junctionSeparation, Heading.MINUS_X, actionmodel, sensorModel);
		m_distribution=ml.getDistribution();
		for (int y = 0; y <m_distribution.getGridHeight(); y++)
			for (int x=0;x<m_distribution.getGridWidth();x++)
			{
				if(m_distribution.getProbability(x, y)>max && (!m_distribution.isObstructed(x, y)))
				{
					max=m_distribution.getProbability(x, y);
					maxX=x;
					maxY=y;
				}
			}
		int robotX=(int)((m_robot.getPose().getLocation().x-startGridX)/junctionSeparation);
		int robotY=(int)((m_robot.getPose().getLocation().y-startGridY)/junctionSeparation);
		assertEquals(true,m_distribution.getProbability(robotX, robotY)==m_distribution.getProbability(maxX,maxY));
		
	}
}
