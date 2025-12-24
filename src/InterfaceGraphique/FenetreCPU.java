
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


        setTitle("Architecture interne du 6809");
        setBounds(10, 140, 320, 550);
        getContentPane().setBackground(Color.DARK_GRAY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);


        //PC
        JLabel labelPC = new JLabel("PC");
        labelPC.setForeground(Color.WHITE);
        labelPC.setFont(new Font("Arial", Font.BOLD, 20));
        labelPC.setBounds(20, 10, 50, 30);
        add(labelPC);

        champPC = crearChamp(80, 10, 100, 30);
        add(champPC);

        // A B
        champTexte2 = crearChamp(20, 50, 260, 30);
        add(champTexte2);

        // S U
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


        //R FLAGS
        crearLabel("DP", 5, 300, 30);
        champDP = crearChamp(50, 300, 50, 30);
        add(champDP);

        champflags = crearChamp(140, 300, 130, 30);
        add(champflags);


        setupFlagsUI();

        // X e Y
        crearLabel("X", 10, 420, 30);
        champX = crearChamp(35, 420, 100, 30);
        add(champX);

        crearLabel("Y", 150, 420, 30);
        champY = crearChamp(180, 420, 100, 30);
        add(champY);


        UALShape ual = new UALShape();
        add(ual);


        bindRegisters();

        setVisible(true);
    }


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

        //ACTUALISER
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



    }

    //Métodos utilitários de UI
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

    //Classe UAL
    class UALShape extends JPanel {

        public UALShape() {
            setOpaque(false);
            setBounds(80, 148, 230, 135); // taille adaptée à la forme
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            Polygon p = new Polygon();

            p.addPoint(20, 10);
            p.addPoint(w - 15, 20);
            p.addPoint(w - 15, h - 15);
            p.addPoint(25, h - 10);
            p.addPoint(25, h - 45);
            p.addPoint(65, h / 2);
            p.addPoint(25, 45);

            //COULEUR
            g2.setColor(new Color(210, 210, 210));
            g2.fillPolygon(p);

            //CONTOUR
            g2.setStroke(new BasicStroke(3));
            g2.setColor(Color.BLACK);
            g2.drawPolygon(p);

            //TEXTE UAL
            g2.setFont(new Font("Arial", Font.BOLD, 36));
            FontMetrics fm = g2.getFontMetrics();

            String txt = "UAL";
            int tx = (w - fm.stringWidth(txt)) / 2 + 20;
            int ty = (h + fm.getAscent()) / 2 - 8;

            g2.drawString(txt, tx, ty);
            g.drawLine(7, 20, 20, 20);
            g.drawLine(7, 120, 22, 120);
            g2.drawLine(120, 130, 120, 400);









        }
    }

}