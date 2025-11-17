package InterfaceGrapgique;
import java.awt.*;
import javax.swing.*;
public class FenetrePrincipale extends JFrame  {
    public  FenetrePrincipale() {
        //FENETRE PRINCIPALE
        setTitle("MOTO6809");
        setBounds(0,0,1920,140);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // MENU PRINCIPAL
        // BARRE DE MENU
        JMenuBar barre = new JMenuBar();
        //MENU PRINCIPAL "Fichiers"
        JMenu menuFichiers = new JMenu("Fichiers");

        // OPTIONS DU MENU fichiers
        JMenuItem Nouveau = new JMenuItem("Nouveau");
        JMenuItem Ouvrir = new JMenuItem("Ouvrir");
        JMenuItem Enregister = new JMenuItem("Enregister");
        JMenuItem Enregister_sous = new JMenuItem("Enregister sous");
        JMenuItem Assembler = new JMenuItem("Assembler");
        JMenuItem Imprimer = new JMenuItem("Imprimer");
        JMenuItem Quiter= new JMenuItem("Quiter");

        // Ajouter les options au menu
        menuFichiers.add( Nouveau);
        menuFichiers.add(Ouvrir);
        menuFichiers.add(Enregister);
        menuFichiers.add(Enregister_sous);
        menuFichiers.add(Assembler);
        menuFichiers.add(Imprimer);
        menuFichiers.add(Quiter);
        setJMenuBar(barre);

        // Ajouter le menu à la barre
        barre.add(menuFichiers);

        // Placer la barre en haut de la fenêtre
        setJMenuBar(barre);





        //MENU PRINCIPAL Simulateur
        JMenu menuSimulateur = new JMenu("Simulateur");

        // OPTIONS DU MENU Simulateur
        JMenuItem Pas_a_pas = new JMenuItem("Pas à pas");
        JMenuItem Executer = new JMenuItem("Exécuter");
        JMenuItem Defaire = new JMenuItem("Défaire");
        JMenuItem Reset = new JMenuItem("Reset");


        // Ajouter les options au menu
        menuSimulateur.add( Pas_a_pas);
        menuSimulateur.add(Executer);
        menuSimulateur.add(Defaire);
        menuSimulateur.add(Reset);

        // Ajouter le menu à la barre
        barre.add(menuSimulateur);




        //MENU PRINCIPAL fenetre
        JMenu menuFenetre = new JMenu("Fenetre");

        // Placer la barre en haut de la fenêtre
        setJMenuBar(barre);

        // OPTIONS DU MENU fenetre
        JMenuItem Programme = new JMenuItem("Programme");
        JMenuItem RAM = new JMenuItem("RAM");
        JMenuItem ROM = new JMenuItem("ROM");
        JMenuItem PIA = new JMenuItem("PIA");
        JMenuItem Arranger = new JMenuItem("Arranger ");


        // Ajouter les options au menu
        menuFenetre.add(Programme);
        menuFenetre.add(RAM);
        menuFenetre.add(ROM);
        menuFenetre.add(PIA);
        menuFenetre.add(Arranger);
        // Ajouter le menu à la barre
        barre.add(menuFenetre);
        // Placer la barre en haut de la fenêtre
        setJMenuBar(barre);




        //MENU PRINCIPAL aide
        JMenu menuAide = new JMenu("Aide");

        // Placer la barre en haut de la fenêtre
        setJMenuBar(barre);

        // OPTIONS DU MENU aide
        JMenuItem A_propos = new JMenuItem("A propos");
        JMenuItem Index = new JMenuItem("Index");
        JMenuItem Jeu_instructions = new JMenuItem("Jeu instructions");



        // Ajouter les options au menu
        menuAide.add(A_propos);
        menuAide.add(Index);
        menuAide.add(Jeu_instructions);

        // Ajouter le menu à la barre
        barre.add(menuAide);
        // Placer la barre en haut de la fenêtre
        setJMenuBar(barre);



        // Actions quand on clique sur les options


        setVisible(true);


        // MENU PRINCIPAL
        // BARRE DE MENU

        //MENU PRINCIPAL "Fichiers"
        JMenu menuOutils = new JMenu("Outils");

        // OPTIONS DU MENU
        JMenuItem Editeur = new JMenuItem("Editeur");
        JMenuItem Calculatrice = new JMenuItem("Calculatrice");
        JMenuItem Information = new JMenuItem("Information");

        // Ajouter les options au menu
        menuOutils.add( Editeur);
        menuOutils.add(Calculatrice);
        menuOutils.add(Information);;
        setJMenuBar(barre);

        // Ajouter le menu à la barre
        barre.add(menuOutils);

        // Placer la barre en haut de la fenêtre
        setJMenuBar(barre);

        // Actions quand on clique sur les options


        setVisible(true);



        //MENU PRINCIPAL "Fichiers"
        JMenu menuOptions = new JMenu("Options");

        // OPTIONS DU MENU
        JMenuItem Police = new JMenuItem("Police");
        JMenuItem Configuration = new JMenuItem("Configuration");
        JMenuItem Sauver = new JMenuItem("Sauver ");

        // Ajouter les options au menu
        menuOptions.add( Police);
        menuOptions.add(Configuration);
        menuOptions.add(Sauver);
        setJMenuBar(barre);

        // Ajouter le menu à la barre
        barre.add(menuOptions);

        // Placer la barre en haut de la fenêtre
        setJMenuBar(barre);

        // Actions quand on clique sur les options


        setVisible(true);



    }




}
