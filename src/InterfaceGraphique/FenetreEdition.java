package InterfaceGraphique;
import CPU.CPU6809;
import Instruction.Syntaxe;
import javax.swing.*;
import java.awt.*;
import CPU.RegistreCPU;
import Memoire.MemoireRam;

public class FenetreEdition extends JFrame {

    private JTextArea zoneTexte;
    private MemoireRam memoire;
    private RegistreCPU registres;
    private CPU6809 cpu = new CPU6809(registres,memoire);
    public FenetreEdition(RegistreCPU registrecpu,CPU6809 cpu) {
this.registres=registrecpu;
this.cpu=cpu;
        setTitle("Édition");
        setBounds(320, 200, 250, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JMenuBar barre = new JMenuBar();
        add(barre, BorderLayout.NORTH);

        ImageIcon MAJ = new ImageIcon(
                new ImageIcon("C:/Users/Maman/PROJET JAVA/Simulateur de Microprocesseur Motorola 6809/src/image/MAJJ.png")
                        .getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH)
        );
        JButton BMAJ = new JButton(MAJ);
        BMAJ.setToolTipText("Exécuter");

        ImageIcon RECHERCHE = new ImageIcon(
                new ImageIcon("C:/Users/Maman/PROJET JAVA/Simulateur de Microprocesseur Motorola 6809/src/image/RECHERCHE.png")
                        .getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH)
        );
        JButton BRECHERCHE = new JButton(RECHERCHE);

        ImageIcon AIDE = new ImageIcon(
                new ImageIcon("C:/Users/Maman/PROJET JAVA/Simulateur de Microprocesseur Motorola 6809/src/image/AIDE.png")
                        .getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH)
        );
        JButton BAIDE = new JButton(AIDE);

        ImageIcon QUITTER = new ImageIcon(
                new ImageIcon("C:/Users/Maman/PROJET JAVA/Simulateur de Microprocesseur Motorola 6809/src/image/QUITTER.png")
                        .getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH)
        );
        JButton BQUITTER = new JButton(QUITTER);
        BQUITTER.addActionListener(e -> dispose());

        barre.add(BMAJ);
        barre.add(BRECHERCHE);
        barre.add(BAIDE);
        barre.add(BQUITTER);

        zoneTexte = new JTextArea();
        zoneTexte.setLineWrap(true);
        zoneTexte.setWrapStyleWord(true);
        zoneTexte.setFont(new Font("Consolas", Font.PLAIN, 16));

        add(new JScrollPane(zoneTexte), BorderLayout.CENTER);

        // ---- ACTION DU BOUTON Exécuter ----
        BMAJ.addActionListener(e -> {
            String code = getCode();
            boolean Ok  = Syntaxe.VerifierCode(code); // ✔ CORREÇÃO AQUI

            if (Ok) {
               //  cpu.step();
                 //registrecpu.getA();

               // registrecpu.executarPrograma(code);
                JOptionPane.showMessageDialog(this,"correcte" );
    registrecpu.reset();        } else {
                JOptionPane.showMessageDialog(this, "❌ Code invalide. Regarde le console.");
            }
        });
    }

    public String getCode() {
        return zoneTexte.getText();
    }
}
