package interval;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Set;

import org.junit.Test;

public abstract class MultiIntervalSetInstanceTest {
	// Testing strategy
	// insert方法测试：
	//     分别测试已加入集合中的interval和未加入集合中的interval
	//     分别测试标签在集合中的interval和标签不在集合中的interval
	//     分别测试interval的start和end合法和非法的情况
	// remove方法测试：
	//     测试删除集合中已有的multiinterval和实际没有的multiinterval
	// labels方法测试：
	//     测试空集和非空集
	// intervals方法测试：
	//     测试集合中已有的multiinterval和实际没有的multiinterval
	
	/**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty interval set of the particular implementation being tested
     */
    public abstract MultiIntervalSet<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; 
    }
    
    @Test
    public void testInitialLabelsEmpty() {
        assertEquals("expected new interval set to have no intervals",
                Collections.emptySet(), emptyInstance().labels());
    }
    
    @Test(expected = Exception.class)
    public void insertExceptionTest() throws Exception {
    	MultiIntervalSet<String> multiIntervals = emptyInstance();
    	
    	multiIntervals.insert(-1, 10, "A");
    }
    
    @Test
    public void insertTest() throws Exception {
    	MultiIntervalSet<String> multiIntervals = emptyInstance();
    	
    	assertEquals("{}", multiIntervals.toString());
    	assertEquals(Collections.emptySet(), multiIntervals.labels());
    	
    	multiIntervals.insert(0, 10, "A");
    	assertEquals("{A=[[0,10]]}", multiIntervals.toString());
    	assertEquals(Set.of("A"), multiIntervals.labels());
    	
		multiIntervals.insert(15, 20, "A");
    	assertEquals("{A=[[0,10],[15,20]]}", multiIntervals.toString());
    	assertEquals(Set.of("A"), multiIntervals.labels());
   	
    	multiIntervals.insert(20, 30, "B");
    	System.out.println(multiIntervals.toString());
    	assertEquals("{B=[[20,30]],A=[[0,10],[15,20]]}", multiIntervals.toString());
    	assertEquals(Set.of("A", "B"), multiIntervals.labels());
    }
    
    @Test
    public void removeTest() throws Exception {
    	MultiIntervalSet<String> multiIntervals = emptyInstance();
    	
    	assertEquals("{}", multiIntervals.toString());
    	assertEquals(Collections.emptySet(), multiIntervals.labels());
    	
    	assertEquals(false, multiIntervals.remove("A"));
    	assertEquals("{}", multiIntervals.toString());
    	assertEquals(Collections.emptySet(), multiIntervals.labels());
    	
    	multiIntervals.insert(0, 10, "A");
    	multiIntervals.insert(15, 20, "A");
    	multiIntervals.insert(20, 30, "B");
    	
    	assertEquals("{A=[[0,10],[15,20]],B=[[20,30]]}", multiIntervals.toString());
    	assertEquals(Set.of("A", "B"), multiIntervals.labels());
    	
    	assertEquals(true, multiIntervals.remove("A"));
    	assertEquals("{B=[[20,30]]}", multiIntervals.toString());
    	assertEquals(Set.of("B"), multiIntervals.labels());
    	
    	assertEquals(false, multiIntervals.remove("A"));
    	assertEquals("{B=[[20,30]]}", multiIntervals.toString());
    	assertEquals(Set.of("B"), multiIntervals.labels());
    	
    	assertEquals(true, multiIntervals.remove("B"));
    	assertEquals("{}", multiIntervals.toString());
    	assertEquals(Collections.emptySet(), multiIntervals.labels());
    }
    
    @Test
	public void labelsTest() throws Exception {
		MultiIntervalSet<String> multiIntervals = emptyInstance();
		
		multiIntervals.insert(0, 10, "A");
		assertEquals(Set.of("A"), multiIntervals.labels());
		
		multiIntervals.insert(15, 20, "A");
		assertEquals(Set.of("A"), multiIntervals.labels());
		
		multiIntervals.insert(20, 30, "B");
		assertEquals(Set.of("A","B"), multiIntervals.labels());
	}
	
	@Test
	public void intervalsTest() throws Exception {
		MultiIntervalSet<String> multiIntervals = emptyInstance();
		
		assertEquals("{}", multiIntervals.intervals("A").toString());
		
		multiIntervals.insert(0, 10, "A");
		multiIntervals.insert(15, 20, "A");
		multiIntervals.insert(20, 30, "B");
		
		assertEquals("{0=[0,10],1=[15,20]}", multiIntervals.intervals("A").toString());
		assertEquals("{0=[20,30]}", multiIntervals.intervals("B").toString());
	}
    
}
