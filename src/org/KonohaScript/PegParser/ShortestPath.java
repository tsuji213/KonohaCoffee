package org.KonohaScript.PegParser;

import java.util.ArrayList;

public class ShortestPath {

	public ShortestPath() {
	}

	private static final int	MAX	= 1024;
	private int[]				pathinfo;
	private int					listsize;

	private void Init(int edges[], ArrayList<Node> NodeList) {
		this.listsize = NodeList.size();
		for(int i = 0; i < edges.length; i++) {
			edges[i] = MAX;
		}
		for(int i = 0; i < this.listsize; i++) {
			Node node = NodeList.get(i);
			node.Index = i;
		}
		for(int i = 0; i < this.listsize; i++) {
			Node node = NodeList.get(i);
			if(node.Children != null) {
				for(int j = 0; j < node.Children.size(); j++) {
					Node child = node.Children.get(j);
					edges[node.Index * NodeList.size() + child.Index] = 1;
				}
			}
		}
	}

	void Compute(ArrayList<Node> NodeList) {
		int ListSize = NodeList.size();
		this.pathinfo = new int[ListSize * ListSize];
		int[] edges = new int[ListSize * ListSize];
		int[] distance = new int[ListSize];
		boolean[] visited = new boolean[ListSize];

		this.Init(edges, NodeList);
		for(int i = 0; i < ListSize; i++) {
			this.ComputeDistance(i, ListSize, edges, distance, visited);
			System.arraycopy(distance, 0, this.pathinfo, ListSize * i, ListSize);
		}
	}

	void ComputeDistance(int start, int ListSize, int[] edges, int[] distance, boolean[] visited) {
		int i, j, min, next = start;
		for(i = 0; i < ListSize; i++) {
			visited[i] = false;
			distance[i] = MAX;
		}
		distance[start] = 0;
		do {
			i = next;
			min = MAX;
			visited[i] = true;
			for(j = 0; j < ListSize; j++) {
				if(visited[j] == true)
					continue;
				int edge = edges[i * ListSize + j];
				if(edge < MAX && distance[i] + edge < distance[j]) {
					distance[j] = distance[i] + edge;
					this.pathinfo[start * ListSize + j] = i;
				}
				if(distance[j] < min) {
					min = distance[j];
					next = j;
				}
			}
		} while(min < MAX);
	}

	int score(Node x, Node y) {
		return this.pathinfo[x.Index * this.listsize + y.Index];
	}

	public static void main(String[] args) {
		int nodesize = 5;
		ArrayList<Node> nodes = new ArrayList<Node>();
		for(int i = 0; i < nodesize; i++) {
			nodes.add(new Node(i));
		}
		nodes.get(0).Id = 0;
		nodes.get(1).Id = 1;
		nodes.get(2).Id = 2;
		nodes.get(3).Id = 3;
		nodes.get(4).Id = 4;

		nodes.get(0).Append(nodes.get(1));
		nodes.get(1).Append(nodes.get(2));
		nodes.get(1).Append(nodes.get(3));
		nodes.get(2).Append(nodes.get(3));
		nodes.get(2).Append(nodes.get(4));
		nodes.get(4).Append(nodes.get(1));

		ShortestPath pathinfo = new ShortestPath();
		pathinfo.Compute(nodes);
		System.out.print("  ");
		for(int j = 0; j < nodes.size(); j++) {
			Node y = nodes.get(j);
			System.out.print(" " + y.Id + "|");
		}
		System.out.println();

		for(int i = 0; i < nodes.size(); i++) {
			Node x = nodes.get(i);
			System.out.print(x.Id + ":");

			for(int j = 0; j < nodes.size(); j++) {
				Node y = nodes.get(j);
				int score = pathinfo.score(x, y);
				String p = "" + score;
				if(score >= 0) {
					p = " " + p;
				}
				System.out.print(p + "|");
			}
			System.out.println();

		}
	}
}

class Node {
	ArrayList<Node>	Children;
	int				Id;
	int				Index;		// Initialized by Path Class

	Node(int Id) {
		this.Id = Id;
		this.Children = null;
	}

	void Append(Node Child) {
		if(this.Children == null) {
			this.Children = new ArrayList<Node>();
		}
		this.Children.add(Child);
	}
}