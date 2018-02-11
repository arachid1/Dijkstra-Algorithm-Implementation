package dijkstra;

import java.util.Hashtable;

public class Pi 
implements PiInterface
{
	private final Hashtable<VertexInterface, Integer> table;

	public Pi()
	{
		table = new Hashtable<VertexInterface, Integer>();
	}

	public void setValue(VertexInterface vertex, int value) // définit pour un sommet sa distance au sommet de départ
	{
		table.put(vertex, new Integer(value));
	}

	public int getValue(VertexInterface vertex) // renvoie pour un sommet sa distance au sommet de départ
	{
		return table.get(vertex).intValue();
	}	
}
