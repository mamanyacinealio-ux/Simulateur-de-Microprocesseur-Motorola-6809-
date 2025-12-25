
package InterfaceGraphique;
import java.awt.Button;
import javax.swing.JButton;
import javax.swing.*;
import java.awt.*;
import CPU.RegistreCPU;
import Memoire.Memoire;
import Instruction.Instruction;

public class FenetreEdition extends JFrame {

    private JTextArea zoneTexte;
    private Memoire memoire;
    private RegistreCPU registres;
    private Instruction instruction;
    private FenetreROM ROM;
    private FenetreRAM RAM;


    private static final int START_ADDRESS = 0xFC00;

    // =====================================================
    // CONSTRUCTEUR
    // =====================================================
    public FenetreEdition(RegistreCPU registrecpu, Memoire memoire,
                          Instruction instruction, FenetreROM rom, FenetreRAM ram) {

        this.registres = registrecpu;
        this.memoire = memoire;
        this.instruction = instruction;
        this.ROM = rom;
        this.RAM = ram;

        setTitle("Édition & Assembleur");
        setBounds(600, 140, 400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JMenuBar barre = new JMenuBar();
        add(barre, BorderLayout.NORTH);
        setupControlButtons(barre);

        zoneTexte = new JTextArea();
        zoneTexte.setFont(new Font("Monospaced", Font.PLAIN, 14));
        zoneTexte.setLineWrap(true);
        zoneTexte.setWrapStyleWord(true);
        add(new JScrollPane(zoneTexte), BorderLayout.CENTER);

        registres.setPC(START_ADDRESS);
    }

    // =====================================================
    // BOUTONS
    // =====================================================
    private void setupControlButtons(JMenuBar barre) {

        ImageIcon iconassembler = new ImageIcon(
                new ImageIcon(getClass().getResource("/image/assembleur.png"))
                        .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)
        );
        JButton assembleur = new JButton(iconassembler);
        assembleur.setToolTipText("Assembler");
        assembleur.addActionListener(e -> assembleAndLoad());
        barre.add(assembleur);

        ImageIcon iconpas = new ImageIcon(
                new ImageIcon(getClass().getResource("/image/pas.png"))
                        .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)
        );
        JButton pas = new JButton(iconpas);
        pas.setToolTipText("Pas à pas");
        pas.addActionListener(e -> {
            try {
                registres.step();
                if (RAM != null) RAM.atualiseTableaux();
                if (ROM != null) ROM.atualiseTableau();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        barre.add(pas);

        ImageIcon iconimprimer = new ImageIcon(
                new ImageIcon(getClass().getResource("/image/imprimer.png"))
                        .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)
        );

        JButton imprimer = new JButton(iconimprimer);
        imprimer.setToolTipText("Imprimer");
        barre.add(imprimer);
        imprimer.addActionListener(e -> {
            try {
                boolean ok = zoneTexte.print();
                if (!ok) {
                    JOptionPane.showMessageDialog(this,
                            "Impression annulée",
                            "Impression",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Erreur lors de l'impression : " + ex.getMessage(),
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

    }


    //OUTILS

    public String getCode() {
        return zoneTexte.getText();
    }

    public void setCode(String texte) {
        zoneTexte.setText(texte);
    }

    private boolean is16BitInstruction(String inst) {
        return inst.equals("LDX") || inst.equals("STX") ||
                inst.equals("CMPX") || inst.equals("LDY") ||
                inst.equals("LDS") || inst.equals("LDU") ||
                inst.equals("LDD") || inst.equals("CMPY");
    }


    //DECODE MODES (
    private String decode(String inst, String op) throws Exception {

        // PSH / PUL
        if (inst.equals("PSHS") || inst.equals("PSHU") ||
                inst.equals("PULS") || inst.equals("PULU")) {
            return "IMMEDIAT";
        }

        if (op == null || op.trim().isEmpty())
            return "INHERENT";

        op = op.trim();

        // IMMEDIAT
        if (op.startsWith("#$")) {
            String hex = op.substring(2);
            if (is16BitInstruction(inst) && hex.length() != 4)
                throw new Exception(inst + " attend une valeur 16 bits (#$0000)");
            if (!is16BitInstruction(inst) && hex.length() != 2)
                throw new Exception(inst + " attend une valeur 8 bits (#$00)");
            return "IMMEDIAT";
        }

        // DIRECT / ETENDU
        if (op.startsWith("<")) return "DIRECT";
        if (op.startsWith("$")) return "ETENDU";

        // INDEXE SIMPLE
        if (op.matches("(-?\\$?[0-9A-F]+)?\\s*,\\s*[XYUS]"))
            return "INDEXE";

        // INDIRECT
        if (op.startsWith("[") && op.endsWith("]"))
            return "ETENDU INDIRECT";

        return "FALSE";
    }

    //INDEXE
    private int postbyteIndexeSimple(String op) {
        char reg = op.charAt(op.length() - 1);
        int base;
        switch (reg) {
            case 'X': base = 0x84; break;
            case 'Y': base = 0xA4; break;
            case 'U': base = 0xC4; break;
            case 'S': base = 0xE4; break;
            default: throw new IllegalArgumentException("Registre index invalide");
        }
        return op.startsWith(",") ? base : base | 0x08;
    }

    //ASSEZMMBLAGE
    public void assembleAndLoad() {

        String[] lignes = zoneTexte.getText().toUpperCase().split("\\R");
        int adr = START_ADDRESS;

        try {
            // END obligatoire
            boolean hasEnd = false;
            for (String l : lignes)
                if (l.trim().equals("END")) hasEnd = true;
            if (!hasEnd)
                throw new Exception("Le programme doit se terminer par END");

            registres.reset();
            memoire.clearROM();

            for (String ligne : lignes) {

                ligne = ligne.trim();
                if (ligne.isEmpty() || ligne.startsWith(";")) continue;
                if (ligne.equals("END")) break;

                String[] parts = ligne.split("\\s+", 2);
                String inst = parts[0];
                String op = (parts.length > 1) ? parts[1] : "";

                String mode = decode(inst, op);
                if (mode.equals("FALSE"))
                    throw new Exception("Mode invalide : " + op);

                String opcodeStr = instruction.opcode(inst, mode);
                if (opcodeStr == null)
                    throw new Exception("Instruction non supportée : " + inst);

                int opcode = Integer.parseInt(
                        opcodeStr.substring(0, opcodeStr.length() - 2), 16);
                int nbOctet = Integer.parseInt(
                        opcodeStr.substring(opcodeStr.length() - 1));

                int offset = (opcode <= 0xFF) ? 1 : 2;

                // OPCODE
                if (offset == 1)
                    memoire.write(adr, (byte) opcode);
                else {
                    memoire.write(adr,     (byte) (opcode >> 8));
                    memoire.write(adr + 1, (byte) opcode);
                }

                // INDEXE
                if (mode.equals("INDEXE")) {
                    int post = postbyteIndexeSimple(op);
                    memoire.write(adr + offset, (byte) post);

                    if (!op.startsWith(",")) {
                        String offStr = op.split(",")[0].trim();
                        int off = offStr.startsWith("$")
                                ? Integer.parseInt(offStr.substring(1), 16)
                                : Integer.parseInt(offStr);
                        memoire.write(adr + offset + 1, (byte) off);
                        adr += offset + 2;
                    } else {
                        adr += offset + 1;
                    }
                    continue;
                }

                // PSH / PUL
                if (inst.equals("PSHS") || inst.equals("PSHU") ||
                        inst.equals("PULS") || inst.equals("PULU")) {

                    int post = instruction.calculerPostOctetPSH_PUL(op);
                    memoire.write(adr + offset, (byte) post);
                    adr += nbOctet;
                    continue;
                }

                // AUTRES MODES
                if (!mode.equals("INHERENT")) {
                    String valHex = op.replaceAll("[#$<>\\[\\]\\s]", "");
                    if (valHex.contains(",")) valHex = valHex.split(",")[0];
                    int val = Integer.parseInt(valHex, 16);

                    int taille = nbOctet - offset;
                    if (taille == 1)
                        memoire.write(adr + offset, (byte) val);
                    else if (taille == 2) {
                        memoire.write(adr + offset,     (byte) (val >> 8));
                        memoire.write(adr + offset + 1, (byte) val);
                    }
                }

                adr += nbOctet;
            }

            registres.setPC(START_ADDRESS);
            if (ROM != null) ROM.atualiseTableau();
            if (RAM != null) RAM.atualiseTableaux();

            JOptionPane.showMessageDialog(this, "Programme chargé");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erreur d’assemblage : " + ex.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}