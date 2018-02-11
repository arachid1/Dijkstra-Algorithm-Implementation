package maze;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import dijkstra.Dijkstra;
import dijkstra.GraphInterface;
import dijkstra.Previous;
import dijkstra.VertexInterface;
import fr.enst.inf103.ui.MazeView;
import fr.enst.inf103.ui.MazeViewSource;

// Classe du labyrinthe
public class Maze 
implements GraphInterface, MazeViewSource	
{
	private int WIDTH;
	private int HEIGHT;	
	private MBox [][] boxes;
	private VertexInterface departure;
	private VertexInterface arrival;	
	private boolean isSolved = false;

	public Maze(int height, int width) // initialise le labyrinthe avec un cadre de cases WBox
	{ 			
		HEIGHT = height+2;
		WIDTH = width+2;
		boxes = new MBox [HEIGHT][WIDTH];

		for (int i=0 ; i < HEIGHT ; i++)
		{
			boxes[i][0]=new WBox(this, i, 0);
			boxes[i][WIDTH-1]=new WBox(this, i, WIDTH-1);
		}

		for (int j=1 ; j < WIDTH-1 ; j++)
		{
			boxes[0][j]=new WBox(this, 0, j);
			boxes[HEIGHT-1][j]=new WBox(this, HEIGHT-1, j);
		}

		for (int i=1 ; i < HEIGHT-1 ; i++)
		{
			for (int j=1 ; j < WIDTH-1 ; j++)
			{					
				setBox(i, j, 'E');					
			}
		}	
	}	 

	public final MBox getBox(int line, int column) // renvoie la case aux coordonnées demandées
	{
		return boxes[line][column];
	}

	private final void setBox(int line, int column, char symbol) // méthode appelée par le constructeur pour créer un labyrinthe vierge
	{
		switch (symbol) 
		{
		case 'D' :
			boxes[line][column] = new DBox(this, line, column); break;
		case 'A' :
			boxes[line][column] = new ABox(this, line, column); break;
		case 'W' :
			boxes[line][column] = new WBox(this, line, column); break;
		case 'E' :
			boxes[line][column] = new EBox(this, line, column); break; 			
		default :
			return;
		}
	}

	public final ArrayList<VertexInterface> getAllVertices() // renvoie la liste de toutes les cases du labyrinthe
	{
		ArrayList<VertexInterface> allVertices = new ArrayList<VertexInterface>();

		for (int line = 0 ; line < HEIGHT ; line++)
		{
			MBox[] theLine = boxes[line];
			for (int column = 0 ; column < WIDTH ; column++)
				allVertices.add(theLine[column]);
		}

		return allVertices;
	}

	public final ArrayList<VertexInterface> getSuccessors(VertexInterface vertex) // renvoie la liste des cases voisines accessibles
	{
		ArrayList<VertexInterface> successors = new ArrayList<VertexInterface>();
		MBox box = (MBox)vertex ; // Transtypage

		int line = box.getLine();
		int column = box.getColumn();

		if (line > 0) // Voisins du haut
		{ 
			MBox neighbor = boxes[line-1][column];
			if (neighbor.isAccessible())
				successors.add(neighbor);
		}

		if (line < HEIGHT-1) // Voisins du bas
		{ 
			MBox neighbor = boxes[line+1][column];
			if (neighbor.isAccessible())
				successors.add(neighbor);
		}

		if (column > 0) // Voisins de gauche
		{ 
			MBox neighbor = boxes[line][column-1];
			if (neighbor.isAccessible())
				successors.add(neighbor);
		}

		if (column < WIDTH-1) // Voisins de droite
		{ 
			MBox neighbor = boxes[line][column+1];
			if (neighbor.isAccessible())
				successors.add(neighbor);
		}	
		return successors;
	}

	public final int getWeight(VertexInterface src, VertexInterface dst) // Chaque arc entre deux cases a un poids de 1
	{
		return 1;
	}

	public final void initFromTextFile(String fileName) throws MazeReadingException, FileNotFoundException, IOException, Exception
	// permet l'initialisation du labyrinthe à partir d'un fichier texte
	{			  
		FileReader frMaze = new FileReader(fileName);
		BufferedReader brMaze = new BufferedReader(frMaze);	

		try {
			for (int lineNo = 1 ; lineNo < HEIGHT-1 ; lineNo++) 
			{				  
				String line = brMaze.readLine();

				if (line == null)
					throw new MazeReadingException(fileName, lineNo, ": pas assez de ligne");
				if (line.length() < WIDTH-2)
					throw new MazeReadingException(fileName, lineNo, ": la ligne est trop courte");
				if (line.length() > WIDTH-2)
					throw new MazeReadingException(fileName, lineNo, ": la ligne est trop longue");

				for (int colNo = 1 ; colNo < WIDTH-1 ; colNo++)
				{
					switch (line.charAt(colNo-1)) 
					{
					case 'D' :
						boxes[lineNo][colNo] = new DBox(this, lineNo, colNo); break;
					case 'A' :
						boxes[lineNo][colNo] = new ABox(this, lineNo, colNo); break;
					case 'W' :
						boxes[lineNo][colNo] = new WBox(this, lineNo, colNo); break;
					case 'E' :
						boxes[lineNo][colNo] = new EBox(this, lineNo, colNo); break;         	
					default :
						throw new MazeReadingException(fileName, lineNo, ": caractère inconnu '" + boxes[lineNo][colNo] + "'" );
					}
				} 
			}

		} finally {
			if (frMaze != null)
			{	
				try {frMaze.close(); } catch (Exception e) {};
			}

			if (brMaze != null)
			{
				try {brMaze.close(); } catch (Exception e) {};		
			}
		}

	}

	public final void saveToTextFile(String fileName) throws FileNotFoundException, IOException, Exception
	// permet d'enregistrer le labyrinthe courant dans un fichier texte
	{		
		if (isSolved)
		{
			System.err.println("Erreur: Vous ne pouvez pas éditer cette case");
			Component frame = null;
			JOptionPane.showMessageDialog(frame, "Labyrinthe résolu ! Vous ne pouvez pas le sauvegarder !", "Attention !", JOptionPane.WARNING_MESSAGE);
			return;
		}

		PrintWriter pwMaze = new PrintWriter(fileName);

		try {			
			for (int lineNo = 1 ; lineNo < HEIGHT-1 ; lineNo++)			
			{
				MBox[] line = boxes[lineNo] ;
				for (int colNo = 1 ; colNo < WIDTH-1 ; colNo++)
				{			    	 
					line[colNo].writeCharTo(pwMaze);
				} 
				pwMaze.println();			        
			}

		} finally {

			if (pwMaze != null)
			{
				try {pwMaze.close(); } catch (Exception e) {};		
			}		
		}
	}

	public int getWidth() // Getter de l'attribut WIDTH
	{
		return WIDTH;
	}

	public int getHeight() // Getter de l'attribut HEIGHT
	{
		return HEIGHT;
	}

	public String getSymbolForBox(int row, int column) // renvoie le symbole de la box (méthode appelée par l'interface graphique)
	{		
		try {
			String symbol = boxes[row][column].getBoxSymbol();
			return symbol;

		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Erreur: Vous ne pouvez pas éditer cette case");
			Component frame = null;
			JOptionPane.showMessageDialog(frame, "Vous ne pouvez pas éditer cette case !", "Attention !", JOptionPane.WARNING_MESSAGE);
			return null;

		} catch (NullPointerException e) {	
			System.err.println("Erreur: Vous ne pouvez pas éditer cette case");
			Component frame = null;
			JOptionPane.showMessageDialog(frame, "Vous ne pouvez pas éditer cette case !", "Attention !", JOptionPane.WARNING_MESSAGE);
			return null;			
		}
	}	

	public boolean drawMaze(Graphics g, MazeView mazeView) // on utilise l'implémentation incluse de la méthode par le .jar (Built-in)
	{
		Window[] windows = Window.getWindows();
		for(int i=0 ; i < windows.length ; i++)
		{
			windows[i].repaint();				
		}
		
		return false;
	}	

	public boolean handleClick(MouseEvent e, MazeView mazeView) // on utilise l'implémentation incluse de la méthode par le .jar (Built-in)
	{
		return false;
	}

	public void setSymbolForBox(int row, int column, String symbol) // permet de changer le type de case
	{   
		if (row==0 || column==0 || row==HEIGHT-1 || column==WIDTH-1)
		{
			System.err.println("Erreur: Vous ne pouvez pas éditer cette case");
			Component frame = null;
			JOptionPane.showMessageDialog(frame, "Vous ne pouvez pas éditer cette case !", "Attention !", JOptionPane.WARNING_MESSAGE);
			return;
		}     

		else if (isSolved)
		{
			ask(departure, arrival);
			return;
		}

		else
		{    	
			try{
				switch (symbol)     	
				{
				case "D" :
					boxes[row][column] = new DBox(this,row,column); break;
				case "A" :
					boxes[row][column] = new ABox(this,row,column); break;
				case "W" :
					boxes[row][column] = new WBox(this,row,column); break;
				case "E" :
					boxes[row][column] = new EBox(this,row,column); break;  
				case "*" :
					boxes[row][column] = new SBox(this,row,column); break;        		
				default :  
					return;
				}

			}  catch (IndexOutOfBoundsException e) {    	              
				System.out.println("Vous ne pouvez pas éditer cette case");
				Component frame = null;
				JOptionPane.showMessageDialog(frame, "Vous ne pouvez pas éditer cette case !", "Information", JOptionPane.WARNING_MESSAGE);	

			} catch (NullPointerException e) {
				System.out.println("Vous ne pouvez pas éditer cette case");
				Component frame = null;
				JOptionPane.showMessageDialog(frame, "Vous ne pouvez pas éditer cette case !", "Information", JOptionPane.WARNING_MESSAGE);				   				
			}
		}
	}     

	public boolean handleKey(KeyEvent e, MazeView mazeView) // on utilise l'implémentation incluse de la méthode par le .jar (Built-in)
	{   	
		return false;
	}

	public boolean isValid() // permet de déterminer si le labyrinthe est conforme aux règles imposées
	{
		int a=0;
		int d=0;
		for (int i=0 ; i < HEIGHT ; i++)
		{
			for (int j=0 ; j < WIDTH ; j++)
			{
				if (this.getSymbolForBox(i,j).equals("D"))
				{
					d=d+1;
				}

				if (this.getSymbolForBox(i,j).equals("A"))
				{
					a=a+1;
				}
			}
		}

		if (!(a==1 && d==1))
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	public void calculateShortestPath() throws NullPointerException // calcule le plus court chemin entre la case de départ et la case d'arrivée
	{		
		try {   	    
			if (isSolved)
			{
				ask(departure, arrival);
			}

			if (isValid())
			{     	   	   	
				for (int i=0 ; i < HEIGHT ; i++)
				{
					for (int j=0 ; j < WIDTH ; j++)    				
					{
						if (getSymbolForBox(i,j).equals("D"))
						{
							departure = getBox(i,j);
						}

						if (getSymbolForBox(i,j).equals("A"))
						{
							arrival = getBox(i,j);    						
						}
					}
				}         	

				Previous p = (Previous) Dijkstra.dijkstra(this, departure);      	    	
				ArrayList<VertexInterface> shortestPath = p.getShortestPathTo(arrival);

				if (!(isSolvable(shortestPath)))
				{
					System.err.println("Erreur: Le labyrinthe n'est pas solvable");
					Component frame = null;
					JOptionPane.showMessageDialog(frame, "Le labyrinthe n'est pas solvable !", "Erreur", JOptionPane.ERROR_MESSAGE); 
					return;
				}

				else 
				{
					printShortestPath(shortestPath); 
					isSolved=true;
					ask(departure, arrival);					
					return;
				} 
			}

			else
			{
				System.err.println("Erreur: Le labyrinthe n'est pas valide");
				Component frame = null;
				JOptionPane.showMessageDialog(frame, "Le labyrinthe n'est pas valide !", "Erreur", JOptionPane.ERROR_MESSAGE); 
				return;
			} 

		} catch(NullPointerException e){
			System.err.println("Erreur: Aucun labyrinthe n'est chargé");
			Component frame = null;
			JOptionPane.showMessageDialog(frame, "Aucun labyrinthe n'est chargé !", "Erreur", JOptionPane.ERROR_MESSAGE);  

		} finally {
			Window[] windows = Window.getWindows();
			for(int i=0 ; i < windows.length ; i++)
			{
				windows[i].repaint();				
			}
		}
	}  

	private void printShortestPath(ArrayList<VertexInterface> shortestPath) // affiche le plus court chemin en vert sur le labyrinthe 
	{
		int x=0;
		int y=0;		
		Iterator<VertexInterface> it = shortestPath.iterator();
		if(shortestPath != null)
		{
			while(it.hasNext())
			{
				MBox box = (MBox)it.next();
				x = box.getLine();
				y = box.getColumn();
				setSymbolForBox(x, y, "*"); 
			}			
		}
	}

	private boolean isSolvable(ArrayList<VertexInterface> shortestPath)
	{
		if (shortestPath.size()<2)
		{
			return false; 
		}

		else
		{
			return true;
		}
	}   

	private void ask(VertexInterface d, VertexInterface a)
	{				
		int option = JOptionPane.showConfirmDialog(null, "Le labyrinthe est résolu ! Voulez-vous effacer le chemin de résolution ? ", "Bravo !", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);					
		if(option == JOptionPane.OK_OPTION)
		{
			for (int i=1 ; i < HEIGHT-1 ; i++)
			{
				for (int j=1 ; j < WIDTH-1 ; j++)
				{					
					if(getSymbolForBox(i,j)=="*")
					{
						setBox(i, j, 'E');
					}
				}
			}	

			setBox(((MBox) d).getLine(), ((MBox) d).getColumn(), 'D');
			setBox(((MBox) a).getLine(), ((MBox) a).getColumn(), 'A');		
			isSolved=false;
		}
	}	
}
	