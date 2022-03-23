package P3;

import java.util.Vector;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class FriendshipGraph {
	private Vector<Vector<Integer>> graph = new Vector<Vector<Integer>>();
	private Set<String> names = new HashSet<String> ();
	private int num = 0;

	/**
	 * 向图中添加节点
	 * @param person
	 */
	public void addVertex(Person person) {
		if(person.getIndex() != -1) {
			System.out.println("已插入过该节点!");
		} else if(names.contains(person.getName())) {
			System.out.println("不同的人不能具有相同的姓名!");
			System.exit(0);
		} else {
			graph.add(new Vector<Integer>());
			names.add(person.getName());
		    person.setIndex(num);
		    num++;
		}
	}

	/**
	 * 向图中添加边
	 * @param p1
	 * @param p2
	 */
	public void addEdge(Person p1, Person p2) {
		int num1 = p1.getIndex();
		int num2 = p2.getIndex();
		graph.get(num1).add(num2);
		graph.get(num2).add(num1);
	}

	/**
	 * 计算两个节点之间的距离，如果两个节点不连通则返回-1
	 * @param p1
	 * @param p2
	 * @return
	 */
	public int getDistance(Person p1, Person p2) {
		int num1 = p1.getIndex();
		int num2 = p2.getIndex();
		if (num1 == num2)
			return 0;

		boolean[] used = new boolean[graph.size()];
		int distance = 0;

		//BFS
		Queue<Integer> q = new LinkedList<Integer>();
		q.offer(num1);
		used[num1] = true;
		while (!q.isEmpty()) {
			int length = q.size();
			for (int i = 0; i < length; i++) {
				int temp = q.element();
				q.poll();
				if (temp == num2) {
					return distance;
				}
				for (int j = 0; j < graph.get(temp).size(); j++) {
					if (!used[graph.get(temp).get(j)]) {
						q.offer(graph.get(temp).get(j));
						used[graph.get(temp).get(j)] = true;
					}
				}
			}
			distance++;
		}
		return -1;
	}
	
	/**
	 * 判断两个顶点间是否有边直接相连，辅助测试用
	 * @param p1
	 * @param p2
	 * @return
	 */
	public boolean isDirectlyConnected(Person p1, Person p2) {
		int num1 = p1.getIndex();
		int num2 = p2.getIndex();
		return graph.get(num1).contains(num2);
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
