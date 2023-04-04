package projet_odt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * La classe Recherche possède les méthodes permettants de rechercher et stocker des informations sur des fichiers de type ODT.
 * C'est elle qui possède le main et qui est donc appelée pour lancer l'application.
 * Elle possède aussi des méthodes qui gèrent les fichiers "stockage" et "parametre".
 * 
 * @author Berrabah / Belouahrani
 * 
 */

public class Recherche{
	
	/**
	 * Le separateur des differents champs stockés dans le fichier stockage.
	 */
	private static String SEPARATOR = "#";
	
	/**
	 * Le separateur des mots clés saisies pour une recherche par l'utilisateur.
	 */
	private static String SEPARATOR2= " ";
	
	/**
	 * Le dezippeur permettant de décompresser un fichier ODT.
	 */
	private static ZipFile zFile;
	
	/**
	 * Liste recevra le nom de tous les fichiers ODT présents dans une arborescance.
	 * @see Recherche#getListe()
	 */
	private String liste ="";
	
	/**
	 * Liste2 recevra tous les fichiers ODT retrouver à partir d'une recherche par mot clés.
	 * @see Recherche#getListe2()
	 */
	private String liste2="";
    
	/**
	 * Le constructeur de l'objet Recherche.
	 */
	public Recherche(){

	}
	
	/**
	 * 
	 * @param liste des fichiers ODT collectés à partir d'un dossier racine.
	 * Cette méthode réinitialise la liste. 
	 */
	public void resetLigne(String liste){
		this.liste="";
	}
	
	/**
	 * 
	 * @param liste des fichiers ODT collectés à partir d'une recherche par mots clés.
	 * Cette méthode réinitialise la liste. 
	 */
	public void resetLigne2(String liste){
		this.liste2=" ";
	}
	
	/**
	 * 
	 * @return la liste des fichiers ODT collectés à partir d'un dossier racine.
	 */
	public String getListe(){
		return liste;
	}
	
	/**
	 * 
	 * @return la liste des fichiers ODT collectés à partir d'une recherche par mots clés.
	 */
	public String getListe2(){
		return liste2;
	}
	
	/**
	 * 
	 * @param odtFile qui sera analysé et dont les titres/sous titres seront stockés.
	 */
	public void stockerMotsCles(File odtFile){
			try {
					zFile= new ZipFile(odtFile);
					ZipEntry contentFile = zFile.getEntry("content.xml");
					
					DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
					DocumentBuilder db = dbf.newDocumentBuilder();
					Document document = db.parse(new InputSource(zFile.getInputStream(contentFile)));
					document.normalize();
				
					NodeList root = document.getElementsByTagName("office:text");
					Node rootNode = root.item(0);
					Element rootElement = (Element)rootNode;
					
					NodeList textList = rootElement.getElementsByTagName("text:title");
					NodeList textList2 = rootElement.getElementsByTagName("text:h");
					
					BufferedWriter writer = new BufferedWriter(new FileWriter("stockage.txt",true));
					
					for(int i=0;i<textList.getLength();i++){
						Node leTexte = textList.item(i);
						Element texteElement= (Element) leTexte;
						writer.write(leTexte.getNodeName()+SEPARATOR+texteElement.getAttribute("text:outline-level")
								+SEPARATOR+odtFile.getAbsolutePath()
								+SEPARATOR+leTexte.getTextContent());
						writer.newLine();
						System.out.println(leTexte.getNodeName()+SEPARATOR+leTexte.getTextContent());
					}
					for(int i=0;i<textList2.getLength();i++){
						Node leTexte = textList2.item(i);
						Element texteElement= (Element) leTexte;
						writer.write(leTexte.getNodeName()+SEPARATOR+texteElement.getAttribute("text:outline-level")
								+SEPARATOR+odtFile.getAbsolutePath()
								+SEPARATOR+leTexte.getTextContent());
						writer.newLine();
						System.out.println(leTexte.getNodeName()+SEPARATOR+leTexte.getTextContent());
					}
					
					
					writer.close();
					}catch (IOException e){
							System.err.println(e.getMessage());
					}catch (ParserConfigurationException e){
					e.printStackTrace();
					}catch (SAXException e) {
					e.printStackTrace();
					}
		}
	
	/**
	 * 
	 * @param repertoire à partir duquel la liste des fichiers ODT sera établie.
	 * @return la liste des fichiers ODT retrouvé.
	 * 
	 * Cette méthode appelle la méthode stockerMotClés en faisant en sorte que dès qu'un fichier ODT est retrouvé
	 * ses informations soit directement stockées.
	 */
	public String lireFichiersODT( File repertoire ) {
		
		if(repertoire.getName().endsWith(".odt")){
			stockerMotsCles( new File(repertoire.getAbsolutePath()));
			System.out.println(repertoire.getAbsolutePath());
			System.out.println("#######################");
			liste= liste + repertoire.getAbsolutePath() + "\n";
		}
		
        if ( repertoire.isDirectory ( ) ) {
                File[] list = repertoire.listFiles();
                if (list != null){
	                for ( int i = 0; i < list.length; i++) {
	                	lireFichiersODT( list[i]);
	                } 
                }else System.err.println(repertoire + " : Erreur de lecture.");
        }
        return liste;
	} 

	/**
	 * 
	 * @param mot_cle tapé par l'utilisateur dans le moteur de recherche
	 * @return la liste des fichiers ODT qui contiennent les mots clés saisis par l'utilisateur.
	 * 
	 * Cette méthode analyse le fichier "stockage" avec les mot clés récuperés en parametre.
	 */
	public String rechercherParMotCle(String mot_cle){
		try {
			String line, fields[],fields2[];
			fields2 = mot_cle.split(SEPARATOR2);
			
			for(int j=0;j<fields2.length;j++){
				
				BufferedReader reader = new BufferedReader(new FileReader("stockage.txt"));
				while ((line = reader.readLine()) != null) {
					fields = line.split(SEPARATOR);
				
					if(fields[0].equals("text:title")&&(fields[3].toLowerCase().contains(fields2[j].toLowerCase()) && (!liste2.contains(fields[2])))){
						System.out.println(fields[2]);
						liste2= liste2 + fields[2]+"\n";
					}
				}reader.close();
			}
				
			
			for(int i=1;i<=4;i++){
				
			for(int k=0;k<fields2.length;k++){
			
				BufferedReader reader2 = new BufferedReader(new FileReader("stockage.txt"));
				while ((line = reader2.readLine()) != null) {
					fields = line.split(SEPARATOR);
				
					if((fields[0].equals("text:h"))&&(Integer.valueOf(fields[1]).equals(i))&&(fields[3].toLowerCase().contains(fields2[k].toLowerCase()))&&(!liste2.contains(fields[2]))){
							System.out.println(fields[2]);
							liste2=liste2+fields[2]+"\n";
						}
					}reader2.close();
				}
			}		
		}catch (FileNotFoundException e){
			System.err.println(e.getMessage());
		}catch (IOException e){
			System.err.println(e.getMessage());
		}
		
		return liste2;
	}
	
	/**
	 * Vide le fichier "stockage".
	 */
	public void viderStockage(){
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter("stockage.txt"));
			writer.write("");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Vide le fichier "parametre".
	 */
	public void viderParametre(){
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter("parametre.txt"));
			writer.write("");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return vrai si le fichier Parametre est vide.
	 */
	public boolean fichierParametreEstVide(){
		try {
			BufferedReader br = new BufferedReader(new FileReader("parametre.txt"));
			if(br.readLine()==null) {
				br.close();
				return true;
			}
			
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 return false;
	}

	/**
	 * 
	 * @return vrai si le fichier Parametre existe.
	 */
	public boolean fichierParametreExiste(){
		File fichier = new File("parametre.txt");
		if(fichier.exists()){
			return true;
		}
		else return false;
		
	}

	/**
	 * 
	 * @return vrai si le fichier Stockage est vide.
	 */
	public boolean fichierStockageEstVide(){
		try {
			BufferedReader br = new BufferedReader(new FileReader("stockage.txt"));
			if(br.readLine()==null) {
				br.close();
				return true;
			}
			
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 return false;
	}

	/**
	 * 
	 * @return vrai si le fichier Stockage existe.
	 */
	public boolean fichierStockageExiste(){
		File fichier = new File("stockage.txt");
		if(fichier.exists()){
			return true;
		}
		else System.err.println("Fichier Stockage inexistant !!");
		return false;
		
	}

/**
 * 
 * @param arguments tapés dans le terminal par l'utilisateur.
 * 3 modes sont disponibles:
 * 	"-f" affiche les titres/sous-titres d'un fichier renseigné en second arguments.
 * "-d" stocke les infos de tous les fichiers ODT présents à partir du dossier racine tapé en second argument.
 * "-w" recherche dans le fichier stockage le ou les noms de fichiers qui contiennent le mot renseigné en second argument.
 * 
 * Si aucun de ces modes n'est appelé, l'interface graphique du logiciel se lance.
 */
	public static  void main(String[] args){
		 Recherche r = new Recherche();
		 if(args.length > 1) {
		       
		        if("-f".equals(args[0])) {
		            r.stockerMotsCles(new File(args[1]));
		        }
		        else if("-d".equals(args[0])) {
		        	r.viderStockage();
		            r.lireFichiersODT(new File(args[1]));
		       
		        }else if("-w".equals(args[0])) {
		        	if(r.fichierStockageExiste())
		            r.rechercherParMotCle(args[1].toLowerCase());
		        }
		  }
		  else if(r.fichierParametreExiste()&&!r.fichierParametreEstVide()){
				new RechercheGUI();
		  }
		  else  new ProblemeRacine();
			
	}
	
}