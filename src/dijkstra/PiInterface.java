package dijkstra;

// Interface de la fonction utilisée pour le calcul des distances dans l'algorithme
public interface PiInterface 
{
	public void setValue(VertexInterface vertex, int value); // fixe la distance pour un sommet

	public int getValue(VertexInterface vertex); // renvoie la valeur de la distance pour un sommet
}
