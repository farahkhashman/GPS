import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class BFSmap extends JPanel{
	private final int width = 1000, height = 700;
	int x, y;
	Image img, campus;
	Boolean point = false;
	HashMap<String, Point> map = new HashMap<String, Point>();
	HashMap<String, ArrayList<String>> edges = new HashMap<String, ArrayList<String>>();
	HashMap<String, Point> PointMap = new HashMap<String, Point>();
	ArrayList<Edge> edgepath = new ArrayList<Edge>();
	ArrayList<Vertex> vertexpath = new ArrayList<Vertex>();
	
	Graph gr = new Graph();
	HashMap<Vertex, ArrayList<Edge>> compiled = new HashMap<Vertex, ArrayList<Edge>>();
	Vertex vselected = null, vhover = null, vtarget = null;
	
	
	
	public BFSmap() throws IOException{
		BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(boxlayout);
		setBorder(BorderFactory.createTitledBorder("King's Academy Map"));
		JTextArea displayarea = new JTextArea();
		displayarea.setEditable(true);
		
		JButton addpoint = new JButton("add point");
		addpoint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WriteData();
			}
		});
		
		JPanel innerPanel = new JPanel();
		innerPanel.add(addpoint);
		add(innerPanel);
		
		JFrame frame = new JFrame();
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		this.setFocusable(true);
		
		img = Toolkit.getDefaultToolkit().createImage("old.png");
		campus = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		run();
	}
	
	public void CollectData() {
		x = y = 0;
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				x = e.getX();
				y = e.getY();
			}
			public void mouseReleased(MouseEvent e) {
				String input = JOptionPane.showInputDialog(this, "Name of the Vertex");
				Point v1 = new Point(x,y);
				map.put(input, v1);
			}
		});
	}
	
	public void WriteData() {
		try {
			FileWriter writer = new FileWriter("MapData.txt", true);
			for(String keys: map.keySet()) {
				writer.write(keys +", "+ map.get(keys).x +", " +map.get(keys).y);
				writer.write("\n");
				System.out.println(keys +", "+ map.get(keys).x +", " +map.get(keys).y);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		//CollectData();
		readFileVertex();
		hover();
		userinput();
	}
	
	private void readFileVertex() {
		try {
			BufferedReader in = new BufferedReader(new FileReader("MapData.txt"));
			for (String line = in.readLine(); line != null; line = in.readLine()) {
				String[] string = line.split(", ");
				int xcor = Integer.parseInt(string[1]);
				int ycor = Integer.parseInt(string[2]);
				gr.addVertex(string[0], xcor, ycor);
				
				}
			repaint();
			in.close();
		} 
		catch (FileNotFoundException e) {
			System.out.println("File not found :( make sure file is in the project (not source code) and "
					+ "has the correct name");
		} 
		catch (IOException e) {}
		ReadFileEdges();
	}
	
	private void ReadFileEdges() {
		try {
			BufferedReader in = new BufferedReader(new FileReader("MapData.txt"));
			ArrayList<Integer> distances = new ArrayList<Integer>();
			for (String line = in.readLine(); line != null; line = in.readLine()) {
				String[] string = line.split(", ");
				int p1x = gr.vertices.get(string[0]).x;
				int p1y = gr.vertices.get(string[0]).y;
				for(int i = 3; i<string.length; i++) {
					int p2x = gr.vertices.get(string[i]).x;
					int p2y = gr.vertices.get(string[i]).y;
					int distance = (int) Math.sqrt(Math.pow(p2x-p1x, 2) + Math.pow(p2y-p1y, 2));
					if(!distances.contains(distance)) {
						distances.add(distance);
						gr.addEdge(string[0], string[i], distance);
					}
				}
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found :( make sure file is in the project (not source code) and "
					+ "has the correct name");
		} 	
		catch (IOException e) {}
	}
	
	
	public void paint(Graphics g) {
		Point p2 = null; 
		Point p3 = null;
		super.paint(g);
		g.drawImage(campus, 0, 60, this);
		
		Graphics2D g2 = (Graphics2D) g;
		for(Vertex v: gr.vertices.values()) {
			p2 = new Point(v.x, v.y);
			for(Edge e : v.edges) {
				p3 = new Point(e.get(v).x, e.get(v).y);
				if(edgepath.contains(e)) {
					g2.setColor(Color.BLUE);
				}
				else {
					g2.setColor(Color.BLACK);
				}
			    g2.setStroke(new BasicStroke(3));
				g2.drawLine(p2.x, p2.y, p3.x, p3.y);
				Point midpoint = new Point(((p2.x+ p3.x)/2), ((p2.y+p3.y)/2));
				g.drawString(Integer.toString(e.distance), midpoint.x, midpoint.y);
			}
		}
		
		
		for(String key : gr.vertices.keySet()) {
			if(vselected!=null && (vselected.x == gr.vertices.get(key).x) && (vselected.y == gr.vertices.get(key).y)) {
				g.setColor(Color.RED);
			}
			else if(vhover!=null && (vhover.x == gr.vertices.get(key).x) && (vhover.y == gr.vertices.get(key).y)) {
				g.setColor(Color.RED);
			}
			else if(vtarget!=null && (vtarget.x == gr.vertices.get(key).x) && (vtarget.y == gr.vertices.get(key).y)) {
				g.setColor(Color.RED);
			}
			else if(vertexpath.contains(gr.vertices.get(key))) {
				g.setColor(Color.BLUE);
			}
			else {
				g.setColor(Color.BLACK);
			}

			g.fillOval(gr.vertices.get(key).x, gr.vertices.get(key).y, 15, 15);
		}
		
	}
	
	public boolean isinside(int cx, int cy, int rad, int x, int y) {
		if ((x - cx) * (x - cx) + (y - cy) * (y - cy) <= rad * rad) 
	            return true; 
	        else
	            return false; 
	}
	
	
	public void userinput() {
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				for(String find: gr.vertices.keySet()) {
					if(isinside((gr.vertices.get(find).x), (gr.vertices.get(find).y), 15, e.getX(), e.getY())) { 
						vselected = gr.vertices.get(find);
						repaint();
					}
				}
			}
			
		});
	}
	
	public void hover() {
		addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				if(vselected!=null) {
				for(Vertex key : gr.vertices.values()) {
					if(isinside(key.x, key.y, 15, e.getX(), e.getY())) {
						vhover = key;
						if(!vhover.equals(vselected) && !vhover.equals(vtarget)) {
							vtarget = key;
							dijkstra();
							repaint();
						}
					}
				}	
				}
			}
		});
	}
	
	public void dijkstra() {
		HashMap<Vertex, Integer> distances = new HashMap<Vertex, Integer>();
		PriorityQueue1 toVisit = new PriorityQueue1();
		HashMap<Vertex, Edge> leadsto = new HashMap<Vertex, Edge>();
		ArrayList<Vertex> visited = new ArrayList<Vertex>();
		
		for(Vertex v: gr.vertices.values()) {
			distances.put(v, Integer.MAX_VALUE);
		}
		
		toVisit.add(vselected, 0);
		distances.put(vselected, 0);
		//System.out.println("selected: " +vselected.place);
		//System.out.println("target: "+vtarget.place);
		
		int d = 0;
		while(!toVisit.isEmpty()) {
			Vertex trialselected = toVisit.pop();
			//System.out.println(trialselected.place);
			//System.out.println(trialselected.edges.size());
			visited.add(trialselected);
			if(trialselected.equals(vtarget)) {
				//System.out.println("completed");
				backtrace(leadsto);
				return;
			}
			
			for(Edge e: trialselected.edges) {
				Vertex neighbour = e.get(trialselected);
				
				d = distances.get(trialselected) + e.distance;
				if(!visited.contains(neighbour)) {
					if(d < distances.get(neighbour)) {
						//System.out.println(neighbour.place);
						//System.out.println("d: "+d+ " and the distances.getneighbour is: "+distances.get(neighbour));
						distances.put(neighbour, d);
						leadsto.put(neighbour, e);
						//System.out.println("is empty "+ toVisit.isEmpty());
						if(toVisit.contains(neighbour)) {
							//System.out.println("original:" +toVisit.getP(neighbour)+ " and the thing to update to is: "+d);
							toVisit.update(neighbour, d);
						}
						else {
							//System.out.println("inner else statement");
							toVisit.add(neighbour, d);
						}
					}
				}
			}
		}
	}
	
	
	
	public void backtrace(HashMap<Vertex, Edge> leadsto) {
		if(!edgepath.isEmpty()) 
			edgepath.clear();
		if(!vertexpath.isEmpty())
			vertexpath.clear();
		System.out.println("	Starting backtrace..");
		System.out.println("Target is: "+vtarget.place);
		Vertex currentVertex = vtarget;
		while(!currentVertex.equals(vselected)) {
			Edge e = leadsto.get(currentVertex);
			currentVertex = e.get(currentVertex);
			edgepath.add(e);
			vertexpath.add(currentVertex);
			System.out.println(currentVertex.place);
		}
		System.out.println("Starts at: " +vselected.place);
		repaint();

	}

	public static void main(String[] args) {
		try {
			BFSmap test = new BFSmap();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
