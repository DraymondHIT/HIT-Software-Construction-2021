package interval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * An implementation of IntervalSet.
 * 
 */
public class CommonIntervalSet<L> implements IntervalSet<L>{
	//fields
	private final List<Interval<L>> intervals = new ArrayList<>();
	private final Set<L> labels = new HashSet<>();
	
	// Abstraction function:
    //   labels表示所有intervals的标签名的集合
	//   intervals表示所有interval的集合
    // Representation invariant:
    //   interval的label必须互不相同
	//   interval中的start和end必须非负
    //   intervals里所有的label必须出现在labels中
    // Safety from rep exposure:
    //   All fields are private final,
    //   The Set and Map objects in the rep are made immutable by unmodifiable wrappers.
	
	//checkRep
	private void checkRep() {
		Set<L> tempLabels = new HashSet<>();
		Iterator<Interval<L>> iter = intervals.iterator();
		while(iter.hasNext()) {
			Interval<L> interval = iter.next();
			assert interval.getStart() >= 0 && interval.getEnd() >= 0;
			if(tempLabels.contains(interval.getLabel())) {
				assert false;
			}else {
				tempLabels.add(interval.getLabel());
			}
		}
		assert tempLabels.equals(labels);
	}
	
	@Override
	public boolean insert(long start, long end, L label) throws Exception {
		boolean flag;
		if(labels.contains(label)) {
			flag = false;
		}else {
			intervals.add(new Interval<>(start, end, label));
		    labels.add(label);
		    flag = true;
		}
		checkRep();
		return flag;
	}

	@Override
	public Set<L> labels() {
		return Collections.unmodifiableSet(labels);
	}

	@Override
	public boolean remove(L label) {
		Iterator<Interval<L>> iter = intervals.iterator();
		while(iter.hasNext()) {
			Interval<L> interval = iter.next();
			if(interval.getLabel().equals(label)) {
				iter.remove();
				labels.remove(label);
				checkRep();
				return true;
			}
		}
		checkRep();
		return false;
	}

	@Override
	public long getStart(L label) throws Exception {
		Iterator<Interval<L>> iter = intervals.iterator();
		while(iter.hasNext()) {
			Interval<L> interval = iter.next();
			if(interval.getLabel().equals(label)) {
				checkRep();
				return interval.getStart();
			}
		}
		checkRep();
		throw new Exception("Label为"+label+"的interval不存在！");
	}

	@Override
	public long getEnd(L label) throws Exception {
		Iterator<Interval<L>> iter = intervals.iterator();
		while(iter.hasNext()) {
			Interval<L> interval = iter.next();
			if(interval.getLabel().equals(label)) {
				checkRep();
				return interval.getEnd();
			}
		}
		checkRep();
		throw new Exception("Label为"+label+"的interval不存在！");
	}
	
	@Override
	public List<Interval<L>> all() {
		intervals.sort(new Comparator<Interval<L>>() {
			@Override
			public int compare(Interval<L> o1, Interval<L> o2) {
				return (int) (o1.getStart()-o2.getStart());
			}
		});
		checkRep();
		return Collections.unmodifiableList(intervals);
	}
	
	@Override
	public String toString() {
		String str = "{";
		intervals.sort(new Comparator<Interval<L>>() {
			@Override
			public int compare(Interval<L> o1, Interval<L> o2) {
				return (int) (o1.getStart()-o2.getStart());
			}			
		});
		Iterator<Interval<L>> iter = intervals.iterator();
		while(iter.hasNext()) {
			Interval<L> interval = iter.next();
			str += interval.toString() + ",";
		}
		if(str.endsWith(",")) {
			str = str.substring(0, str.length()-1);
		}
		str += "}";
		checkRep();
		return str;
	}
	
}

