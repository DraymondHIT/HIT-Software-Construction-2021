package interval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PeriodIntervalSetImpl<L> implements PeriodIntervalSet<L>{

	//fields
	private final int period;
	private final int times;
	private List<Set<L>> records = new ArrayList<>();
	
	// Abstraction function:
    //   period表示周期
	//   times表示每天的时间段数
	//   records表示某个时间段事件的集合
    // Representation invariant:
    //   
    // Safety from rep exposure:
    //   All fields are private final,
    //   The Set and Map objects in the rep are made immutable by unmodifiable wrappers.
	
	//constructor
	public PeriodIntervalSetImpl(int period, int times) {
		this.period = period;
		this.times = times;
		int num = period*times;
		for(int i=0; i<=num; i++) {
			records.add(new HashSet<>());
		}
	}
	
	public PeriodIntervalSetImpl() {
		this.period = 7;
		this.times = 5;
		for(int i=0; i<=35; i++) {
			records.add(new HashSet<>());
		}
	}
	
	@Override
	public void checkNoConflict() throws Exception {
		for(int i=0; i<records.size(); i++) {
			if(records.get(i).size() > 1) {
				throw new Exception("Conflicts in interval set!");
			}
		}
	}

	@Override
	public void insert(L label, Interval<Integer> interval) throws Exception {
		int i = interval.getLabel();
		int index = (i-1)*times+(int)interval.getStart();
	    records.get(index).add(label);
	}

	@Override
	public double ratioOfFree() {
		int freeNum = 0;
		for(int i=1; i<records.size(); i++) {
			if(records.get(i).isEmpty()) {
				freeNum++;
			}
		}
		return (double)freeNum/(records.size()-1);
	}

	@Override
	public double ratioOfOverlap() {
		int overlapNum = 0;
		for(int i=1; i<records.size(); i++) {
			if(records.get(i).size() > 1) {
				overlapNum++;
			}
		}
		return (double)overlapNum/(records.size()-1);
	}

	@Override
	public List<Set<L>> eventsOfDay(int day) {
		List<Set<L>> ans = new ArrayList<>();
		int index = (day-1) % period + 1;
		for(int i=(index-1)*times+1; i<=index*times; i++) {
			ans.add(records.get(i));
		}
		return Collections.unmodifiableList(ans);
	}

}
