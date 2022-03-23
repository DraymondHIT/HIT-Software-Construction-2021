package interval;

import java.util.ArrayList;
import java.util.List;

/**
 * An immutable interval.
 * @author lkz
 *
 * @param <L> type of labels in this interval set, must be immutable
 */
public class MultiInterval<L> {
	//fields
	private final List<Long> starts = new ArrayList<>();
	private final List<Long> ends = new ArrayList<>();
	private final L label;
	
	// Abstraction function:
    //   start,end分别存放多个intervals的起始数值和终止数值
	//   label代表这些intervals共同的标签名
    // Representation invariant:
    //   starts和ends中的数必须是非负数
    // Safety from rep exposure:
    //   All fields are private final,
    //   both L and long are immutable type
	
	//constructor
	public MultiInterval(L label){
		this.label = label;
		checkRep();
	}
	
	//checkRep
	private void checkRep() {
		for(int i=0; i<starts.size(); i++) {
			assert starts.get(i) >= 0 && ends.get(i) >= 0;
		}
	}
	
	//methods
	public boolean add(long start, long end) throws Exception {
		if(start < 0 || end < 0) {
			throw new Exception();
		}
		if(starts.isEmpty() || start > starts.get(starts.size()-1)) {
			starts.add(start);
			ends.add(end);
			checkRep();
			return true;
		}
		for(int i=0; i<starts.size(); i++) {
			if(start < starts.get(i) || start == starts.get(i) && end < ends.get(i)) {
				starts.add(i, start);
				ends.add(i, end);
				checkRep();
				return true;
			}else if(start == starts.get(i) && end == ends.get(i)) {
				return false;
			}
		}
		checkRep();
		return true;
	}
	
	public List<Long> getStart() {
		return starts;
	}
	
	public List<Long> getEnd() {
		return ends;
	}
	
	public L getLabel() {
		return label;
	}
	
	public int size() {
		return starts.size();
	}
	
	@Override
	public String toString() {
		String str = label + "=[";
		for(int i=0; i<starts.size(); i++) {
			str += "[" + starts.get(i) + "," + ends.get(i) + "],";
		}
		if(starts.size() > 0) {
			str = str.substring(0, str.length()-1);
		}
		str += "]";
		checkRep();
		return str;
	}
}
