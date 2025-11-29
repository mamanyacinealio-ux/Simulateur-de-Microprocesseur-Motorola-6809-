package InterfaceGraphique;
import javax.swing.*;
import java.awt.*;
public class FenetreEdition extends JFrame {
     private String  code="jkk"
             ;

    public String getCode() {
        return code;
    }

    public FenetreEdition() {
        //CREATION DE LA FENETRE
        setTitle("Édition");
        setBounds(320, 200, 250, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //CREATION DE LA BARRE DES TACHES
      JMenuBar barre=new JMenuBar();
      add(barre, BorderLayout.NORTH);
       //creation des bouttons
        ImageIcon MAJ= new ImageIcon(
                new ImageIcon("C:/Users/Maman/PROJET JAVA/Simulateur de Microprocesseur Motorola 6809/src/image/MAJJ.png")
                        .getImage()
                        .getScaledInstance(15, 15, Image.SCALE_SMOOTH)
        );
        JButton BMAJ= new JButton(MAJ);
        BMAJ.setToolTipText("Exécuter");



        ImageIcon RECHERCHE= new ImageIcon(
                new ImageIcon("C:/Users/Maman/PROJET JAVA/Simulateur de Microprocesseur Motorola 6809/src/image/RECHERCHE.png")
                        .getImage()
                        .getScaledInstance(15, 15, Image.SCALE_SMOOTH)
        );
        JButton BRECHERCHE = new JButton(RECHERCHE);
        BRECHERCHE.setToolTipText("RECHERCHE");

        ImageIcon AIDE= new ImageIcon(
                new ImageIcon("C:/Users/Maman/PROJET JAVA/Simulateur de Microprocesseur Motorola 6809/src/image/AIDE.png")
                        .getImage()
                        .getScaledInstance(15, 15, Image.SCALE_SMOOTH)
        );
        JButton BAIDE = new JButton(AIDE);
        BAIDE.setToolTipText("AIDE");

        ImageIcon QUITTER= new ImageIcon(
                new ImageIcon("C:/Users/Maman/PROJET JAVA/Simulateur de Microprocesseur Motorola 6809/src/image/QUITTER.png")
                        .getImage()
                        .getScaledInstance(15, 15, Image.SCALE_SMOOTH)
        );
        JButton BQUITTER = new JButton(QUITTER);
        BQUITTER.setToolTipText("QUITTER");
        BQUITTER.addActionListener(e -> {

            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(BQUITTER);
            topFrame.dispose();
        });


        barre.add(BMAJ);
        barre.add(BRECHERCHE);
        barre.add(BAIDE);
        barre.add(BQUITTER);


        //Zone de texte
        JTextArea zoneTexte = new JTextArea();
        zoneTexte.setLineWrap(true);
        zoneTexte.setWrapStyleWord(true);
        zoneTexte.setFont(new Font("Consolas", Font.PLAIN, 16));
        //recuperation du code tapé
         code= zoneTexte.getText();

        //en cas de dépassement
        JScrollPane scrollPane = new JScrollPane(zoneTexte);
        scrollPane.setBounds(120, 20, 250, 200);
        add(scrollPane);






    }
}
