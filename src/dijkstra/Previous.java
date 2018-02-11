package dijkstra;

import java.util.ArrayList;
import java.util.Hashtable;

public class Previous
implements PreviousInterface
{
	private final Hashtable<VertexInterface, VertexInterface> table; 

	public Previous()
	{
		table = new Hashtable<VertexInterface, VertexInterface>(); // pour chaque sommet du graphe est indiqué son prédécesseur
	}

	public void setPrevious(VertexInterface vertex, VertexInterface previous) // définit pour un sommet son prédecesseur dans l'arborescence
	{
		table.put(vertex, previous);
	}

	public VertexInterface getPrevious(VertexInterface vertex) // renvoie pour un sommet son prédécesseur danns l'arborescence
	{
		return table.get(vertex);
	}

	public ArrayList<VertexInterface> getShortestPathTo(VertexInterface vertex) // renvoie l'arborescence d'un sommet du labyrinthe vers le départ
	{
		ArrayList<VertexInterface> path = new ArrayList<VertexInterface>();

		while (vertex != null)
		{
			path.add(vertex);
			vertex = getPrevious(vertex);
		}

		return path;
	}
}
