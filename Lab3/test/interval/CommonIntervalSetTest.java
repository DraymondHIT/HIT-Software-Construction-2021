package interval;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Set;

import org.junit.Test;

public class CommonIntervalSetTest extends IntervalSetInstanceTest{
	@Override public IntervalSet<String> emptyInstance() {
        return new CommonIntervalSet<>();
    }
	
	@Test
    public void toStringTest() throws Exception {
    	IntervalSet<String> intervals = emptyInstance();
    	
    	assertEquals("{}", intervals.toString());
    	
    	intervals.insert(0, 10, "A");
    	assertEquals("{A=[0,10]}", intervals.toString());
    	
    	intervals.insert(11, 15, "B");
    	assertEquals("{A=[0,10],B=[11,15]}", intervals.toString());
    	
    	intervals.insert(8, 10, "C");
    	assertEquals("{A=[0,10],C=[8,10],B=[11,15]}", intervals.toString());
    }
    
    @Test
    public void labelsTest() throws Exception {
    	IntervalSet<String> intervals = emptyInstance();
    	
    	assertEquals(Collections.emptySet(), intervals.labels());
    	
    	intervals.insert(0, 10, "A");
    	assertEquals(Set.of("A"), intervals.labels());
    }
    
    @Test(expected = Exception.class)
    public void insertExceptionTest() throws Exception {
    	IntervalSet<String> intervals = emptyInstance();
    	
    	intervals.insert(-1, 10, "A");
    }
    
    @Test
    public void insertTest() throws Exception {
    	IntervalSet<String> intervals = emptyInstance();
    	
    	assertEquals("{}", intervals.toString());
    	assertEquals(Collections.emptySet(), intervals.labels());
    	
    	assertEquals(true, intervals.insert(0, 10, "A"));
    	assertEquals("{A=[0,10]}", intervals.toString());
    	assertEquals(Set.of("A"), intervals.labels());
    	
    	assertEquals(false, intervals.insert(10, 20, "A"));
    	assertEquals("{A=[0,10]}", intervals.toString());
    	assertEquals(Set.of("A"), intervals.labels());
    	
    	assertEquals(true, intervals.insert(20, 30, "B"));
    	assertEquals(true, "{A=[0,10],B=[20,30]}".equals(intervals.toString()) || "{B=[20,30],A=[0,10]}".equals(intervals.toString()));
    	assertEquals(Set.of("A", "B"), intervals.labels());
    }
    
    @Test
    public void removeTest() throws Exception {
    	IntervalSet<String> intervals = emptyInstance();
    	
    	assertEquals("{}", intervals.toString());
    	assertEquals(Collections.emptySet(), intervals.labels());
    	
    	assertEquals(false, intervals.remove("A"));
    	assertEquals("{}", intervals.toString());
    	assertEquals(Collections.emptySet(), intervals.labels());
    	
    	intervals.insert(0, 10, "A");
    	intervals.insert(20, 30, "B");
    	
//    	assertEquals("{B=[20,30],A=[0,10]}", intervals.toString());
    	assertEquals(true, "{A=[0,10],B=[20,30]}".equals(intervals.toString()) || "{B=[20,30],A=[0,10]}".equals(intervals.toString()));
    	assertEquals(Set.of("A", "B"), intervals.labels());
    	
    	assertEquals(true, intervals.remove("A"));
    	assertEquals("{B=[20,30]}", intervals.toString());
    	assertEquals(Set.of("B"), intervals.labels());
    	
    	assertEquals(false, intervals.remove("A"));
    	assertEquals("{B=[20,30]}", intervals.toString());
    	assertEquals(Set.of("B"), intervals.labels());
    	
    	assertEquals(true, intervals.remove("B"));
    	assertEquals("{}", intervals.toString());
    	assertEquals(Collections.emptySet(), intervals.labels());
    }
    
    @Test
    public void getStartTest() throws Exception {
    	IntervalSet<String> intervals = emptyInstance();
    	
    	assertEquals("{}", intervals.toString());
    	assertEquals(Collections.emptySet(), intervals.labels());
    	
    	intervals.insert(0, 10, "A");
    	intervals.insert(20, 30, "B");
    	
    	assertEquals(true, "{A=[0,10],B=[20,30]}".equals(intervals.toString()) || "{B=[20,30],A=[0,10]}".equals(intervals.toString()));
    	assertEquals(Set.of("A", "B"), intervals.labels());
    	
    	assertEquals(0, intervals.getStart("A"));
    	assertEquals(20, intervals.getStart("B"));
    	
    	assertEquals(true, "{A=[0,10],B=[20,30]}".equals(intervals.toString()) || "{B=[20,30],A=[0,10]}".equals(intervals.toString()));
    	assertEquals(Set.of("A", "B"), intervals.labels());
    }
    
    @Test(expected = Exception.class)
    public void getStartExceptionTest() throws Exception {
        IntervalSet<String> intervals = emptyInstance();
    	
    	assertEquals("{}", intervals.toString());
    	assertEquals(Collections.emptySet(), intervals.labels());
    	
    	intervals.getStart("A");
    }
    
    @Test
    public void getEndTest() throws Exception {
    	IntervalSet<String> intervals = emptyInstance();
    	
    	assertEquals("{}", intervals.toString());
    	assertEquals(Collections.emptySet(), intervals.labels());
    	
    	intervals.insert(0, 10, "A");
    	intervals.insert(20, 30, "B");
    	
    	assertEquals(true, "{A=[0,10],B=[20,30]}".equals(intervals.toString()) || "{B=[20,30],A=[0,10]}".equals(intervals.toString()));
    	assertEquals(Set.of("A", "B"), intervals.labels());
    	
    	assertEquals(10, intervals.getEnd("A"));
    	assertEquals(30, intervals.getEnd("B"));
    	
    	assertEquals(true, "{A=[0,10],B=[20,30]}".equals(intervals.toString()) || "{B=[20,30],A=[0,10]}".equals(intervals.toString()));
    	assertEquals(Set.of("A", "B"), intervals.labels());
    }
    
    @Test(expected = Exception.class)
    public void getEndExceptionTest() throws Exception {
        IntervalSet<String> intervals = emptyInstance();
    	
    	assertEquals("{}", intervals.toString());
    	assertEquals(Collections.emptySet(), intervals.labels());
    	
    	intervals.getEnd("A");
    }
    
    @Test
    public void intervalTest() throws Exception {
    	Interval<String> interval = new Interval<>(0, 10, "A");
    	
    	assertEquals(0, interval.getStart());
    	assertEquals(10, interval.getEnd());
    	assertEquals("A", interval.getLabel());
    	
    	assertEquals("A=[0,10]", interval.toString());
    }

}
