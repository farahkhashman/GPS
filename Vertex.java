
import java.util.ArrayList;

public class Vertex {
	public ArrayList<Edge> edges;
	public String place;
	public int x, y;
	
	public Vertex(String val, int xcor, int ycor) {
		place = val;
		x = xcor; 
		y = ycor;
		edges = new ArrayList<Edge>();
	}
	
	public void addEdge(Edge e) {
		edges.add(e);
	}
}
