
public class Node<Vertex> {
	
	private Vertex info;
	private int priority = 0;
	
	public Node(Vertex in, int p){
		info = in;
		priority = p;
	}
	
	public int GetP() {
		return priority;
	}
	
	public Vertex GetInfo() {
		return info;
	}

}
