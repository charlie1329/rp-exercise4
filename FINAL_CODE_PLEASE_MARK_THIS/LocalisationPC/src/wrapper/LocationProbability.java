package wrapper;

import graphs.Coord;

/**
 * Represents the probability of being in a given location.
 */
public class LocationProbability {
    // x and y coordinate
    private final int x, y;

    // probability of being in the coordinate x, y
    private final float probability;

    /**
     * Creates a location probability instance with the given location and probability.
     * @param x The x-coordinate of the location.
     * @param y The y-coordinate of the location.
     * @param probability The probability of being at the given location.
     */
    public LocationProbability(int x, int y, float probability){
        this.x = x;
        this.y = y;
        this.probability = probability;
    }

    /**
     * Gets the x-coordinate of the location.
     * @return The x-coordinate of the location.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the location.
     * @return The y-coordinate of the location.
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the probability of being at the location.
     * @return The probability of being at the location.
     */
    public float getProbability() {
        return probability;
    }

    @Override
    public String toString() {
        return "p({" + x + "," +  y + "}): " + Math.round(100 * probability) + "%";
    }
    
    /**this method converts a position to a coordinate for convenience
     * 
     * @return a coordinate representing a position
     */
    public Coord convertToCoord()
    {
    	return new Coord(this.getX(), this.getY());
    }
}