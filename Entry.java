
//************************************************************
       //*                                                          *
       //*                                                          *
       //*        Hicham Mazouzi  300145076                         *
       //*                                                          *
       //************************************************************
       



/**
 * @author Lachlan
 */
public class Entry <K extends Comparable, V> {
	K key;
	V value;

	/**
	 * Returns the key stored in this entry.
	 * @return the entry's key
	 */
	public K getKey() {
		return key;
	} /* getKey */


	/**
	 * Returns the value stored in this entry.
	 * @return the entry's value
	 */
	public V getValue() {
		return value;
	} /* getValue */


	public Entry( K k, V v ) {
		key   = k;
		value = v;
	}


}
