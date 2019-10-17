import java.util.ArrayList;

public class PriorityQueue1 {
	private ArrayList<Node<Vertex>> queue = new ArrayList<Node<Vertex>>();
	
	public void add(Vertex info, int p) {
		Node<Vertex> n = new Node<Vertex>(info, p);
		
		int start = 0;
		int end = queue.size()-1;
		int mid = 0;
		
		if(queue.isEmpty()) {	
			queue.add(n);
			return;
		}
		
		if(queue.get(0).GetP()<n.GetP()) {
			queue.add(0, n);
			return;
		}
		if(queue.get(queue.size()-1).GetP()>n.GetP()) {
			queue.add(n);
			return;
		}
		

		while(start<=end) {
			mid = (start+end)/2;
			if(queue.get(mid).GetP() > n.GetP()) {
				start = mid+1;
			}
			else if(queue.get(mid).GetP() < n.GetP()) {
				end = mid-1;
			}
			else {
				queue.add(mid, n);
				return;
			}
		}
			queue.add(start, n);
		
		
	}
	
	 public String toString() {
		String output = "";
		for(Node<Vertex> r: queue) {
			output += r.GetInfo().place;
			output += "\n";
		}
		return output;
	}
	
	public Vertex pop(){
		return queue.remove(queue.size()-1).GetInfo();
		
	}
	
	public int size() {
		return queue.size();
	}
	
	public boolean contains(Vertex ins) {
		for(Node<Vertex> v: queue) {
			if(v.GetInfo().equals(ins))
				return true;
		}
		return false;
	}
	
	public void update(Vertex info, int p) {
		for(Node<Vertex> k : queue) {
			if(k.GetInfo().equals(info)) {
				queue.remove(k);
				add(info, p);
				return;
			}
		}
		System.out.println("no existing Node");
	}
	
	public boolean isEmpty() {
		return queue.isEmpty();
	}
	
	public int getP(Vertex v) {
		for(int i = 0; i<queue.size(); i++) {
			if(queue.get(i).GetInfo().equals(v)) {
				return queue.get(i).GetP();
			}
		}
		
		return 00000;
		
	}
	
	

	public static void main(String[] args) {
		PriorityQueue1 test = new PriorityQueue1();
		Vertex v1 = new Vertex("v1", 10, 10);
		Vertex v2 = new Vertex("v2", 20, 20);
		Vertex v4 = new Vertex("v4", 50, 30);
		Vertex v5 = new Vertex("v5", 14, 50);
		test.add(v1, 1);
		test.add(v2, 2);
		test.add(v4, 4);
		test.add(v5, 5);
		System.out.println(test.toString());
		test.update(v4, 1);
		System.out.println(test.toString());
		Vertex v = new Vertex("v6", 60, 70);
		System.out.println(test.contains(v));
	}

}
