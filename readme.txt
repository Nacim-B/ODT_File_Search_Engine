Projet Gestionnaire de Fichiers ODT
===================================

Logiciel réalisé par Nacim Berrabah et Hakim B****.
-------------

L'objectif de se programme est de lister les fichiers de type "odt" placés sous l'arborescence d'un dossier source précédemment renseigné.
Une fois la liste généré, vous avez à disposition un moteur de recherche dans lequel vous pouvez entrer un ou plusierus mots. 
Le moteur de recherche se limite à rechercher les mots dans les titres des documents (balises html <h> de toutes tailles).
La liste des fichiers dans lesquels on peut retrouver vos mots recherchés est enfin générée.


MODE CONSOLE DU LOGICIEL
-----------------------

Pour utiliser le mode console du logiciel :

	>Lancer le Terminal de l'ordinateur.
	
	>Déplacez-vous jusqu'à l'interieur du dossier où se situe le projet (« cd /cheminDuDossier »).
	
	>taper:	java -jar Gestionnaire_ODT.jar -f "cheminDuFichierATraiter"
			pour afficher les titres et sous-titres d'un fichier odt.
		
		java -jar Gestionnaire_ODT.jar -d "cheminDuDossierAAnalyser"
			pour stocker les informations des fichiers ODT placés sous le dossier racine renseigné.
			  
			  java -jar Gestionnaire_ODT.jar -w "motAChercher"
		pour lister les fichiers qui contiennent le mot recherché.

MODE GRAPHIQUE DU LOGICIEL
--------------------------

	> À partir du terminal : 
		- Se déplacer jusqu'au dossier où se situe le projet
		- taper "java -jar Gestionnaire_ODT.jar"
							 
	> Directement à partir du dossier où se situe le projet : Double cliquez sur le fichier Gestionnaire_ODT.jar
