package interval;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Set;

import org.junit.Test;

public class IntervalSetStaticTest {

	// Testing strategy
    //   empty()
    //     no inputs, only output is empty graph
    //     observe with vertices()
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testEmptyLabelsEmpty() {
        assertEquals("expected empty() graph to have no vertices",
                Collections.emptySet(), IntervalSet.empty().labels());
    }
    
    @Test
    public void labelsTest() throws Exception {
    	IntervalSet<Integer> intervals = IntervalSet.empty();
    	
    	assertEquals(Collections.emptySet(), intervals.labels());
    	
    	intervals.insert(0, 10, 100);
    	assertEquals(Set.of(100), intervals.labels());
    }
    
    @Test(expected = Exception.class)
    public void insertExceptionTest() throws Exception {
    	IntervalSet<Integer> intervals = IntervalSet.empty();
    	
    	intervals.insert(-1, 10, 100);
    }
    
    @Test
    public void insertTest() throws Exception {
    	IntervalSet<Integer> intervals = IntervalSet.empty();
    	
    	assertEquals("{}", intervals.toString());
    	assertEquals(Collections.emptySet(), intervals.labels());
    	
    	assertEquals(true, intervals.insert(0, 10, 100));
    	assertEquals("{100=[0,10]}", intervals.toString());
    	assertEquals(Set.of(100), intervals.labels());
    	
    	assertEquals(false, intervals.insert(10, 20, 100));
    	assertEquals("{100=[0,10]}", intervals.toString());
    	assertEquals(Set.of(100), intervals.labels());
    	
    	assertEquals(true, intervals.insert(20, 30, 200));
    	assertEquals(true, "{100=[0,10],200=[20,30]}".equals(intervals.toString()) || "{200=[20,30],100=[0,10]}".equals(intervals.toString()));
    	assertEquals(Set.of(100, 200), intervals.labels());
    }
    
    @Test
    public void removeTest() throws Exception {
    	IntervalSet<Integer> intervals = IntervalSet.empty();
    	
    	assertEquals("{}", intervals.toString());
    	assertEquals(Collections.emptySet(), intervals.labels());
    	
    	assertEquals(false, intervals.remove(100));
    	assertEquals("{}", intervals.toString());
    	assertEquals(Collections.emptySet(), intervals.labels());
    	
    	intervals.insert(0, 10, 100);
    	intervals.insert(20, 30, 200);
    	
    	assertEquals(true, "{100=[0,10],200=[20,30]}".equals(intervals.toString()) || "{200=[20,30],100=[0,10]}".equals(intervals.toString()));
    	assertEquals(Set.of(100, 200), intervals.labels());
    	
    	assertEquals(true, intervals.remove(100));
    	assertEquals("{200=[20,30]}", intervals.toString());
    	assertEquals(Set.of(200), intervals.labels());
    	
    	assertEquals(false, intervals.remove(100));
    	assertEquals("{200=[20,30]}", intervals.toString());
    	assertEquals(Set.of(200), intervals.labels());
    	
    	assertEquals(true, intervals.remove(200));
    	assertEquals("{}", intervals.toString());
    	assertEquals(Collections.emptySet(), intervals.labels());
    }
    
    @Test
    public void getStartTest() throws Exception {
    	IntervalSet<Integer> intervals = IntervalSet.empty();
    	
    	assertEquals("{}", intervals.toString());
    	assertEquals(Collections.emptySet(), intervals.labels());
    	
    	intervals.insert(0, 10, 100);
    	intervals.insert(20, 30, 200);
    	
    	assertEquals(true, "{100=[0,10],200=[20,30]}".equals(intervals.toString()) || "{200=[20,30],100=[0,10]}".equals(intervals.toString()));
    	assertEquals(Set.of(100, 200), intervals.labels());
    	
    	assertEquals(0, intervals.getStart(100));
    	assertEquals(20, intervals.getStart(200));
    	
    	assertEquals(true, "{100=[0,10],200=[20,30]}".equals(intervals.toString()) || "{200=[20,30],100=[0,10]}".equals(intervals.toString()));
    	assertEquals(Set.of(100, 200), intervals.labels());
    }
    
    @Test(expected = Exception.class)
    public void getStartExceptionTest() throws Exception {
    	IntervalSet<Integer> intervals = IntervalSet.empty();
    	
    	assertEquals("{}", intervals.toString());
    	assertEquals(Collections.emptySet(), intervals.labels());
    	
    	intervals.getStart(100);
    }
    
    @Test
    public void getEndTest() throws Exception {
    	IntervalSet<Integer> intervals = IntervalSet.empty();
    	
    	assertEquals("{}", intervals.toString());
    	assertEquals(Collections.emptySet(), intervals.labels());
    	
    	intervals.insert(0, 10, 100);
    	intervals.insert(20, 30, 200);
    	
    	assertEquals(true, "{100=[0,10],200=[20,30]}".equals(intervals.toString()) || "{200=[20,30],100=[0,10]}".equals(intervals.toString()));
    	assertEquals(Set.of(100, 200), intervals.labels());
    	
    	assertEquals(10, intervals.getEnd(100));
    	assertEquals(30, intervals.getEnd(200));
    	
    	assertEquals(true, "{100=[0,10],200=[20,30]}".equals(intervals.toString()) || "{200=[20,30],100=[0,10]}".equals(intervals.toString()));
    	assertEquals(Set.of(100, 200), intervals.labels());
    }
    
    @Test(expected = Exception.class)
    public void getEndExceptionTest() throws Exception {
    	IntervalSet<Integer> intervals = IntervalSet.empty();
    	
    	assertEquals("{}", intervals.toString());
    	assertEquals(Collections.emptySet(), intervals.labels());
    	
    	intervals.getEnd(100);
    }
}
