import java.util.HashMap;

// a data structure representing a Graph 
public class Graph {

	HashMap<String,Vertex> vertices;
	
	public Graph() {
		vertices = new HashMap<String,Vertex>();
	}
	
	public Vertex addVertex(String info, int xcor, int ycor) {
		Vertex v = new Vertex(info, xcor, ycor);
		vertices.put(info,v);
		return v;
	}
	
	public void addEdge(String v1name, String v2name, int edgedistance) {
		if (vertices.containsKey(v1name) && vertices.containsKey(v2name)) {
			Edge e = new Edge(vertices.get(v1name), vertices.get(v2name), edgedistance);
			vertices.get(v1name).addEdge(e);
			vertices.get(v2name).addEdge(e);
		}
		else
			System.out.println("Graph does not contain the inputted vertices.");
	}
	

}
