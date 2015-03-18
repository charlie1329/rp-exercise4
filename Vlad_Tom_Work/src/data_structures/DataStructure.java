package data_structures;

import lists_and_maybe.*;

/** this interface provides a general structure for which the different data structures
 * for this exercise must follow; this allows me to generalise in search by selecting any data structure
 * @author Charlie Street
 *
 */
public interface DataStructure<A> 
{
	public void insertItem(A e);//list parameter needed for priority queue
	public void insertList (IList<A> toAdd);
	public void removeFront();
	public Maybe<A> getFront();
	public boolean isEmpty();
	public boolean shouldIKeepParent(A a);//although this method doesn't explicitly relate to the data structure, it is related to the search strategy each one employs
										  //it is different for each search strategy so has to be placed here
	
}
