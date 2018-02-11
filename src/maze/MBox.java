package maze;

import java.io.PrintWriter;
import dijkstra.VertexInterface;

// Classe générique pour les cases du labyrinthe
public abstract class MBox 
implements VertexInterface
{   
	private final Maze maze;
	private final int line;
	private final int column;

	public MBox(Maze maze, int line, int column)
	{
		this.maze = maze;
		this.column = column;
		this.line = line;
	}

	public abstract void writeCharTo(PrintWriter pw); // permet d'écrire le symbole de la case lors de l'enregistrement dans un fichier texte

	public abstract String getBoxSymbol(); // renvoie le symbole de case reconnu par l'interface graphique

	public final String getLabel() // renvoie les coordonnées de la case
	{
		return "(" + line + "," + column + ")";
	}

	public final int getLine()
	{
		return line;
	}

	public final int getColumn()
	{
		return column;
	}

	public final Maze getMaze()
	{
		return maze;
	}

	public boolean isAccessible() // Méthode utilisée par la méthode getSuccessors de la classe Maze
	{
		return true;
	}
}
