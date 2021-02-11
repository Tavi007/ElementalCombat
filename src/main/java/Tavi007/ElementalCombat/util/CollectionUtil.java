package Tavi007.ElementalCombat.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

public class CollectionUtil {

	public static <T> T getNext(Collection<T> collection, T target) {
		return getNext(collection, target, false);
	}

	public static <T> T getNext(Collection<T> collection, T target, final boolean circular) {
		if (collection == null) {
			return null;
		}
		Iterator<T> itr = collection.iterator();
		T first = null;
		boolean firstItr = true;
		while (itr.hasNext()) {
			T t = itr.next();
			if (circular && firstItr) {
				first = t;
				firstItr = false;
			}
			if (Objects.equals(t, target)) {
				return itr.hasNext() ? itr.next() : circular ? first : null;
			}
		}
		return null;
	}

	public static <T> T getPrevious(Collection<T> collection, T target) {
		return getPrevious(collection, target, false);
	}

	public static <T> T getPrevious(Collection<T> collection, T target, final boolean circular) {
		if (collection == null) {
			return null;
		}
		Iterator<T> itr = collection.iterator();
		T previous = null;
		boolean firstItr = true;
		while (itr.hasNext()) {
			T t = itr.next();
			if (Objects.equals(t, target)) {
				if (firstItr && circular) {
					for (; itr.hasNext(); t = itr.next())
						;
					return t;
				} else {
					return previous;
				}
			}
			previous = t;
			firstItr = false;
		}
		return null;
	}
}
