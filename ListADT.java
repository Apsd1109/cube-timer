/*
 * Author: Anda Su
 * Teacher: J. Radulovic
 * Jun. 17, 2019
 * A generic list ADT.
 */

public interface ListADT<E> {
	public void add(E e);			// Insert an element at the end of the list.
	public void remove(int i);		// Remove an element at the given index of the list.
	public void removeLatest();		// Remove the latest element.
	public void removeAll();		// Remove all elements of the list.
	public E get(int i);			// Return an element at the given index of the list.
	public int size(); 				// Return the number of elements in the queue.
	
}
