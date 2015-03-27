package data_structures;

import lists_and_maybe.*;

/** this class represents a priority queue which takes a heuristic function which must have a comparable output
 * 
 * @author Charlie Street
 * @param <A>the type of items in the queue
 * @param <B>the return type of the function
 */
public class PriorityQueue<A, B extends Comparable<B>> implements DataStructure<A> {
	
	private Function<A,B> heuristic;
	private IList<A> priQueue;
	
	/**constructor initialises function and sets queue as empty
	 * 
	 * @param heuristic the heuristic determining priorities in the queue
	 */
	public PriorityQueue(Function<A,B> heuristic)
	{
		this.priQueue = new Nil<A>();
		this.heuristic = heuristic;
	}

	/** this method will insert a single item into a priority queue
	 * in this implementation if two items have equal priority the item entering first will be first
	 * this insertion algorithm works in constant time
	 * @param e the item to add
	 */
	@Override
	public void insertItem(A e) 
	{
		boolean found = false;//used to determine when correct position found
		IList<A> tempPri = this.priQueue;//used to work through list without altering attribute
		IList<A> preFixList = new Nil<A>();//the list of items before the position to add
		while(!found)
		{
			if(tempPri.isEmpty())
			{
				found = true;
				this.priQueue = priQueue.append(e);
			}
			else if(heuristic.apply(e).compareTo(heuristic.apply(tempPri.head()))>=0)
			{
				preFixList = preFixList.append(tempPri.head());
				tempPri = tempPri.tail();
			}
			else if(heuristic.apply(e).compareTo(heuristic.apply(tempPri.head()))<0)
			{
				found = true;
				this.priQueue = preFixList.append(new Cons<A>(e,tempPri));
			}
		}	
	}
	
	/**method inserts a list of items into a priority queue in a priority based order
	 * 
	 * @param toAdd the list to add
	 */
	@Override
	public void insertList(IList<A> toAdd) {
		while(!toAdd.isEmpty())
		{
			this.insertItem(toAdd.head());
			toAdd = toAdd.tail();
		}
		
	}

	/**returns the tail of the queue
	 * 
	 */
	@Override
	public void removeFront() {
		this.priQueue = this.priQueue.tail();
		
	}

	/** gets the front of the queue
	 * 
	 * @return the front item in the queue
	 */
	@Override
	public Maybe<A> getFront() {
		if(this.priQueue.isEmpty())
		{
			return new Nothing<A>();
		}
		else
		{
			return new Just<A>(this.priQueue.head());
		}
	}

	/** wrapper method for isEmpty in list
	 * 
	 * @return whether the priority queue is empty or not
	 */
	@Override
	public boolean isEmpty() {
		return this.priQueue.isEmpty();
	}
	
	/**wrapper method to allow a priority queue to be printed for testing purposes
	 * 
	 * @return the priority queue
	 */
	public String toString()
	{
		return this.priQueue.toString();
	}
	
	/**answers whether parent should be changed by whether an item is already in the list
	 * 
	 */
	public boolean shouldIKeepParent(A a)
	{
		return this.priQueue.has(a);
	}
	

}
