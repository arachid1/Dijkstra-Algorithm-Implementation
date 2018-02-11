package maze;

public class MazeReadingException extends Exception 
{
	public final static long serialVersionUID = 1L ;

	public MazeReadingException (String fileName, int lineNo, String errorMsg)
	{
		super("Erreur détectée pendant la lecture du labyrinthe dans " + fileName + "(" + lineNo + ")" + errorMsg);
	}
}
