
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
	public OrderedArray(Comparator <? super T> comparator) 
	throws OrderedArrayException {
		if (comparator == null)
			throw new OrderedArrayException("OrderedArray Constructor:" + " parameter comparator cannot be null");
		this.array = new ArrayList();
		this.comparator = comparator;
	}
}
