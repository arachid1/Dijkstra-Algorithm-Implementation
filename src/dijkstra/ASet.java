package dijkstra;

import java.util.HashSet;

// Classe ASet implémentant ASetInterface
public class ASet 
implements ASetInterface
{
	private final HashSet<VertexInterface> aSet;

	public ASet() // initialise la liste des sommets dont on est sûr de la distance avec l'algorithme de Dijkstra
	{
		aSet = new HashSet<VertexInterface>();
	}

	public void add(VertexInterface vertex)
	{
		aSet.add(vertex);
	}

	public boolean contains(VertexInterface vertex)
	{
		return aSet.contains(vertex);
	}	
}
