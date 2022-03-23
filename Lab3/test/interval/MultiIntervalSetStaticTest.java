package interval;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Set;

import org.junit.Test;

public class MultiIntervalSetStaticTest {

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
                Collections.emptySet(), MultiIntervalSet.empty().labels());
    }

    @Test
	public void labelsTest() throws Exception {
		MultiIntervalSet<Integer> multiIntervals = MultiIntervalSet.empty();
		
		multiIntervals.insert(0, 10, 100);
		assertEquals(Set.of(100), multiIntervals.labels());
		
		multiIntervals.insert(15, 20, 100);
		assertEquals(Set.of(100), multiIntervals.labels());
		
		multiIntervals.insert(20, 30, 200);
		assertEquals(Set.of(100, 200), multiIntervals.labels());
	}
	
	@Test
	public void intervalsTest() throws Exception {
		MultiIntervalSet<Integer> multiIntervals = MultiIntervalSet.empty();
		
		assertEquals("{}", multiIntervals.intervals(100).toString());
		
		multiIntervals.insert(0, 10, 100);
		multiIntervals.insert(15, 20, 100);
		multiIntervals.insert(20, 30, 200);
		
		assertEquals("{0=[0,10],1=[15,20]}", multiIntervals.intervals(100).toString());
		assertEquals("{0=[20,30]}", multiIntervals.intervals(200).toString());
	}
	
	@Test(expected = Exception.class)
    public void insertExceptionTest() throws Exception {
		MultiIntervalSet<Integer> multiIntervals = MultiIntervalSet.empty();
    	
    	multiIntervals.insert(-1, 10, 100);
    }
    
    @Test
    public void insertTest() throws Exception {
    	MultiIntervalSet<Integer> multiIntervals = MultiIntervalSet.empty();
    	
    	assertEquals("{}", multiIntervals.toString());
    	assertEquals(Collections.emptySet(), multiIntervals.labels());
    	
    	multiIntervals.insert(0, 10, 100);
    	assertEquals("{100=[[0,10]]}", multiIntervals.toString());
    	assertEquals(Set.of(100), multiIntervals.labels());
    	
		multiIntervals.insert(15, 20, 100);
    	assertEquals("{100=[[0,10],[15,20]]}", multiIntervals.toString());
    	assertEquals(Set.of(100), multiIntervals.labels());
   	
    	multiIntervals.insert(20, 30, 200);
    	assertEquals(true, multiIntervals.toString().equals("{100=[[0,10],[15,20]],200=[[20,30]]}") || multiIntervals.toString().equals("{200=[[20,30]],100=[[0,10],[15,20]]}"));
    	assertEquals(Set.of(100, 200), multiIntervals.labels());
    }
    
    @Test
    public void removeTest() throws Exception {
    	MultiIntervalSet<Integer> multiIntervals = MultiIntervalSet.empty();
    	
    	assertEquals("{}", multiIntervals.toString());
    	assertEquals(Collections.emptySet(), multiIntervals.labels());
    	
    	assertEquals(false, multiIntervals.remove(100));
    	assertEquals("{}", multiIntervals.toString());
    	assertEquals(Collections.emptySet(), multiIntervals.labels());
    	
    	multiIntervals.insert(0, 10, 100);
    	multiIntervals.insert(15, 20, 100);
    	multiIntervals.insert(20, 30, 200);
    	
    	assertEquals(true, multiIntervals.toString().equals("{100=[[0,10],[15,20]],200=[[20,30]]}") || multiIntervals.toString().equals("{200=[[20,30]],100=[[0,10],[15,20]]}"));
    	assertEquals(Set.of(100, 200), multiIntervals.labels());
    	
    	assertEquals(true, multiIntervals.remove(100));
    	assertEquals("{200=[[20,30]]}", multiIntervals.toString());
    	assertEquals(Set.of(200), multiIntervals.labels());
    	
    	assertEquals(false, multiIntervals.remove(100));
    	assertEquals("{200=[[20,30]]}", multiIntervals.toString());
    	assertEquals(Set.of(200), multiIntervals.labels());
    	
    	assertEquals(true, multiIntervals.remove(200));
    	assertEquals("{}", multiIntervals.toString());
    	assertEquals(Collections.emptySet(), multiIntervals.labels());
    }
    
}
