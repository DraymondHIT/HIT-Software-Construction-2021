package P2;

import P1.graph.ConcreteEdgesGraph;
import P1.graph.Graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class FriendshipGraph {
	private Graph<Person> graph = new ConcreteEdgesGraph<>();

	/**
	 * 向图中添加节点
	 * @param person label for the new vertex
	 * @return true if no such vertex in graph;
	 *         otherwise false
	 */
	public boolean addVertex(Person person) {
		if(!graph.add(person)) {
			System.out.println("已插入过该节点！");
			return false;
		}
		return true;
	}

	/**
	 * 向图中添加边
	 * @param p1 label for the source vertex
	 * @param p2 label for the target vertex
	 * @return true if there is no edge between p1 and p2;
	 *         otherwise false
	 */
	public boolean addEdge(Person p1, Person p2) {
		int weight;
		weight = graph.set(p1, p2, 1);
		graph.set(p2, p1, 1);
		return weight == 0 ? true : false;
	}

	/**
	 * 计算两个节点之间的距离，如果两个节点不连通则返回-1
	 * @param p1 label for the source vertex
	 * @param p2 label for the target vertex
	 * @return distance between the two vertices if they are connected;
	 *         otherwise -1
	 */
	public int getDistance(Person p1, Person p2) {
		if (p1.equals(p2))
			return 0;

		Set<Person> used = new HashSet<>();
		int distance = 0;

		//BFS
		Queue<Person> q = new LinkedList<>();
		q.offer(p1);
		used.add(p1);
		while (!q.isEmpty()) {
			int length = q.size();
			for (int i = 0; i < length; i++) {
				Person temp = q.element();
				q.poll();
				if (temp.equals(p2)) {
					return distance;
				}
				for(Person person : graph.targets(temp).keySet()) {
					if (!used.contains(person)) {
						q.offer(person);
						used.add(person);
					}
				}
			}
			distance++;
		}
		return -1;
	}
	
	
	/**
	 * main方法
	 * @param args
	 */
	public static void main(String[] args) {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		graph.addEdge(rachel, ross);
		graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, ross);
		System.out.println(graph.getDistance(rachel, ross));
		// should print 1
		System.out.println(graph.getDistance(rachel, ben));
		// should print 2
		System.out.println(graph.getDistance(rachel, rachel));
		// should print 0
		System.out.println(graph.getDistance(rachel, kramer));
		// should print -1
	}
}
