package projet_odt;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * Simple interface d'aide, expliquant le but du logiciel et comment l'utiliser. 
 * @author Berrabah/Belouahrani
 *
 */
public class Aide extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JFrame frame = new JFrame();
	public JPanel panel = new JPanel();
	
	public JLabel description = new JLabel("Ce logiciel a pour but de rechercher les fichiers ODT présents à "
			+ "partir d'un dossier racine choisis par l'utilisateur. ");
	public JLabel description2 = new JLabel("Le logiciel possède également un moteur de recherche permettant"
			+ " de retrouver les fichiers ODT associés aux mots clés saisis par l'utilisateur");
	public JLabel bouton = new JLabel("Description des boutons : ");
	public JLabel liste = new JLabel("Liste des Fichiers ODT : permet d'afficher la liste des fichiers ODT "
			+ "retrouvés par le logiciel.");
	public JLabel racine = new JLabel("Choisissez le dossier racine : permet à l'utilisateur de choisir le"
			+ " dossier racine à partir duquel les fichiers ODT seront stocker et traiter.");
	public JLabel recherche = new JLabel("Lancer la recherche : lance une recherche parmis les fichiers ODT stockés,"
			+ "et liste ceux qui contiennent les mots clés saisis.");
	public JLabel ouvrir = new JLabel("Ouvrir dossier conteneur : copier le chemin du DOSSIER où se trouve le fichier"
			+ " que vous venez de retrouver grace au logiciel, et ce bouton vous permettra d'acceder à "
			+ "l'explorateur de fichier directement placé au dossier souhaité.. ");
	public JLabel reinit = new JLabel("Fichier > Réinitialiser: Redémarre le logiciel en vidant tous les "
			+ "parametres stockés");
	
	 public Aide(){
		
		JPanel b1 = new JPanel();
	    
	    b1.setLayout(new BoxLayout(b1, BoxLayout.LINE_AXIS));
	    b1.add(description);
	    b1.add(description2);

	    JPanel b2 = new JPanel();
	    
	    b2.setLayout(new BoxLayout(b2, BoxLayout.LINE_AXIS));
	    b2.add(bouton);
	    

	    JPanel b3 = new JPanel();
	   
	    b3.setLayout(new BoxLayout(b3, BoxLayout.LINE_AXIS));
	    b3.add(liste);
	    
	    JPanel b4 = new JPanel();
		   
	    b4.setLayout(new BoxLayout(b4, BoxLayout.LINE_AXIS));
	    b4.add(ouvrir);
	    
	    JPanel b5 = new JPanel();
		   
	    b5.setLayout(new BoxLayout(b5, BoxLayout.LINE_AXIS));
	    b5.add(recherche);
	    
	    JPanel b6 = new JPanel();
		   
	    b6.setLayout(new BoxLayout(b6, BoxLayout.LINE_AXIS));
	    b6.add(racine);
	    
	    JPanel b7 = new JPanel();
		   
	    b7.setLayout(new BoxLayout(b7, BoxLayout.LINE_AXIS));
	    b7.add(reinit);
	    
	    JPanel b8 = new JPanel();
	    
	    b8.setLayout(new BoxLayout(b8, BoxLayout.PAGE_AXIS));
	    b8.add(b1);
	    b8.add(b2);
	    b8.add(b3);
	    b8.add(b4);
	    b8.add(b6);
	    b8.add(b7);
		
	    frame.add(b8);	
		frame.pack();
		setTitle("Renseigner le dossier racine");
		frame.setVisible(true);
	}
}
