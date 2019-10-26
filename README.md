# GPS
This program helps find the shortest distance between two locations on a map. The code follows Dijkstra's algorithim which creates a tree of shortest paths from the initial location selected by the user to all other locations found on the map.
The code runs as follows:
  1. build the user interface/graphics
  --
  (the following will only be done once)
  2. Collect Data Method: having chosen the picture (here, a campus map of King's Academy), choose from the picture which locations will be vertices/destinations and adding their point location to a set.
  3. Write Data Method: from the set created, each destination/place will have the coordinations and all other locations it is connected to/creates paths with. 
  --
  4. Read File Methods: the text file created at the beginning will here be used to build the tree (the graph, nodes and edges). the vertex/node will represent one destination and the edge will represent the path to it, and subsequently, the distance between every two nodes.
  5. Dijkstra's Algorithim: after the code receives the initial vertex the user had chosen, it will evaluate the shortest path between its neighbouring vertices. Having established the neighbouring vertex with the shortest path from the initial, the code runs a recursive method that continues further down the tree until the target destination is reached with the shortest paths possible. 
  
