package InterfaceGraphique ;
import java.awt.*;
import javax.swing.*;
import CPU.RegistreCPU;
public class FenetreCPU extends JFrame {
    private RegistreCPU registreCPU = new RegistreCPU();
    private JTextField champTexte1;
    private JTextField champTexte2;
    private JTextField champA;
    private JTextField champB;
    private JTextField champPC;
    private JTextField champU;
    private JTextField champS;
    private JTextField champX;
    private JTextField champY;
    private JTextField champDP;
    private JTextField champflags;






    public FenetreCPU(RegistreCPU registreCPU) {
        //Liaison avec le registre
        this.registreCPU = registreCPU;
        //FENETRE PRINCIPALE
        setTitle("Architecture interne du 6809");
        setBounds(10, 140, 300, 500);
        getContentPane().setBackground(Color.DARK_GRAY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);


        //le registre Pc
        JLabel PC = new JLabel("PC");
        PC.setForeground(Color.BLACK); // couleur du texte
        PC.setFont(new Font("Arial", Font.BOLD, 30));
        PC.setBounds(40, 10, 200, 30);
        add(PC);



        champPC = new JTextField("FC00");
        champPC.setFont(new Font("Arial", Font.BOLD, 16));
        champPC.setForeground(Color.BLACK);
        champPC.setBackground(Color.WHITE);
        champPC.setOpaque(true);
        champPC.setBounds(90, 10, 100, 30);
        add(champPC);


        // champ 2
        champTexte2 = new JTextField("");
        champTexte2.setFont(new Font("Arial", Font.BOLD, 16));
        champTexte2.setForeground(Color.BLACK);
        champTexte2.setBackground(Color.WHITE);
        champTexte2.setOpaque(true);
        champTexte2.setBounds(20, 50, 250, 30); // position décalée vers le bas
        add(champTexte2);

        //champ
        JLabel S = new JLabel("S");
        S.setForeground(Color.BLACK); // couleur du texte
        S.setFont(new Font("Arial", Font.BOLD, 30));
        S.setBounds(00, 100, 200, 30);
        add(S);

       champS = new JTextField("S");
        champS.setFont(new Font("Arial", Font.BOLD, 16));
        champS.setForeground(Color.BLACK);
        champS.setBackground(Color.WHITE);
        champS.setOpaque(true);
        champS.setBounds(30, 100, 100, 30); // position décalée vers le bas
        add(champS);

        //champ U
        JLabel U = new JLabel("U");
        U.setForeground(Color.BLACK); // couleur du texte
        U.setFont(new Font("Arial", Font.BOLD, 30));
        U.setBounds(140, 100, 200, 30);
        add(U);
        champU = new JTextField("U");
        champU.setFont(new Font("Arial", Font.BOLD, 16));
        champU.setForeground(Color.BLACK);
        champU.setBackground(Color.WHITE);
        champU.setOpaque(true);
        champU.setBounds(170, 100, 100, 30); // position décalée vers le bas
        add(champU);

        //champ A
        JLabel A = new JLabel("A");
        A.setForeground(Color.BLACK); // couleur du texte
        A.setFont(new Font("Arial", Font.BOLD, 30));
        A.setBounds(0, 150, 200, 30);
        add(A);
        champA = new JTextField("A");
        champA.setFont(new Font("Arial", Font.BOLD, 16));
        champA.setForeground(Color.BLACK);
        champA.setBackground(Color.WHITE);
        champA.setOpaque(true);
        champA.setBounds(30, 150, 50, 30);
        add(champA);


        //champ B
        JLabel B = new JLabel("B");
        B.setForeground(Color.BLACK); // couleur du texte
        B.setFont(new Font("Arial", Font.BOLD, 30));
        B.setBounds(0, 250, 200, 30);
        add(B);
        champB = new JTextField("B");
        champB.setFont(new Font("Arial", Font.BOLD, 16));
        champB.setForeground(Color.BLACK);
        champB.setBackground(Color.WHITE);
        champB.setOpaque(true);
        champB.setBounds(30, 250, 50, 30);
        add(champB);

        //champ DP
        JLabel DP = new JLabel("DP");
        DP.setForeground(Color.BLACK); // couleur du texte
        DP.setFont(new Font("Arial", Font.BOLD, 30));
        DP.setBounds(0, 300, 200, 30);
        add(DP);
        champDP = new JTextField("DP");
        champDP.setFont(new Font("Arial", Font.BOLD, 16));
        champDP.setForeground(Color.BLACK);
        champDP.setBackground(Color.WHITE);
        champDP.setOpaque(true);
        champDP.setBounds(50, 300, 50, 30);
        add(champDP);

        //flags
        champflags = new JTextField("flags");
        champflags.setFont(new Font("Arial", Font.BOLD, 16));
        champflags.setForeground(Color.BLACK);
        champflags.setBackground(Color.WHITE);
        champflags.setOpaque(true);
        champflags.setBounds(150, 300, 120, 30);
        add(champflags);


        JLabel E= new JLabel("E F H I N Z V C");
        E.setForeground(Color.BLACK); // couleur du texte
        E.setFont(new Font("Arial", Font.BOLD, 15));
        E.setBounds(160, 330, 200, 30);
        add(E);




        //  champ X
        JLabel X = new JLabel("X");
        X.setForeground(Color.BLACK); // couleur du texte
        X.setFont(new Font("Arial", Font.BOLD, 30));
        X.setBounds(00, 400, 200, 30);
        add(X);

        champX = new JTextField("X");
        champX.setFont(new Font("Arial", Font.BOLD, 16));
        champX.setForeground(Color.BLACK);
        champX.setBackground(Color.WHITE);
        champX.setOpaque(true);
        champX.setBounds(30, 400, 100, 30);
        add(champX);

        //  champ Y
        JLabel Y = new JLabel("Y");
        Y.setForeground(Color.BLACK); // couleur du texte
        Y.setFont(new Font("Arial", Font.BOLD, 30));
        Y.setBounds(140, 400, 200, 30);
        add(Y);
        champY = new JTextField("Y");
        champY.setFont(new Font("Arial", Font.BOLD, 16));
        champY.setForeground(Color.BLACK);
        champY.setBackground(Color.WHITE);
        champY.setOpaque(true);
        champY.setBounds(170, 400, 100, 30); // position décalée vers le bas
        add(champY);
        UALShape ual = new UALShape();
        add(ual);

        //liaison avec tous les registres
        bindRegisters();


        //visibilité de la fenetre
        setVisible(true);
    }


    class UALShape extends JPanel {



            public UALShape() {
                setOpaque(false);

                //position
                setBounds(80, 165, 200, 100);
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;

                g2.setStroke(new BasicStroke(3));
                g2.setColor(Color.WHITE);

                // LE POLYGONE
                Polygon p = new Polygon();
                p.addPoint(10, 10);
                p.addPoint(180, 10);
                p.addPoint(180, 90);
                p.addPoint(10, 90);
                p.addPoint(40, 50);
                p.addPoint(10, 10);
                g2.drawPolygon(p);
                g2.setFont(new Font("Arial", Font.BOLD, 28));
                g2.drawString("UAL", 75, 65);
            }
        }



    private void bindRegisters() {
        // bind pour assurer tous les changements de registres
        registreCPU.addPropertyChangeListener(evt -> {
            switch (evt.getPropertyName()) {
                case "PC": champPC.setText(String.format("%04X", registreCPU.getPC())); break;
                case "A": champA.setText(String.format("%02X", registreCPU.getA())); break;
                case "B": champB.setText(String.format("%02X", registreCPU.getB())); break;
                case "D": champTexte2.setText(String.format("%04X", registreCPU.getD())); break;
                case "X": champX.setText(String.format("%04X", registreCPU.getX())); break;
                case "Y": champY.setText(String.format("%04X", registreCPU.getY())); break;
                case "U": champU.setText(String.format("%04X", registreCPU.getU())); break;
                case "S": champS.setText(String.format("%04X", registreCPU.getS())); break;
                case "DP": champDP.setText(String.format("%02X", registreCPU.getDP())); break;
                case "CC": champflags.setText(String.format("%02X", registreCPU.getCC())); break;
            }
        });

        // actualiser les champs
        champPC.setText(String.format("%04X", registreCPU.getPC()));
        champTexte2.setText(String.format("%04X",registreCPU.getD()));
        champA.setText(String.format("%02X", registreCPU.getA()));
        champB.setText(String.format("%02X", registreCPU.getB()));
        champX.setText(String.format("%04X", registreCPU.getX()));
        champY.setText(String.format("%04X", registreCPU.getY()));
        champU.setText(String.format("%04X", registreCPU.getU()));
        champS.setText(String.format("%04X", registreCPU.getS()));
        champDP.setText(String.format("%02X", registreCPU.getDP()));
        champflags.setText(String.format("%02X", registreCPU.getCC()));
    }






}
