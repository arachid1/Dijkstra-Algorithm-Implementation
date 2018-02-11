package maze;

import java.io.PrintWriter;

// Classe représentant les classes de départ
public class DBox extends MBox 
{
	public DBox(Maze maze, int line, int column)
	{
		super(maze, line, column);
	}

	public final void writeCharTo(PrintWriter pw)
	{
		pw.print('D');
	}

	public final String getBoxSymbol()
	{
		return "D";
	}
}
