package maze;

import java.io.PrintWriter;

// Classe représentant les cases INfranchissables
public class WBox extends MBox
{
	public WBox(Maze maze, int line, int column)
	{
		super(maze, line, column);
	}

	@Override
	public final boolean isAccessible() // Méthode utilisée par la méthode getSuccessors de la classe Maze
	{
		return false; // Case inaccessible
	}

	public final void writeCharTo(PrintWriter pw)
	{
		pw.print('W');
	}

	public final String getBoxSymbol()
	{
		return "W";
	}
}
