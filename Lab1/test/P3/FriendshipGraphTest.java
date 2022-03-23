package P3;

import static org.junit.Assert.*;

import org.junit.Test;

public class FriendshipGraphTest {

	@Test
	public void addVertexTest() {
        FriendshipGraph graph = new FriendshipGraph();

        Person a = new Person("a");
        Person b = new Person("b");
//        Person c = new Person("a");
        
        graph.addVertex(a);
        graph.addVertex(b);
//        graph.addVertex(c);
    }
	
	@Test
	public void addEdgeTest() {
		FriendshipGraph graph = new FriendshipGraph();

		Person a = new Person("a");
        Person b = new Person("b");
        Person c = new Person("c");
        
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        
        graph.addEdge(a, b);
        graph.addEdge(b, c);
        
        assertEquals(true, graph.isDirectlyConnected(a, b));
        assertEquals(false, graph.isDirectlyConnected(a, c));
        assertEquals(true, graph.isDirectlyConnected(b, c));
	}

	
	@Test
	public void getDistanceTest() {
		FriendshipGraph graph = new FriendshipGraph();

		Person a = new Person("a");
        Person b = new Person("b");
        Person c = new Person("c");
        Person d = new Person("d");
        Person e = new Person("e");
        Person f = new Person("f");
        Person g = new Person("g");
        Person h = new Person("h");
        
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.addVertex(e);
        graph.addVertex(f);
        graph.addVertex(g);
        graph.addVertex(h);
        
        graph.addEdge(a, b);
        graph.addEdge(a, e);
        graph.addEdge(b, c);
        graph.addEdge(b, g);
        graph.addEdge(c, e);
        graph.addEdge(d, f);
        graph.addEdge(d, h);
        
        assertEquals(2, graph.getDistance(a, c));
        assertEquals(2, graph.getDistance(c, g));
        assertEquals(1, graph.getDistance(b, c));
        assertEquals(-1, graph.getDistance(f, c));
        assertEquals(2, graph.getDistance(h, f));
	}
}
