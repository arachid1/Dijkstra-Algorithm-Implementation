package dijkstra;

// Interface pour l'ensemble A (sommets dont on est sûr de la distance avec Dijkstra)
public interface ASetInterface 
{
	public void add(VertexInterface vertex); // ajoute le sommet à l'ensemble A

	public boolean contains(VertexInterface vertex); // vérifie l'appartenance du sommet à A
}
