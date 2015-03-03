package graphs;

/** class represents a coordinate in 2d space as a pair of integers
 * 
 * @author Charlie Street
 *
 */
public class Coord {
	
	private int x;
	private int y;

	/**constructor initialises attributes
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public Coord(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	/** returns the x coordinate
	 * 
	 * @return x
	 */
	public int getX()
	{
		return this.x;
	}
	
	/**returns the y coordinate
	 * 
	 * @return y
	 */
	public int getY()
	{
		return this.y;
	}
	
	/**printing out a representation of the coordinate
	 * 
	 */
	public String toString()
	{
		return "("+this.getX()+", "+this.getY()+")";
	}
	
	
	/**required due to issue in containsKey in the LinkedHashMap class
	 * the optimisation in the containsMethod, in our case, provides a small bug
	 */
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	/** allowing equality checks on coordinates
	 * 
	 */
	public boolean equals(Object o)
	{
		Coord c = (Coord)o;
		return this.getX() == c.getX() && this.getY() == c.getY();
	}
}
