package InterfaceGrapgique;
import java.awt.*;
import javax.swing.*;

public class FenetreCPU extends JFrame {
    private JLabel texteVariable;
    public FenetreCPU() {
        //FENETRE PRINCIPALE
        setTitle("Architecture interne du 6809");
        setBounds(10, 140, 300, 500);
        getContentPane().setBackground(Color.DARK_GRAY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(null);


        //le registre Pc
        JLabel texte = new JLabel("PC");
        texte.setForeground(Color.BLACK); // couleur du texte
        texte.setFont(new Font("Arial", Font.BOLD, 30));
        texte.setBounds(40, 10, 300, 30);
        add(texte);

        JTextField champTexte = new JTextField("FC00");
        champTexte.setFont(new Font("Arial", Font.BOLD, 16));
        champTexte.setForeground(Color.BLACK);
        champTexte.setBackground(Color.WHITE);
        champTexte.setOpaque(true);
        champTexte.setBounds(90, 10, 100, 30);
        add(champTexte);

        // Second champ
        JTextField champTexte2 = new JTextField("FC00");
        champTexte2.setFont(new Font("Arial", Font.BOLD, 16));
        champTexte2.setForeground(Color.BLACK);
        champTexte2.setBackground(Color.WHITE);
        champTexte2.setOpaque(true);
        champTexte2.setBounds(90, 50, 100, 30); // position décalée vers le bas
        add(champTexte2);

        setVisible(true);
    }











}