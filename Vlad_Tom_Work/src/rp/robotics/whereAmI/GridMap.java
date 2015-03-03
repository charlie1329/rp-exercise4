package rp.robotics.whereAmI;

import rp.robotics.mapping.RPLineMap;
import lejos.geom.Line;
import lejos.geom.Point;
import lejos.robotics.navigation.Pose;

public class GridMap implements rp.robotics.mapping.IGridMap{

	private RPLineMap linemap;
	private final float xStart;
	private final float yStart;
	private final float cellSize;
	private final int gridXSize;
	private final int gridYSize;
	/**
	 * 
	 * @param linemap
	 * @param _xStart
	 * @param _yStart
	 * @param _cellSize
	 */
	public GridMap(RPLineMap linemap, int _gridXSize, int _gridYSize, float _xStart, float _yStart, float _cellSize)
	{
		this.linemap=linemap;
		this.xStart=_xStart;
		this.yStart=_yStart;
		this.cellSize=_cellSize;
		this.gridXSize=_gridXSize;
		this.gridYSize=_gridYSize;
	}
	@Override
	/**
	 * Gets the number of X position in the map
	 */
	public int getXSize() {
		return this.gridXSize;
	}

	@Override
	/**
	 * Gets the number of Y position in the map
	 */
	public int getYSize() {
		return this.gridYSize;
	}

	@Override
	/**
	 * Checks if the point (x,y) is in the map
	 */
	public boolean isValidGridPosition(int _x, int _y) {
		return _x<this.gridXSize && _y<this.gridYSize && _x>=0 && _y>=0;
	}

	@Override
	/**
	 * Checks if the point (_x,_y) is within an obstacle or not
	 */
	public boolean isObstructed(int _x, int _y) {
		Point point=this.getCoordinatesOfGridPosition(_x, _y);
		return !this.linemap.inside(point);
	}
	/**
	 * Gets the position in the grid into coordinates in the map(in cm)
	 */
	@Override
	public Point getCoordinatesOfGridPosition(int _x, int _y) {
		return new Point(this.xStart+_x*this.cellSize,this.yStart+_y*this.cellSize);
	}

	@Override
	/**
	 * Checks if there is a transition between 2 points (no obstacle between two connected points
	 */
	public boolean isValidTransition(int _x1, int _y1, int _x2, int _y2) {
		float xCoord1=this.xStart+_x1*this.cellSize;
		float xCoord2=this.xStart+_x2*this.cellSize;
		float yCoord1=this.yStart+_y1*this.cellSize;
		float yCoord2=this.yStart+_y2*this.cellSize;

		if(!this.isObstructed(_x1, _y1) && !this.isObstructed(_x2, _y2) && this.isValidGridPosition(_x1, _y1) && this.isValidGridPosition(_x2, _y2))
		{
			if(_x1==_x2)
			{
				for(float j=Math.min(yCoord1, yCoord2);j<=Math.max(yCoord1, yCoord2);j++)
				{
					if(!this.linemap.inside(new Point(xCoord1,j)))
						return false;
				}
				return true;
			}
			else if(_y1==_y2)
			{
				{
					for(float j=Math.min(xCoord1, xCoord2);j<=Math.max(xCoord1, xCoord2);j++)
					{
						if(!this.linemap.inside(new Point(j,yCoord1)))
							return false;
					}
				}
				return true;
			}
			else return true;
		}
		else return false;
		
	}

	@Override
	/**
	 * Checks the distance from the point to the closest obstacle
	 */
	public float rangeToObstacleFromGridPosition(int _x, int _y, float _heading) {
		return this.linemap.range(new Pose(this.xStart+_x*this.cellSize,this.yStart+_y*this.cellSize,_heading));
	}

}
