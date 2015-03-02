package rp.robotics.whereAmI;

import rp.robotics.mapping.RPLineMap;
import lejos.geom.Point;
import lejos.robotics.navigation.Pose;

public class GridMap implements rp.robotics.mapping.IGridMap{

	private RPLineMap linemap;
	/**
	 * Creates a grid map which takes a linemap
	 * @param linemap The linemap contains the bounding rectangle of the map and the lines
	 */
	public GridMap(RPLineMap linemap)
	{
		this.linemap=linemap;
	}
	@Override
	/**
	 * Gets the width of the map
	 */
	public int getXSize() {
		return (int)this.linemap.getBoundingRect().getWidth();
	}

	@Override
	/**
	 * Gets the height of the map
	 */
	public int getYSize() {
		return (int)this.linemap.getBoundingRect().getHeight();
	}

	@Override
	/**
	 * Checks if the point (x,y) is in the map
	 */
	public boolean isValidGridPosition(int _x, int _y) {
		return this.linemap.inside(new Point(_x,_y));
	}

	@Override
	/**
	 * Checks if the point (_x,_y) is within an obstacle or not
	 */
	public boolean isObstructed(int _x, int _y) {
		for(int i=0;i<this.linemap.getLines().length;i++)
			if(this.linemap.getLines()[i].contains(_x, _y))
				return false;
		return true;
	}

	@Override
	public Point getCoordinatesOfGridPosition(int _x, int _y) {
		return new Point(_x,_y);
	}

	@Override
	/**
	 * Checks if there is a transition between 2 points
	 */
	public boolean isValidTransition(int _x1, int _y1, int _x2, int _y2) {
		return isObstructed(_x1,_y1) && isObstructed(_x2,_y2);
	}

	@Override
	/**
	 * Checks the distance from the point to the closest obstacle
	 */
	public float rangeToObstacleFromGridPosition(int _x, int _y, float _heading) {
		return this.linemap.range(new Pose(_x,_y,_heading));
	}

}
