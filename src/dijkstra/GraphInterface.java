package dijkstra;

import java.util.ArrayList;

// Interface du graphe
public interface GraphInterface
{
	public ArrayList<VertexInterface> getAllVertices(); // renvoie la liste de tous les sommets

	public ArrayList<VertexInterface> getSuccessors(VertexInterface vertex); // renvoie la liste des successeurs d'un sommet

	public int getWeight(VertexInterface departure, VertexInterface arrival); // renvoie le poids d'un arc

}