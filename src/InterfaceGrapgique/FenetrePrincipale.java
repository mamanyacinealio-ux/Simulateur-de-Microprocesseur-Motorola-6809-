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
    }


}
