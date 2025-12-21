/*package InterfaceGraphique ;
import java.awt.*;
import javax.swing.*;
import CPU.RegistreCPU;
import Instruction.Instruction;

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



private Instruction I;


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
*/


package InterfaceGraphique;

import java.awt.*;
import javax.swing.*;
import CPU.RegistreCPU;
import Instruction.Instruction;

public class FenetreCPU extends JFrame {
    private RegistreCPU registreCPU;
    private JTextField champTexte2, champA, champB, champPC, champU, champS, champX, champY, champDP, champflags;

    // Labels individuais para as flags (CC)
    private JLabel lblFlagE, lblFlagF, lblFlagH, lblFlagI, lblFlagN, lblFlagZ, lblFlagV, lblFlagC;

    public FenetreCPU(RegistreCPU registreCPU) {
        this.registreCPU = registreCPU;

        // Configuração da Janela
        setTitle("Architecture interne du 6809");
        setBounds(10, 140, 320, 550);
        getContentPane().setBackground(Color.DARK_GRAY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // --- Registrador PC ---
        JLabel labelPC = new JLabel("PC");
        labelPC.setForeground(Color.WHITE);
        labelPC.setFont(new Font("Arial", Font.BOLD, 20));
        labelPC.setBounds(20, 10, 50, 30);
        add(labelPC);

        champPC = crearChamp(80, 10, 100, 30);
        add(champPC);

        // --- Registrador D (A:B) ---
        champTexte2 = crearChamp(20, 50, 260, 30);
        add(champTexte2);

        // --- Registradores S e U ---
        crearLabel("S", 10, 100, 30);
        champS = crearChamp(35, 100, 90, 30);
        add(champS);

        crearLabel("U", 145, 100, 30);
        champU = crearChamp(175, 100, 90, 30);
        add(champU);

        // --- Registradores A e B ---
        crearLabel("A", 10, 150, 30);
        champA = crearChamp(35, 150, 50, 30);
        add(champA);

        crearLabel("B", 10, 250, 30);
        champB = crearChamp(35, 250, 50, 30);
        add(champB);

        // --- Registrador DP e Hex das Flags ---
        crearLabel("DP", 5, 300, 30);
        champDP = crearChamp(50, 300, 50, 30);
        add(champDP);

        champflags = crearChamp(140, 300, 130, 30);
        add(champflags);

        // --- CRIAÇÃO DAS FLAGS INDIVIDUAIS (E F H I N Z V C) ---
        setupFlagsUI();

        // --- Registradores X e Y ---
        crearLabel("X", 10, 420, 30);
        champX = crearChamp(35, 420, 100, 30);
        add(champX);

        crearLabel("Y", 150, 420, 30);
        champY = crearChamp(180, 420, 100, 30);
        add(champY);

        // Desenho da UAL
        UALShape ual = new UALShape();
        add(ual);

        // Conectar eventos
        bindRegisters();

        setVisible(true);
    }

    // Método para configurar as letras das flags individualmente
    private void setupFlagsUI() {
        int x = 145;
        int y = 335;
        int gap = 16;
        Font f = new Font("Arial", Font.BOLD, 14);

        lblFlagE = crearLabelFlag("E", x, y, f);
        lblFlagF = crearLabelFlag("F", x + gap, y, f);
        lblFlagH = crearLabelFlag("H", x + gap*2, y, f);
        lblFlagI = crearLabelFlag("I", x + gap*3, y, f);
        lblFlagN = crearLabelFlag("N", x + gap*4, y, f);
        lblFlagZ = crearLabelFlag("Z", x + gap*5, y, f);
        lblFlagV = crearLabelFlag("V", x + gap*6, y, f);
        lblFlagC = crearLabelFlag("C", x + gap*7, y, f);
    }

    private JLabel crearLabelFlag(String texto, int x, int y, Font f) {
        JLabel l = new JLabel(texto);
        l.setBounds(x, y, 15, 20);
        l.setFont(f);
        l.setForeground(Color.LIGHT_GRAY);
        add(l);
        return l;
    }

    private void bindRegisters() {
        registreCPU.addPropertyChangeListener(evt -> {
            String prop = evt.getPropertyName();
            Object val = evt.getNewValue();

            switch (prop) {
                case "PC": champPC.setText(String.format("%04X", (int)val)); break;
                case "A":  champA.setText(String.format("%02X", (int)val)); break;
                case "B":  champB.setText(String.format("%02X", (int)val)); break;
                case "D":  champTexte2.setText(String.format("%04X", (int)val)); break;
                case "X":  champX.setText(String.format("%04X", (int)val)); break;
                case "Y":  champY.setText(String.format("%04X", (int)val)); break;
                case "U":  champU.setText(String.format("%04X", (int)val)); break;
                case "S":  champS.setText(String.format("%04X", (int)val)); break;
                case "DP": champDP.setText(String.format("%02X", (int)val)); break;
                case "CC":
                    int ccValue = (int) val;
                    champflags.setText(String.format("%02X", ccValue));
                    // Atualiza as cores das letras
                    updateFlagColor(lblFlagC, (ccValue & 0x01) != 0);
                    updateFlagColor(lblFlagV, (ccValue & 0x02) != 0);
                    updateFlagColor(lblFlagZ, (ccValue & 0x04) != 0);
                    updateFlagColor(lblFlagN, (ccValue & 0x08) != 0);
                    updateFlagColor(lblFlagI, (ccValue & 0x10) != 0);
                    updateFlagColor(lblFlagH, (ccValue & 0x20) != 0);
                    updateFlagColor(lblFlagF, (ccValue & 0x40) != 0);
                    updateFlagColor(lblFlagE, (ccValue & 0x80) != 0);
                    break;
            }
        });

        // Atualização inicial (forçar valores atuais)
        refreshAllFields();
    }

    private void updateFlagColor(JLabel label, boolean active) {
        if (label != null) {
            label.setForeground(active ? Color.RED : Color.LIGHT_GRAY);
        }
    }

    private void refreshAllFields() {
        champPC.setText(String.format("%04X", registreCPU.getPC()));
        champA.setText(String.format("%02X", registreCPU.getA()));
        champB.setText(String.format("%02X", registreCPU.getB()));
        champflags.setText(String.format("%02X", registreCPU.getCC()));
        champX.setText(String.format("%04X", registreCPU.getX()));
        champY.setText(String.format("%04X", registreCPU.getY()));
        champU.setText(String.format("%04X", registreCPU.getU()));
        champS.setText(String.format("%04X", registreCPU.getS()));
        champDP.setText(String.format("%04X", registreCPU.getDP()));


        // Adicione os outros conforme necessário...
    }

    // Métodos utilitários de UI
    private JTextField crearChamp(int x, int y, int w, int h) {
        JTextField tf = new JTextField();
        tf.setBounds(x, y, w, h);
        tf.setFont(new Font("Arial", Font.BOLD, 16));
        tf.setHorizontalAlignment(JTextField.CENTER);
        tf.setEditable(false);
        return tf;
    }

    private void crearLabel(String texto, int x, int y, int h) {
        JLabel l = new JLabel(texto);
        l.setForeground(Color.WHITE);
        l.setFont(new Font("Arial", Font.BOLD, 20));
        l.setBounds(x, y, 40, h);
        add(l);
    }

    // Classe interna da UAL
    class UALShape extends JPanel {
        public UALShape() {
            setOpaque(false);
            setBounds(80, 165, 200, 100);
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(new BasicStroke(3));
            g2.setColor(Color.WHITE);
            Polygon p = new Polygon();
            p.addPoint(10, 10); p.addPoint(180, 10); p.addPoint(180, 90);
            p.addPoint(10, 90); p.addPoint(40, 50); p.addPoint(10, 10);
            g2.drawPolygon(p);
            g2.setFont(new Font("Arial", Font.BOLD, 28));
            g2.drawString("UAL", 75, 65);
        }
    }
}