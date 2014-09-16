import java.util.Comparator;

/**
 * CS2103T (AY2014/15 Semester 1) CE2: TextBuddy++ Group: T17-3J
 * 
 * Implements comparator that ignore case during sort.
 * 
 * @author WeiCheng (A0111815R)
 *
 */
public class SortIgnoreCase implements Comparator<Object> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object o1, Object o2) {
		String s1 = (String) o1;
		String s2 = (String) o2;
		return s1.toLowerCase().compareTo(s2.toLowerCase());
	}
}