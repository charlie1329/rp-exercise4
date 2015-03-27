package ActionsForRobot;

/**this interface adds something to runnable in the form of checking if an action is related to the robots movement
 * 
 * @author Charlie Street
 *
 */
public interface MovementChecker extends Runnable 
{
	public boolean isMovement();
	public boolean isLocaliser();
}
