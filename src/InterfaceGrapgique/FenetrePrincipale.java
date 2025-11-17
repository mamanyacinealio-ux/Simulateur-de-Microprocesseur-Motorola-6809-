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

        // OPTIONS DU MENU
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
