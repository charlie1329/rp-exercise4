package graphs;


import java.util.Collection;
import rp.*;
import lists_and_maybe.*;

/** this class represents a node on a graph which contains it's successor nodes
 * 
 * @author Charlie Street
 *
 */
public class Node<A> {
	
	private A content;
	private Collection<Node<A>> successors;
	private Maybe<Node<A>> parent;//used to help find path
	private boolean blocked;//used to determine whether a route is blocked for the robot

	/**constructor initialises attributes
	 * 
	 * @param content the content of the node
	 * @param successors the successors of the node
	 */
	public Node(A content) 
	{
		this.content = content;
		this.successors = new SimpleSet<Node<A>>();
		this.parent = new Nothing<Node<A>>();//using maybe type to prevent null pointers being thrown about
		this.blocked = false;
	}
	
	/**allows the addition of successors
	 * 
	 * @param node the node to be added
	 */
	public void addSuccessor(Node<A> node)
	{
		this.successors.add(node);
	}
	
	/** method returns the content inside the node
	 * 
	 * @return the content of the node
	 */
	public A getContent()
	{
		return this.content;
	}
	
	/**returns the successors of the node in question
	 * returns them in the form of a collection
	 * @return the successors of that node
	 */
	public Collection<Node<A>> getSuccessors()
	{
		return this.successors;
	}
	
	/**returns the successor list in terms of an IList so it can work with the already implemented data structures
	 * i am doing this as all of the data structures are already written using the IList interface
	 * @return the list of successors
	 */
	public IList<Node<A>> getSuccessorsList()
	{
		IList<Node<A>> successor = new Nil<Node<A>>();
		for(Node<A> n : this.successors)
		{
			successor = successor.append(n);
		}
		return successor;
	}
	
	/**overwriting default toString method for testing purposes only
	 * 
	 */
	public String toString()
	{
		return "Node("+this.content+")";
	}
	
	/**checking for the content of a node to be equal to a parameter
	 * 
	 * @param a the contents to be compared
	 * @return whether the node contents equals the passed in value
	 */
	public boolean contentEquals(A a)
	{
		return this.content.equals(a);
	}
	
	/**method returns the currentParent of a node (may change)
	 * 
	 * @return the currentParent
	 */
	public Maybe<Node<A>> getParent()
	{
		return this.parent;
	}
	
	/** sets the new current parent of the node
	 * just can be set within method as there will only be a valid value passed to this method
	 * @param currentParent the currentParent of the node
	 */
	public void setParent(Maybe<Node<A>> currentParent)
	{
		this.parent = currentParent;
	}
	
	/**this method allows us to change the status of whether the node is part of a blocked edge
	 * 
	 * @param blocked the new value of blocked
	 */
	public void setBlocked(boolean blocked)
	{
		this.blocked = blocked;
	}
	
	/**method returns whether or not the node is part of a blocked edge
	 * 
	 * @return the blocked status
	 */
	public boolean getBlocked()
	{
		return this.blocked;
	}
}
