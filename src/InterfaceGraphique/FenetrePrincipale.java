package InterfaceGraphique;
import Instruction.Instruction;
import Memoire.Memoire;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import javax.swing.JFrame;
import CPU.RegistreCPU;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.io.File;
public class FenetrePrincipale extends JFrame implements ActionListener {
    private int vitesse = 10;
    private boolean programmeCharge = false;
    private Memoire m;
    private JMenuItem ramItem;
    private JMenuItem romItem;
    private RegistreCPU registrecpu;
    private Instruction I;
    private FenetreROM ROM;
    private FenetreRAM RAM;
    private FenetreEdition fenetreEditionCourante;


    public FenetrePrincipale(RegistreCPU registrecpu,Memoire m,Instruction I) {
        //FENETRE PRINCIPALE
        this.registrecpu=registrecpu;
        this.m = new Memoire(65536);
        this.I=I;




        setTitle("MOTO6809");
        setBounds(0, 0, 1920, 140);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // MENU PRINCIPAL
        // BARRE Du MENU  PRINCIPAL
        JMenuBar barre = new JMenuBar();
        //MENU PRINCIPAL "Fichiers"
        JMenu menuFichiers = new JMenu("Fichiers");

        // OPTIONS DU MENU fichiers
        JMenuItem Nouveau = new JMenuItem("Nouveau");
        Nouveau.setToolTipText("Nouveau fichier");
        Nouveau.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FenetreEdition fenetreEdition=new FenetreEdition(registrecpu,m,I,ROM,RAM);
                fenetreEdition.setVisible(true);
            }
        });
        JMenuItem Ouvrir = new JMenuItem("Ouvrir");
        //ouvrir un fichier en mémoire
        Ouvrir.addActionListener(e -> {

            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(FenetrePrincipale.this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File fichier = fileChooser.getSelectedFile();

                try {
                    // Lire tout le fichier
                    String contenu = Files.readString(fichier.toPath());

                    // Ouvrir la fenêtre d’édition
                    FenetreEdition fenetreEdition =
                            new FenetreEdition(registrecpu, m, I, ROM, RAM);

                    // Mettre le texte dans la zone
                    fenetreEdition.setCode(contenu);

                    fenetreEdition.setVisible(true);

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(
                            FenetrePrincipale.this,
                            "Erreur de lecture du fichier",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
        JMenuItem Enregister = new JMenuItem("Enregister");
        //enregister un fichier en mémoire
        Enregister.addActionListener(e -> {

            // Ouvrir la fenêtre d’édition
            FenetreEdition fenetreEdition =
                    new FenetreEdition(registrecpu, m, I, ROM, RAM);

            // Choisir où enregistrer
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(FenetrePrincipale.this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File fichier = fileChooser.getSelectedFile();

                try {
                    // Récupérer le texte
                    String texte = fenetreEdition.getCode();

                    // Écrire dans le fichier
                    Files.writeString(fichier.toPath(), texte);



                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(
                            FenetrePrincipale.this,
                            "Erreur lors de l’enregistrement",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        JMenuItem Enregister_sous = new JMenuItem("Enregister sous");
        JMenuItem Assembler = new JMenuItem("Assembler");
        JMenuItem Imprimer = new JMenuItem("Imprimer");
        JMenuItem Quitter = new JMenuItem("Quiter");
        Quitter.addActionListener(e -> {

            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(Quitter);
            topFrame.dispose();
        });

        //ajouter les options au menu
        menuFichiers.add(Nouveau);
        menuFichiers.add(Ouvrir);
        menuFichiers.add(Enregister);
        menuFichiers.add(Enregister_sous);
        menuFichiers.add(Assembler);
        menuFichiers.add(Imprimer);
        menuFichiers.add(Quitter);
        setJMenuBar(barre);

        //ajouter le menu à la barre
        barre.add(menuFichiers);


        //MENU PRINCIPAL Simulateur
        JMenu menuSimulateur = new JMenu("Simulateur");

        // OPTIONS DU MENU Simulateur
        JMenuItem Pas_a_pas = new JMenuItem("Pas à pas");
        JMenuItem Executer = new JMenuItem("Exécuter");
        JMenuItem Defaire = new JMenuItem("Défaire");
        JMenuItem Reset = new JMenuItem("Reset");


        // Ajouter les options au menu
        menuSimulateur.add(Pas_a_pas);
        menuSimulateur.add(Executer);
        menuSimulateur.add(Defaire);
        menuSimulateur.add(Reset);


        // MENU PRINCIPAL
        // BARRE DE MENU Outils

        //MENU PRINCIPAL "Fichiers"
        JMenu menuOutils = new JMenu("Outils");

        // OPTIONS DU MENU
        JMenuItem Editeur = new JMenuItem("Editeur");
        JMenuItem Calculatrice = new JMenuItem("Calculatrice");
        JMenuItem Information = new JMenuItem("Information");

        // Ajouter les options au menu
        menuOutils.add(Editeur);
        menuOutils.add(Calculatrice);
        menuOutils.add(Information);
        ;
        setJMenuBar(barre);

        // Ajouter le menu à la barre
        barre.add(menuOutils);

        //Placer la barre en haut de la fenêtre
        setJMenuBar(barre);

        //Actions quand  cliquer


        //MENU PRINCIPAL
        JMenu menuFenetre = new JMenu(" Fenêtre");


        // OPTIONS DU MENU
        JMenuItem Programme = new JMenuItem("Programme");
        ramItem = new JMenuItem("RAM");
        romItem = new JMenuItem("ROM");
        JMenuItem PIA = new JMenuItem("PIA");
        JMenuItem Arranger = new JMenuItem("Arranger ");

        ramItem.addActionListener(this);
        romItem.addActionListener(this);

        //AJout les options au menu
        menuFenetre.add(Programme);
        menuFenetre.add(ramItem);
        menuFenetre.add(romItem);
        menuFenetre.add(PIA);
        menuFenetre.add(Arranger);
        setJMenuBar(barre);
        //ajouter le menu à la barre
        barre.add(menuFenetre);


        //MENU PRINCIPAL options
        JMenu menuOptions = new JMenu("Options");

        //OPTIONS DU MENU
        JMenuItem Police = new JMenuItem("Police");
        JMenuItem Configuration = new JMenuItem("Configuration");
        JMenuItem Sauver = new JMenuItem("Sauver ");

        //Ajouter les options au menu
        menuOptions.add(Police);
        menuOptions.add(Configuration);
        menuOptions.add(Sauver);
        setJMenuBar(barre);

        //Ajouter le menu à la barre
        barre.add(menuOptions);


        //MENU PRINCIPAL aide
        JMenu menuAide = new JMenu("Aide");

        // Placer la barre en haut de la fenêtre
        setJMenuBar(barre);

        // OPTIONS DU MENU aide
        JMenuItem A_propos = new JMenuItem("A propos");
        JMenuItem Index = new JMenuItem("Index");
        JMenuItem Jeu_instructions = new JMenuItem("Jeu instructions");


        //Ajouter les options au menu
        menuAide.add(A_propos);
        menuAide.add(Index);
        menuAide.add(Jeu_instructions);

        //Ajouter le menu à la barre
        barre.add(menuAide);


        // Création de la barre d'outils
        JToolBar barre1 = new JToolBar();


        // Ajouter la barre à la fenêtre
        add(barre1, BorderLayout.NORTH);

        // Cretation des boutons de la deuxième barre
        JTextArea zoneTexte = new JTextArea();
        add(new JScrollPane(zoneTexte), BorderLayout.CENTER);


        ImageIcon iconNouveau = new ImageIcon(
                new ImageIcon(getClass().getResource("/image/new2.png"))
                        .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)
        );

        JButton Nouveau1 = new JButton(iconNouveau);
        Nouveau1.setToolTipText("Nouveau fichier");
        Nouveau1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fenetreEditionCourante =
                        new FenetreEdition(registrecpu, m, I, ROM, RAM);
                fenetreEditionCourante.setVisible(true);

            }
        });

        ImageIcon iconnew = new ImageIcon(
                new ImageIcon(getClass().getResource("/image/ouvrir2.png"))
                        .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)
        );

        JButton ouvrir1 = new JButton(iconnew);
        ouvrir1.setToolTipText("Ouvrir fichier");
        //ouvrir un fichier en mémoire
        ouvrir1.addActionListener(e -> {

            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(FenetrePrincipale.this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File fichier = fileChooser.getSelectedFile();

                try {
                    // Lire tout le fichier
                    String contenu = Files.readString(fichier.toPath());

                    // Ouvrir la fenêtre d’édition
                    fenetreEditionCourante =
                            new FenetreEdition(registrecpu, m, I, ROM, RAM);
                    fenetreEditionCourante.setVisible(true);


                    // Mettre le texte dans la zone
                    fenetreEditionCourante.setCode(contenu);

                    fenetreEditionCourante.setVisible(true);

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(
                            FenetrePrincipale.this,
                            "Erreur de lecture du fichier",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });


        ImageIcon iconsauver = new ImageIcon(
                new ImageIcon(getClass().getResource("/image/sauver.png"))
                        .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)
        );

        JButton sauver = new JButton(iconsauver);
        sauver.setToolTipText("Sauvergarder");
       //enregister un fichier en mémoire
        sauver.addActionListener(e -> {

            if (fenetreEditionCourante == null) {
                JOptionPane.showMessageDialog(this,
                        "Aucun fichier ouvert",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File fichier = fileChooser.getSelectedFile();
                try {
                    Files.writeString(
                            fichier.toPath(),
                            fenetreEditionCourante.getCode()
                    );
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this,
                            "Erreur lors de l’enregistrement",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });




        ImageIcon iconediteur = new ImageIcon(
                new ImageIcon(getClass().getResource("/image/edition.png"))
                        .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)
        );
        JButton editeur = new JButton(iconediteur);
        editeur.setToolTipText("Édititer");
        editeur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FenetreEdition fenetreEdition = new FenetreEdition(registrecpu,m,I,ROM,RAM);
                fenetreEdition.setVisible(true);
            }
        });

        ImageIcon iconpas = new ImageIcon(
                new ImageIcon("/image/pas.png")
                        .getImage()
                        .getScaledInstance(20, 20, Image.SCALE_SMOOTH)
        );
        JButton pas = new JButton(iconpas);
        pas.setToolTipText("Pas à pas");



        ImageIcon iconreset = new ImageIcon(
                new ImageIcon(getClass().getResource("/image/reset.png"))
                        .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)
        );

        JButton reset = new JButton(iconreset);
        reset.setToolTipText("Reset");
        reset.addActionListener(e -> {
            registrecpu.reset();
            if (RAM != null) RAM.atualiseTableaux();
            if (ROM != null) ROM.atualiseTableau();

            System.out.println("RESET CPU effectué");
        });


        ImageIcon iconirq = new ImageIcon(
                new ImageIcon("C:/Image/irq.png")
                        .getImage()
                        .getScaledInstance(20, 20, Image.SCALE_SMOOTH)
        );
        JButton irq = new JButton("IRQ");
        irq.setToolTipText("IRQ");
        irq.addActionListener(e -> {
            I.IRQ();
            if (RAM != null) RAM.atualiseTableaux();
            if (ROM != null) ROM.atualiseTableau();
        });

        ImageIcon iconfirq = new ImageIcon(
                new ImageIcon("C:/Image/firq.png")
                        .getImage()
                        .getScaledInstance(20, 20, Image.SCALE_SMOOTH)
        );
        JButton firq = new JButton("FIRQ");
        firq.setToolTipText("FIRQ");
        firq.addActionListener(e -> {
            I.FIRQ();
            if (RAM != null) RAM.atualiseTableaux();
            if (ROM != null) ROM.atualiseTableau();
        });



        ImageIcon iconnmi = new ImageIcon(
                new ImageIcon("C:/Image/nmi.png")
                        .getImage()
                        .getScaledInstance(20, 20, Image.SCALE_SMOOTH)
        );
        JButton nmi = new JButton("NMI");
        nmi.setToolTipText("NMI");
        nmi.addActionListener(e -> {
            I.NMI();
            if (RAM != null) RAM.atualiseTableaux();
            if (ROM != null) ROM.atualiseTableau();
        });

        ImageIcon iconarranger = new ImageIcon(
                new ImageIcon("C:/Image/arranger.png")
                        .getImage()
                        .getScaledInstance(20, 20, Image.SCALE_SMOOTH)
        );
        JButton arranger = new JButton(iconarranger);
        arranger.setToolTipText("Arranger");


        ImageIcon iconassembler = new ImageIcon(
                new ImageIcon(getClass().getResource("/image/assembleur.png"))
                        .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)
        );

        JButton assembler = new JButton(iconassembler);
        assembler.setToolTipText("Assembler");
        Assembler.addActionListener(e -> {

            if (fenetreEditionCourante == null) {
                JOptionPane.showMessageDialog(this,
                        "Aucun programme ouvert",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            fenetreEditionCourante.assembleAndLoad();
        });









        ImageIcon iconimprimer = new ImageIcon(
                new ImageIcon("C:/Image/imprimer.png")
                        .getImage()
                        .getScaledInstance(20, 20, Image.SCALE_SMOOTH)
        );


        JButton imprimer = new JButton(iconimprimer);
        imprimer.setToolTipText("Imprimer");



        // Label pour afficher la vitesse
        JLabel labelVitesse = new JLabel("Vitesse : " + vitesse);
        ImageIcon iconvitesse = new ImageIcon(
                new ImageIcon("C:/Image/vitesse.png")
                        .getImage()
                        .getScaledInstance(20, 20, Image.SCALE_SMOOTH)
        );


        JButton boutonAugmenter = new JButton("▲");
        boutonAugmenter.addActionListener(e -> {
            vitesse += 10;
            labelVitesse.setText("Vitesse : " + vitesse);
        });


        JButton boutonDiminuer = new JButton("▼");
        boutonDiminuer.addActionListener(e -> {
            if (vitesse > 10) {
                vitesse -= 10;
                labelVitesse.setText("Vitesse : " + vitesse);
            }
        });


        //Ajout des boutons à la barre
        barre1.add(Nouveau1);
        barre1.add(ouvrir1);
        barre1.add(sauver);
        barre1.add(editeur);
       // barre1.add(pas);
        barre1.add(irq);
        barre1.add(firq);
        barre1.add(nmi);
        barre1.add(reset);
       // barre1.add(arranger);
        //barre1.add(assembleur);
        //barre1.add(imprimer);








        setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ramItem) {
            ouvrirFenetreRAM();

        } else   {
            ouvrirFenetreROM();

        }


    }

    private void ouvrirFenetreRAM() {
        FenetreRAM ram = new FenetreRAM(this.m);
        if (RAM !=null) RAM.atualiseTableaux();

        ram.setVisible(true);

    }

    private void ouvrirFenetreROM() {
        FenetreROM rom = new FenetreROM(this.m);
        if (ROM !=null) ROM.atualiseTableau();
        rom.setVisible(true);

    }

}