package projet_odt;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Cette classe est une interface graphique qui s'affiche lorsqu'il y a un soucis avec les fichiers Parametre ou Stockage.
 * Un bouton permet redéfinir un dossier racine à partir de l'explorateur de fichier. Et ainsi les fichiers Parametre
 * et Stockage sont mis à jour automatiquement.
 * @author Berrabah/Belouahrani
 *
 */
public class ProblemeRacine extends JFrame  implements ActionListener  {
	
	private static final long serialVersionUID = 1L;
	
	private JFrame frame = new JFrame();
	private JPanel panel = new JPanel();
	private JPanel panel2 = new JPanel();
	
	private JButton choixFichierRacine = new JButton("Choix Dossier Racine");
	private JLabel probleme = new JLabel("Aucun dossier racine n'a été renseigné pour exécuter la recherche");
	private JLabel probleme2 = new JLabel("Veuillez en renseigner un.");
	
	public ProblemeRacine(){
		 
		super("Aucun dossier racine n'a été spécifié. Choisisser en un.");
		
		ImageIcon image = new ImageIcon(new ImageIcon("./images/icone.png").getImage().getScaledInstance(90,90,Image.SCALE_DEFAULT));
		JLabel label = new JLabel();
		label.setIcon(image);
		
		frame.setLayout(new FlowLayout());
		frame.add(panel,BorderLayout.WEST);
		frame.add(panel2,BorderLayout.EAST);
		//panel.setLayout(new BorderLayout());
		panel2.setLayout(new GridLayout(3,1));
		panel.add(label);
		panel2.add(probleme);
		panel2.add(probleme2);
		panel2.add(choixFichierRacine);
		
		choixFichierRacine.addActionListener(this);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setSize(600,150);
		setTitle("Renseigner le dossier racine");
		frame.setVisible(true);
		
		}

	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==choixFichierRacine){
			this.setVisible(false);
			RechercheGUI rgui = new RechercheGUI();
			rgui.parametreDossierRacine();
		}
	}
}