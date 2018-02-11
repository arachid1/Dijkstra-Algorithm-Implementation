import fr.enst.inf103.ui.MazeWindow;

// Classe d'exécution du programme
public class MainTest 
{
	public static void main(String[] args) 
	{		 
		System.setProperty("file.encoding", "UTF-8");
		Menu menu = new Menu();
		new MazeWindow("Labyrinthe - Godefroy GALAS & Maxime MATHERON", menu);
	}
}
