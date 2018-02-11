package maze;

import java.io.PrintWriter;

import maze.MBox;
import maze.Maze;

// Classe représentant le chemin à emprunter pour résoudre le labyrinthe
public class SBox extends MBox 
{
	public SBox(Maze maze, int line, int column)
	{
		super(maze, line, column);
	}

	public final void writeCharTo(PrintWriter pw)
	{
		pw.print('*');
	}

	public final String getBoxSymbol()
	{
		return "*";
	}
}
