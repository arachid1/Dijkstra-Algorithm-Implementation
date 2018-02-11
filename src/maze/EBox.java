package maze;

import java.io.PrintWriter;

// Classe représentant les cases franchissables
public class EBox extends MBox 
{
	public EBox(Maze maze, int line, int column)
	{
		super(maze, line, column);
	}

	public final void writeCharTo(PrintWriter pw)
	{
		pw.print('E');
	}

	public final String getBoxSymbol()
	{
		return "E";
	}
}
