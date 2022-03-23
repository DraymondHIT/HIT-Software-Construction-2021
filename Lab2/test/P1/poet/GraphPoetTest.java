/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {
    
    // Testing strategy
    //   测试权重均为1的有向图和权重不都为1的有向图
	//   测试有向图为空或者input为空的情况
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // tests
    @Test
    public void poemTest() throws IOException {
    	GraphPoet test1 = new GraphPoet(new File("./test/P1/poet/test1.txt"));
    	String input1 = "a c c a.";
    	
    	assertEquals("a b c c d a.", test1.poem(input1));
    	
    	GraphPoet test2 = new GraphPoet(new File("./test/P1/poet/test2.txt"));
    	String input2 = "a c c a c.";
    	
    	assertEquals("a d c d c a d c.", test2.poem(input2));
    	
    	GraphPoet test3 = new GraphPoet(new File("./test/P1/poet/test3.txt"));
    	String input3 = "";
    	
    	assertEquals("", test3.poem(input3));
    	
    	GraphPoet test4 = new GraphPoet(new File("./test/P1/poet/test4.txt"));
    	String input4 = "a b c.";
    	
    	assertEquals("a b c.", test4.poem(input4));
    }
}
