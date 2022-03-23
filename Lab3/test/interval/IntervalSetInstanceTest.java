package interval;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

public abstract class IntervalSetInstanceTest {

	// Testing strategy
	// insert方法测试：
	//     分别测试已加入集合中的interval和未加入集合中的interval
	//     分别测试interval的start和end合法和非法的情况
	// remove方法测试：
	//     测试删除集合中已有的interval和实际没有的interval
	// labels方法测试：
	//     测试空集和非空集
	// getStart方法测试：
	//     测试集合中已有的interval和实际没有的interval
	// getEnd方法测试：
	//     测试集合中已有的interval和实际没有的interval
	
	/**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty interval set of the particular implementation being tested
     */
    public abstract IntervalSet<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; 
    }
    
    @Test
    public void testInitialLabelsEmpty() {
        assertEquals("expected new interval set to have no intervals",
                Collections.emptySet(), emptyInstance().labels());
    }

}
