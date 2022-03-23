package P2;

import static org.junit.Assert.*;

import org.junit.Test;

public class FriendshipGraphTest {

	@Test
	public void addVertexTest() {
        FriendshipGraph graph = new FriendshipGraph();

        Person a = new Person("a");
        Person b = new Person("b");
        
        assertEquals(true, graph.addVertex(a));
        assertEquals(true, graph.addVertex(b));
        assertEquals(false, graph.addVertex(a));
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
        
        assertEquals(true, graph.addEdge(a, b));
        assertEquals(true, graph.addEdge(b, c));
        assertEquals(false, graph.addEdge(a, b));
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
