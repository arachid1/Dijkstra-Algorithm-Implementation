package dijkstra;

import java.util.ArrayList;

// Interface de la fonction père dans le graphe
public interface PreviousInterface 
{
	public void setPrevious(VertexInterface vertex, VertexInterface previous); // affecte un père à un sommet

	public VertexInterface getPrevious(VertexInterface vertex); // renvoie le père d'un sommet

	public ArrayList<VertexInterface> getShortestPathTo(VertexInterface vertex); // renvoie la liste des sommets du chemin le plus court vers un autre sommet
}
