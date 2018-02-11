package maze;

import java.io.PrintWriter;

// Classe représentant les cases d'arrivée
public class ABox extends MBox 
{
	public ABox(Maze maze, int line, int column)
	{
		super(maze, line, column);
	}

	public final void writeCharTo(PrintWriter pw)
	{
		pw.print('A');
	}

	public final String getBoxSymbol()
	{
		return "A";
	}
}
