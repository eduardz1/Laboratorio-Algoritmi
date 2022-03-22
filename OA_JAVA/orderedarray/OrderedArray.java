
package orderedarray;

import java.util.ArrayList;
import java.util.Comparator;

// TODO: cambiare i tab in soft-tab da 2 spazi

/**
 * This class maintains the elements ordered according
 * to a criteria specified at creation time
 * @param T type of the element in the array
 */
public class OrderedArray <T> {
	ArrayList <T> array = null;
	Comparator <? super T> comparator = null;
	
	/**
	 * Creates an empty OrderedArray
	 * Accepts a comparator as input implementing
	 * a previous relation between T elements
	 * @param comparator: comparator implementing previous relation between T elements
	 * @throws OrderedArrayException throws an Exception when comparator is null
	 */
	public OrderedArray(Comparator <? super T> comparator) throws OrderedArrayException {
		if (comparator == null)
			throw new OrderedArrayException("OrderedArray Constructor:" + " parameter comparator cannot be null");
		this.array = new ArrayList();
		this.comparator = comparator;
	}
	
	/**
	 * @return TRUE if this.ordered array is empty
	 */
	public boolean isEmpty() { // cannot be null because of how the constructor is declared
		return this.array.isEmpty();
	}
	
	/**
	 * @return number of elements in this.array
	 */
	public int size() {
		return this.array.size();
	}
	
	/**
	 * @param i index of the element requested
	 * @return element at i position in the ArrayList
	 */
	public T getElementAt(int i) throws OrderedArrayException {
		if((i < 0) || (i >= this.array.size())
			throw new OrderedArrayException("OrderedArray getElementAt" + " index of array out of bound");
		return this.array.get(i);
	}
}
