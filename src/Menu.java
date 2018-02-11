import java.awt.Component;
import java.awt.Window;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import maze.Maze;
import maze.MazeReadingException;
import fr.enst.inf103.ui.MazeViewController;
import fr.enst.inf103.ui.MazeViewSource;

// Classe implémentant l'interface MazeViewController
public class Menu 
implements MazeViewController
{
	private MazeViewSource maze;

	public final MazeViewSource newMaze() // Initialisation d'un labyrinthe vierge dans l'UI
	{
		try {
			Object[] choiceList = {"4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59","60","61","62","63","64","65","66","67","68","69","70","71","72","73","74","75","76","77","78","79","80","81","82","83","84","85","86","87","88","89","90","91","92","93","94","95","96","97","98","99","100"};
			Object choiceHeight = JOptionPane.showInputDialog(null, "Choisissez la hauteur du labyrinthe", "Hauteur", JOptionPane.INFORMATION_MESSAGE, null, choiceList, choiceList[0]);   
			Object choiceWidth = JOptionPane.showInputDialog(null, "Choisissez la largeur du labyrinthe", "Largeur", JOptionPane.INFORMATION_MESSAGE, null, choiceList, choiceList[0]); 
			int height = Integer.parseInt((String) choiceHeight);
			int width = Integer.parseInt((String) choiceWidth);
			maze = new Maze(height, width);			
			return maze;

		} catch (NumberFormatException e) {
			maze=null;
			System.err.println("Erreur: Aucun labyrinthe n'a été créé");
			Component frame = null;
			JOptionPane.showMessageDialog(frame, "Aucun labyrinthe n'a été créé !", "Erreur", JOptionPane.ERROR_MESSAGE);			
			return maze;
		}     	   
	}

	public final MazeViewSource openMaze(String fileName) // Initialisation d'un labyrinthe à partir d'un fichier dans l'UI
	{
		FileReader fr = null;
		BufferedReader br = null;		
		String line = null;	
		
		try {					            
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);	
			ArrayList<Integer> lengthList = new ArrayList<Integer>();				
			while ((line=br.readLine()) != null)
			{							
				lengthList.add(line.length());
			}						
			maze = new Maze(lengthList.size(), lengthList.get(0));
			((Maze) maze).initFromTextFile(fileName);
			return maze;

		} catch (MazeReadingException e) {
			maze = null;
			System.err.println(e.getMessage());		
			Component frame = null;
			JOptionPane.showMessageDialog(frame, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE); 
			return maze;
			
		} catch (FileNotFoundException e) {
			maze = null;
			System.err.println("Erreur: fichier non trouvé \"" + "fileName" + "\"");
			Component frame = null;
			JOptionPane.showMessageDialog(frame, "Erreur: fichier non trouvé \"" + "fileName" + "\"", "Erreur", JOptionPane.ERROR_MESSAGE);
			return maze;
			
		} catch (IOException e) {
			maze = null;
			System.err.println("Erreur: erreur de lecture du fichier \"" + "fileName" + "\"");
			Component frame = null;
			JOptionPane.showMessageDialog(frame, "Erreur: erreur de lecture du fichier \"" + "fileName" + "\"", "Erreur", JOptionPane.ERROR_MESSAGE);
			return maze;
			
		} catch (Exception e) {
			maze = null;
			Component frame = null;
			JOptionPane.showMessageDialog(frame, "Erreur inconnue !", "Erreur", JOptionPane.ERROR_MESSAGE);
			System.err.println("Erreur inconnue");			
			return maze;
			
		} catch (OutOfMemoryError e) {
			maze = null;
			Component frame = null;
			JOptionPane.showMessageDialog(frame, "Dépassement de mémoire !", "Erreur", JOptionPane.ERROR_MESSAGE);
			System.err.println("Erreur: dépassement de mémoire");
			return maze;
			
		} finally {
			if (fr != null)
			{
				try {fr.close(); } catch (Exception e) {};
			}
			
			if (br != null)
			{
				try {br.close(); } catch (Exception e) {};
			}			
		}
	}

	public final void saveMazeAs(String fileName) // enregistre le labyrinthe dans un fichier texte
	{
		try { 			
			((Maze) maze).saveToTextFile(fileName);
			return;	    

		} catch (FileNotFoundException e) {
			System.err.println("Erreur dans la classe Maze, initFromTextFile: fichier non trouvé \"" + "fileName" + "\"");
			Component frame = null;
			JOptionPane.showMessageDialog(frame, "Erreur dans la classe Maze, initFromTextFile: fichier non trouvé \"" + "fileName" + "\"", "Erreur", JOptionPane.ERROR_MESSAGE);
			return;
			
		} catch (Exception e) {
			System.err.println("Erreur: Aucun labyrinthe chargé donc sauvegarde impossible");
			Component frame = null;
			JOptionPane.showMessageDialog(frame, "Aucun labyrinthe chargé donc sauvegarde impossible !", "Erreur", JOptionPane.ERROR_MESSAGE);
			return;		  				    
		} 
	}

	public final MazeViewSource getMazeViewSource()
	{
		Window[] windows = Window.getWindows();
		for(int i=0 ; i < windows.length ; i++)
		{
			windows[i].repaint();				
		}
		return maze;
	}    

	public final void calculateShortestPath() // calcule le plus court chemin entre la case de départ et la case d'arrivée
	{   
		try {
			((Maze) maze).calculateShortestPath();	
			
		} catch (NullPointerException e) {
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
}
