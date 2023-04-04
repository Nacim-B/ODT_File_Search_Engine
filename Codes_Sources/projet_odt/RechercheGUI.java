package projet_odt;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * La classe RechercheGUI gère l'interface du logiciel.
 * @author Berrabah / Belouahrani
 *
 */
public class RechercheGUI extends JFrame implements ActionListener{
	
	
	private static final long serialVersionUID = 1L;
	
	private JFrame frame = new JFrame("Fenetre");
	private JPanel panel = new JPanel();
	private JPanel panel1 = new JPanel();
	private JPanel panel2 = new JPanel();
	private JPanel panel3= new JPanel();
	private JPanel panel4= new JPanel();
	
	private JLabel mot_a_chercher = new JLabel("Mot(s) à chercher :");
	private JLabel ouvrirLabel = new JLabel("Copier l'adresse du dossier à retrouver.");
	
	private JTextField txt_mot_cle = new JTextField(20);
	private JTextField ouvrir = new JTextField(10);
	private JTextArea resultatODT = new JTextArea(10,30);
	private JTextArea resultatRecherche = new JTextArea(10,30);
	
	private JButton ouvrirBouton = new JButton("Ouvrir");
	private JButton lancer_recherche = new JButton("Lancer la recherche");
	private JButton liste_odt = new JButton("Liste des Fichiers ODT");
	private JButton choixFichierRacine = new JButton("Choisissez le dossier racine.");
	
	private JScrollPane derouleur ;
	private JScrollPane derouleur2;
	
	private JMenuBar menu_bar = new JMenuBar();

	private JMenu fichier = new JMenu("Fichier");
	private JMenu edition = new JMenu("Edition");
	private JMenu parametre = new JMenu("Parametre");
	private JMenu aide = new JMenu("Aide");
	
	private JMenuItem reset = new JMenuItem("Réinitialiser");
	private JMenuItem quitter = new JMenuItem("Quitter");
	private JMenuItem listeODT = new JMenuItem("Lister Fichiers ODT");
	private JMenuItem lancerRecherche = new JMenuItem("Lancer Recherche");
	private JMenuItem dossierRacine = new JMenuItem("Choix du Dossier Racine");
	private JMenuItem ficheAide = new JMenuItem("Mode Emploi logiciel");

	
	private static Recherche recherche ;
	
	/**
	 * Constructeur de l'interface.
	 * Initialisation des paramètres graphiques.
	 * Une nouvelle Recherche est lancé afin que les méthodes de la classe Recherche puisse être utilisées.
	 * 
	 */
	public RechercheGUI(){
		recherche = new Recherche();
		init();
	}
	

	/**
	 * Initialisation des paramètres graphiques.
	 */
	private void init(){
		
		frame.getContentPane().setLayout(new GridLayout(1,2));
		frame.getContentPane().add(panel);
		
		frame.getContentPane().add(panel1);
		
		panel.setLayout(new FlowLayout());
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.PAGE_AXIS));
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.LINE_AXIS));
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.LINE_AXIS));
		panel4.setLayout(new BoxLayout(panel4, BoxLayout.LINE_AXIS));
		
		panel.add(liste_odt);
		panel.add(choixFichierRacine);
		panel.add(resultatODT);
		
		panel2.add(mot_a_chercher);
		panel2.add(txt_mot_cle);
		panel2.add(lancer_recherche);
		
		panel3.add(resultatRecherche);
		
		panel4.add(ouvrirLabel);
		panel4.add(ouvrir);
		panel4.add(ouvrirBouton);
		
		panel1.add(panel2);
		panel1.add(panel3);
		panel1.add(panel4);
		
		initDerouleur();
		
		panel.add(derouleur);
		panel3.add(derouleur2);
		
		initMenu();
		
		resultatODT.setEditable(false);
		resultatRecherche.setEditable(false);
		
		initActionListener();
		
		frame.setResizable(false);
		frame.setSize(1000,300);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setTitle("Moteur de recherche de fichiers ODT");
		frame.setVisible(true);
	}
	
	/**
	 * Iniatialisation des écouteurs des boutons.
	 */
	public void initActionListener(){
		ouvrirBouton.addActionListener(this);
		listeODT.addActionListener(this);
		liste_odt.addActionListener(this);
		lancerRecherche.addActionListener(this);
		lancer_recherche.addActionListener(this);
		reset.addActionListener(this);
		quitter.addActionListener(this);
		dossierRacine.addActionListener(this);
		choixFichierRacine.addActionListener(this);
		ficheAide.addActionListener(this);
	}
	
	/**
	 * Initialisation des dérouleurs utilisés dans JTextArea.
	 */
	public void initDerouleur(){

		derouleur = new JScrollPane(resultatODT,
	            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		derouleur2 = new JScrollPane(resultatRecherche,
	            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		derouleur.setBounds(new Rectangle(-4, 1, 300, 198));
		derouleur2.setBounds(new Rectangle(-4, 1, 300, 198));
		
	}
	
	/**
	 * Cette méthode permet de lancer l'explorateur de fichier, avec seul possiblité de choisir un repertoire.
	 * Une fois le dossier choisi, son chamin est enregistré dans un fichier Parametre.
	 */
	public void parametreDossierRacine(){
		JFileChooser chooser = new JFileChooser(); ;
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = chooser.showOpenDialog(choixFichierRacine);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				BufferedWriter bf = new BufferedWriter(new FileWriter("parametre.txt",false));
				bf.write(chooser.getSelectedFile().getAbsolutePath());
				Recherche r = new Recherche();
				r.viderStockage();
				resultatODT.setText( r.lireFichiersODT(new File(chooser.getSelectedFile().getAbsolutePath())));
				bf.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	 
	/**
	 * Initialisation de la barre de menu.
	 */
	public void initMenu(){
		fichier.add(reset);
		fichier.addSeparator();
		fichier.add(quitter);
		
		edition.add(listeODT);
		edition.add(new JSeparator());
		edition.add(lancerRecherche);
		parametre.add(dossierRacine);
		aide.add(ficheAide);
		
		menu_bar.add(fichier);
		
		menu_bar.add(edition);

		menu_bar.add(parametre);
		
		menu_bar.add(aide);
		
		frame.setJMenuBar(menu_bar);
	}
	
	
	 /**
	  * Affectation des actions à effectuer lors l'utilisation d'un bouton.
	  */
	public void actionPerformed(ActionEvent arg0) {
		Recherche r = new Recherche();
		if(arg0.getSource() == choixFichierRacine || arg0.getSource() == dossierRacine){
			parametreDossierRacine();
		}
		
		else if(arg0.getSource() == liste_odt||arg0.getSource() == listeODT){
			recherche.resetLigne(recherche.getListe());
			try {
				recherche.viderStockage();
				if(recherche.fichierParametreExiste()&&!recherche.fichierParametreEstVide()){
					BufferedReader br = new BufferedReader(new FileReader("parametre.txt"));
					resultatODT.setText(recherche.lireFichiersODT(new File(br.readLine())));
					br.close();
				}else new ProblemeRacine();
				
			}catch (FileNotFoundException e) {
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		else if(arg0.getSource() == lancer_recherche || arg0.getSource() == lancerRecherche){
			recherche.resetLigne2(recherche.getListe2());
			
			if(recherche.fichierParametreExiste()&&!recherche.fichierParametreEstVide()
					&& recherche.fichierStockageExiste()&&!recherche.fichierStockageEstVide()){
				resultatRecherche.setText(recherche.rechercherParMotCle(txt_mot_cle.getText()));
				
			
			}else new ProblemeRacine();
		}
		
		else if(arg0.getSource()==ouvrirBouton){
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File(ouvrir.getText()));
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.showOpenDialog(ouvrirBouton);
		}
		
		
		else if(arg0.getSource() == reset){
			r.viderStockage();
			r.viderParametre();
			frame.setVisible(false);
			new RechercheGUI();
		}
		
		else if(arg0.getSource()==quitter){
			frame.setVisible(false);
		}
		
		else if(arg0.getSource()==ficheAide){
			new Aide();
		}

		}
	

}
