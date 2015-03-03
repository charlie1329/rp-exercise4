package graphs;

import lists_and_maybe.Predicate;

/**class makes it easier to create a predicate without using lambdas 
 * this predicate just checks if a certain node has been reached
 * @author Charlie Street
 *
 */
public class GoalPredicate implements Predicate<Coord> {
	
	private Coord goal;

	/**this constructor initialises the attribute
	 * 
	 * @param goal the goal node
	 */
	public GoalPredicate(Coord goal) {
		this.goal = goal;
	}

	/** returns whether a node equals the goal
	 * 
	 * @param toCheck the node being checked
	 * @return whether it equals the goal
	 */
	public boolean holds(Coord toCheck) {
		return this.goal.equals(toCheck);
	}

}
